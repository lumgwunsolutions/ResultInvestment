package group.lsg.resultinvestmentapp.Class;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import butterknife.internal.Constants;
import group.lsg.resultinvestmentapp.R;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG =
            MyFirebaseMessagingService.class.getSimpleName();

    private static int notificationId = 0;
    private static final String POST_ID_KEY = "postId";
    private static final String AUTHOR_ID_KEY = "authorId";
    private static final String ACTION_TYPE_KEY = "actionType";
    private static final String TITLE_KEY = "title";
    private static final String BODY_KEY = "body";
    private static final String ICON_KEY = "icon";
    private static final String ACTION_TYPE_NEW_POST = "new_post";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData() != null &&
                remoteMessage.getData().get(ACTION_TYPE_KEY) != null) {
            handleRemoteMessage(remoteMessage);
        } else {
            LogUtil.logError(TAG, "onMessageReceived()", new
                    RuntimeException("FCM remoteMessage doesn't contains Action Type"));
        }
    }
    private void handleRemoteMessage(RemoteMessage remoteMessage) {
        String receivedActionType =
                remoteMessage.getData().get(ACTION_TYPE_KEY);
        LogUtil.logDebug(TAG, "Message Notification Action Type: " +
                receivedActionType);

        switch (receivedActionType) {
            case ACTION_TYPE_NEW_LIKE:
                parseCommentOrLike(remoteMessage);
                break;
            case ACTION_TYPE_NEW_COMMENT:
                parseCommentOrLike(remoteMessage);
                break;
            case ACTION_TYPE_NEW_POST:
                handleNewPostCreatedAction(remoteMessage);
                break;
        }
    }
    private void handleNewPostCreatedAction(RemoteMessage remoteMessage) {
        String postAuthorId = remoteMessage.getData().get(AUTHOR_ID_KEY);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //Send notification for each users except author of post.
        if (firebaseUser != null &&
                !firebaseUser.getUid().equals(postAuthorId)) {
            PostManager.getInstance(this.getApplicationContext()).incrementNewPostsCounter();
        }
    }
    public Bitmap getBitmapFromUrl(String imageUrl) {
        try {
            return Glide.with(this)
                    .load(imageUrl)
                    .asBitmap()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(Constants.PushNotification.LARGE_ICONE_SIZE,
                            Constants.PushNotification.LARGE_ICONE_SIZE)
                    .get();

        } catch (Exception e) {
            LogUtil.logError(TAG, "getBitmapfromUrl", e);
            return null;
        }
    }
    private void sendNotification(String notificationTitle, String
            notificationBody, Bitmap bitmap, Intent intent) {
        sendNotification(notificationTitle, notificationBody, bitmap,
                intent, null);
    }
    private void sendNotification(String notificationTitle, String
            notificationBody, Bitmap bitmap, Intent intent, Intent backIntent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent;

        if(backIntent != null) {
            backIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Intent[] intents = new Intent[] {backIntent, intent};
            pendingIntent = PendingIntent.getActivities(this,
                    notificationId++, intents, PendingIntent.FLAG_ONE_SHOT);
        } else {
            pendingIntent = PendingIntent.getActivity(this,
                    notificationId++, intent, PendingIntent.FLAG_ONE_SHOT);
        }
        Uri defaultSoundUri=
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setAutoCancel(true)   //Automatically delete the notification
                        .setSmallIcon(R.drawable.ic_push_notification_small)
//Notification icon
                        .setContentIntent(pendingIntent)
                        .setContentTitle(notificationTitle)
                        .setContentText(notificationBody)
                        .setLargeIcon(bitmap)
                        .setSound(defaultSoundUri);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId++ /* ID of
notification */, notificationBuilder.build());
    }
}






