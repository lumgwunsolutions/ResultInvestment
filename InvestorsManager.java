package group.lsg.resultinvestmentapp.Class;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvestorsManager extends FirebaseListenersManager {

    private static final String TAG = InvestorsManager.class.getSimpleName();
    private static InvestorsManager instance;
    private int newInvestorsCounter = 0;
    private InvestorsCounterWatcher investorsCounterWatcher;

    private Context context;
    private DatabaseHelper databaseHelper;
    private Map<Context, List<ValueEventListener>> activeListeners =
            new HashMap<>();


    public static InvestorsManager getInstance(Context context) {
        if (instance == null) {
            instance = new InvestorsManager(context);
        }
        return instance;
    }

    private InvestorsManager(Context context) {
        this.context = context;
        databaseHelper = ApplicationHelper.getDatabaseHelper();
    }

    public void createOrUpdateInvestors(Investors investors) {
        try {
            ApplicationHelper.getDatabaseHelper().createOrUpdateInvestors(investors);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public Investors buildProfile(FirebaseUser firebaseUser, String
            largeAvatarURL) {
        Investors profile = new Investors(firebaseUser.getUid());
        profile.setEmail(firebaseUser.getEmail());
        profile.setFirstName(firebaseUser.getDisplayName());
        profile.setPhotoUrl(largeAvatarURL != null ? largeAvatarURL :
                firebaseUser.getPhotoUrl().toString());
        return profile;
    }
    public void isProfileExist(String id, final
    OnObjectExistListener<Investors> onObjectExistListener) {
        DatabaseReference databaseReference =
                databaseHelper.getDatabaseReference().child("investors").child(id);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onObjectExistListener.onDataChanged(dataSnapshot.exists()); }
                @Override
                public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void createOrUpdateProfile(Investors profile, OnProfileCreatedListener onProfileCreatedListener) {
        createOrUpdateProfile(profile, null, onProfileCreatedListener);
    }


    public void
    getInvestorsList(OnInvestorsListChangedListener<Investors>
                             onDataChangedListener, long date) {
        ApplicationHelper.getDatabaseHelper().getInvestorsList(onDataChangedListener,
                date);
    }

    public void
    getInvestorsListByUser(OnDataChangedListener<Investors>
                                   onDataChangedListener, String userId) {
        ApplicationHelper.getDatabaseHelper().getInvestorsListByUser(onDataChangedListener,
                userId);
    }
    public void getInvestors(Context context, String investorsId,
                             OnInvestorsChangedListener onInvestorsChangedListener) {
        ValueEventListener valueEventListener =
                ApplicationHelper.getDatabaseHelper().getInvestors(investorsId,
                        onInvestorsChangedListener);
        addListenerToMap(context, valueEventListener);
    }

    public void getSingleInvestorsValue(String investorsId,
                                        OnInvestorsChangedListener onInvestorsChangedListener) {
        ApplicationHelper.getDatabaseHelper().getSingleInvestors(investorstId,
                OnInvestorsChangedListener);
    }

    public void createOrUpdateInvestorsWithImage(Uri imageUri, final
    OnInvestorsCreatedListener onInvestorsCreatedListener, final Investors
                                                         investors) {
        // Register observers to listen for when the download is done or if it fails
        DatabaseHelper databaseHelper = ApplicationHelper.getDatabaseHelper();
        if (investors.getId() == null) {
            investors.setId(databaseHelper.generateInvestorsId());
        }

        final String imageTitle =
                ImageUtil.generateImageTitle(UploadImagePrefix.Investors,
                        investors.getId());
        UploadTask uploadTask = databaseHelper.uploadImage(imageUri,
                imageTitle);
        if (uploadTask != null) {
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    onInvestorsCreatedListener.onInvestorsSaved(false);

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains filemetadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    LogUtil.logDebug(TAG, "successful upload image, image url: " + String.valueOf(downloadUrl));

                    investors.setImagePath(String.valueOf(downloadUrl));
                    investors.setImageTitle(imageTitle);
                    createOrUpdateInvestors(investors);
                    onInvestorsCreatedListener.onInvestorsSaved(true);
                }
            });
        }
    }

    public Task<Void> removeImage(String imageTitle) {
        final DatabaseHelper databaseHelper =
                ApplicationHelper.getDatabaseHelper();
        return databaseHelper.removeImage(imageTitle);
    }

    public void removeInvestors(final Investors investors, final
    OnTaskCompleteListener onTaskCompleteListener) {
        final DatabaseHelper databaseHelper =
                ApplicationHelper.getDatabaseHelper();
        Task<Void> removeImageTask = removeImage(investors.getImageTitle());

        removeImageTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                databaseHelper.removeInvestors(investors).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                onTaskCompleteListener.onTaskComplete(task.isSuccessful());

                                databaseHelper.updateProfileLikeCountAfterRemovingInvestors(investors);
                                LogUtil.logDebug(TAG, "removeInvestors(), is success: " + task.isSuccessful());
                            }
                        });
                LogUtil.logDebug(TAG, "removeImage(): success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                LogUtil.logError(TAG, "removeImage()", exception);
                onTaskCompleteListener.onTaskComplete(false);
            }
        });
    }
    public void hasInvestmentPackage(Context activityContext, String
            name, String userId, final OnObjectExistListener<InvestmentPackage>
                                             onObjectExistListener) {
        DatabaseHelper databaseHelper = ApplicationHelper.getDatabaseHelper();
        ValueEventListener valueEventListener =
                databaseHelper.hasInvestmentPackage(name, userId,
                        onObjectExistListener);
        addListenerToMap(activityContext, valueEventListener);
    }

    public void hasInvestmentPackageSingleValue(String name, String
            userId, final OnObjectExistListener<InvestmentPackage>
                                                        onObjectExistListener) {
        DatabaseHelper databaseHelper = ApplicationHelper.getDatabaseHelper();
        databaseHelper.hasInvestmentPackageSingleValue(name, userId,
                onObjectExistListener);
    }

    public void isInvestorsExistSingleValue(String name, final
    OnObjectExistListener<Investors> onObjectExistListener) {
        DatabaseHelper databaseHelper = ApplicationHelper.getDatabaseHelper();
        databaseHelper.isInvestorsExistSingleValue(name, onObjectExistListener);
    }

    public void incrementWatchersCount(String name) {
        DatabaseHelper databaseHelper = ApplicationHelper.getDatabaseHelper();
        databaseHelper.incrementWatchersCount(name);
    }

    public void incrementNewInvestorsCounter() {
        newInvestorsCounter++;
        notifyInvestorsCounterWatcher();
    }

    public void clearNewInvestorsCounter() {
        newInvestorsCounter = 0;
        notifyInvestorsCounterWatcher();
    }

    public int getNewInvestorsCounter() {
        return newInvestorsCounter;
    }

    public void setInvestorsCounterWatcher(InvestorsCounterWatcher
                                                   investorsCounterWatcher) {
        this.investorsCounterWatcher = investorsCounterWatcher;
    }
    private void notifyInvestorsCounterWatcher() {
        if (investorsCounterWatcher != null) {
            investorsCounterWatcher.onInvestorsCounterChanged(newInvestorsCounter);
        }
    }

    public interface InvestorsCounterWatcher {
        void onInvestorsCounterChanged(int newValue);
    }
    public void createOrUpdateProfile(final Investors profile, Uri
            imageUri, final OnProfileCreatedListener onProfileCreatedListener) {
        if (imageUri == null) {
            databaseHelper.createOrUpdateProfile(profile,
                    onProfileCreatedListener);
            return;
        }

        String imageTitle =
                ImageUtil.generateImageTitle(UploadImagePrefix.PROFILE,
                        profile.getId());
        UploadTask uploadTask = databaseHelper.uploadImage(imageUri,
                imageTitle);

        if (uploadTask != null) {
            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull
                                               Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUrl = task.getResult().
                                getDownloadUrl();
                        LogUtil.logDebug(TAG, "successful upload image, image url: " + String.valueOf(downloadUrl));
                        profile.setPhotoUrl(downloadUrl.toString());
                        databaseHelper.createOrUpdateProfile(profile,
                                onProfileCreatedListener);
                    } else {
                        onProfileCreatedListener.onProfileCreated(false);
                        LogUtil.logDebug(TAG, "fail to upload image");
                    }
                }
            });
        } else {
            onProfileCreatedListener.onProfileCreated(false);
            LogUtil.logDebug(TAG, "fail to upload image");
        }
    }

    public void getProfileValue(Context activityContext, String id,
                                final OnObjectChangedListener<Investors> listener) {
        ValueEventListener valueEventListener =
                databaseHelper.getProfile(id, listener);

        addListenerToMap(activityContext, valueEventListener);
    }
    public void getProfileSingleValue(String id, final
    OnObjectChangedListener<Investors> listener) {
        databaseHelper.getProfileSingleValue(id, listener);
    }

    public ProfileStatus checkProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            return ProfileStatus.NOT_AUTHORIZED;
        } else if (!RinaUtil.isProfileCreated(context)){
            return ProfileStatus.NO_PROFILE;
        } else {
            return ProfileStatus.PROFILE_CREATED;
        }
    }
}
