package group.lsg.resultinvestmentapp.Class;

import android.content.Context;
import android.os.AsyncTask;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.iid.FirebaseInstanceId;

public class LogoutHelper {
    private static final String TAG = LogoutHelper.class.getSimpleName();
    private static ClearImageCacheAsyncTask clearImageCacheAsyncTask;

    public static void signOut(GoogleApiClient mGoogleApiClient,
                               FragmentActivity fragmentActivity) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            DatabaseHelper.getInstance(fragmentActivity.getApplicationContext())

                    .removeRegistrationToken(FirebaseInstanceId.getInstance().getToken(),
                            user.getUid());

            for (UserInfo profile : user.getProviderData()) {
                String providerId = profile.getProviderId();
                logoutByProvider(providerId, mGoogleApiClient,
                        fragmentActivity);
            }
            logoutFirebase(fragmentActivity.getApplicationContext());
        }

        if (clearImageCacheAsyncTask == null) {
            clearImageCacheAsyncTask = new
                    ClearImageCacheAsyncTask(fragmentActivity.getApplicationContext());
            clearImageCacheAsyncTask.execute();
        }
    }
    private static void logoutFirebase(Context context) {
        FirebaseAuth.getInstance().signOut();
        PreferencesUtil.setProfileCreated(context, false);
    }
    private static class ClearImageCacheAsyncTask extends
            AsyncTask<Void, Void, Void> {
        private Context context;

        public ClearImageCacheAsyncTask(Context context) {
            this.context = context;
        }
        @Override
        protected Void doInBackground(Void... params) {

            Glide.get(context.getApplicationContext()).clearDiskCache();
            return null;
        }

        @Override
        protected void onPostExecute(Void o) {
            super.onPostExecute(o);
            clearImageCacheAsyncTask = null;
            Glide.get(context.getApplicationContext()).clearMemory();
        }
    }
}



