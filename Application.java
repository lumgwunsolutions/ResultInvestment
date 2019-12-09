package group.lsg.resultinvestmentapp.Class;

import android.content.Context;
import android.provider.Settings;

import leakcanary.LeakCanary;

public class Application extends android.app.Application {
    public static final int VERSION = (int) 1.0;

    private static Context mContext;

    public static RefWatcher getRefWatcher(Context context) {
        Application application = (Application) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;

    @Override public void onCreate() {
        super.onCreate();

        refWatcher = LeakCanary.install(this);
        Application.mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return Application.mContext;
    }

    public static String getAndroidId() {
        return Settings.Secure.getString(
                getAppContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

}

