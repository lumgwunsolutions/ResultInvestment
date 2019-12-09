package group.lsg.resultinvestmentapp.Class;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.github.johnpersano.supertoasts.library.SuperToast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import group.lsg.resultinvestmentapp.R;

public class AppUpdateManager {
    private static final String FILE_SEPARATOR = "/";
    private static final String FILE_PATH =
            Environment.getExternalStorageDirectory() + FILE_SEPARATOR +"RINA" +
                    FILE_SEPARATOR;
    private static final String FILE_NAME = FILE_PATH + "RINA.apk";
    private static final int UPDARE_TOKEN = 0x29;
    private static final int INSTALL_TOKEN = 0x31;

    private Context context;
    public static String message = "RINL";
    public static String spec = "";
    private Dialog dialog;
    private ProgressBar progressBar;
    private int curProgress;
    private boolean isCancel;
    private String updateContent;

    public static boolean mustUpdate = false;

    private MaterialDialog progressDialog;

    public AppUpdateManager(Context context) {
        this.context = context;
    }

    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDARE_TOKEN:
                    progressDialog.setProgress(curProgress);
                    break;
                case INSTALL_TOKEN:
                    installApp();
                    break;
            }
        }
    };


    public void checkUpdateInfo(final Boolean showInfo) {
        BmobQuery<APK> query = new BmobQuery<>();
        query.addWhereGreaterThan("version", Application.VERSION);
        query.setLimit(Integer.MAX_VALUE);
        query.findObjects(Application.getAppContext(), new FindListener<APK>() {
            @Override
            public void onSuccess(final List<APK> object) {
                if (object.size() == 0 && showInfo) {
                    RinaUtil.showToast(context,
                            context.getResources().getString("newest version"),
                            SuperToast.Background.BLUE);
                }
                BmobQuery<APK> tooOldQuery = new BmobQuery<>();
                tooOldQuery.addWhereEqualTo("version", Application.VERSION);
                tooOldQuery.setLimit(1);
                tooOldQuery.findObjects(Application.getAppContext(),
                        new FindListener<APK>() {
                            @Override
                            public void onSuccess(List<APK> objectTooOld) {
                                if (objectTooOld.get(0).getTooOld()) {
                                    mustUpdate = true;
                                } else {
                                    mustUpdate = false;
                                }
                                if (object.size() > 0) {
                                    int max = -1;
                                    int maxPosition = 0;
                                    for (int i = 0; i < object.size(); i++) {
                                        if (object.get(i).getVersion() > max) {
                                            max = object.get(i).getVersion();
                                            maxPosition = i;
                                        }
                                    }
                                    spec = object.get(maxPosition).getFileUrl();
                                    updateContent = object.get(maxPosition).getInfo();
                                    SettingManager.getInstance().setCanBeUpdated(true);
                                    if
                                    (SettingManager.getInstance().getRemindUpdate()) showNoticeDialog();
                                } else {
                                    SettingManager.getInstance().setCanBeUpdated(false);
                                }
                            }
                            @Override
                            public void onError(int code, String msg) {
                                mustUpdate = false;
                                if (object.size() > 0) {
                                    int max = -1;
                                    int maxPosition = 0;
                                    for (int i = 0; i < object.size(); i++) {
                                        if (object.get(i).getVersion() > max) {
                                            max = object.get(i).getVersion();
                                            maxPosition = i;
                                        }
                                    }
                                    spec = object.get(maxPosition).getFileUrl();
                                    updateContent = object.get(maxPosition).getInfo();
                                    SettingManager.getInstance().setCanBeUpdated(true);
                                    if
                                    (SettingManager.getInstance().getRemindUpdate()) showNoticeDialog();
                                } else {
                                    SettingManager.getInstance().setCanBeUpdated(false);
                                }
                            }
                        });
            }
            @Override
            public void onError(int code, String msg) {
            }
        });
    }

    private void showNoticeDialog() {
        if (mustUpdate) {
            new MaterialDialog.Builder(context)
                    .title("update title")
                    .content(getContent())
                    .positiveText("update ok")
                    .cancelable(false)
                    .onAny(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog
                                                    materialDialog, @NonNull DialogAction dialogAction) {
                            if (dialogAction == DialogAction.POSITIVE) {
                                showDownloadDialog();
                                materialDialog.dismiss();
                            }
                        }
                    })
                    .show();
        } else {
            new MaterialDialog.Builder(context)
                    .title("update title")
                    .content(getContent())
                    .positiveText("update ok")
                    .negativeText("update cancel")
                    .neutralText("update never")
                    .onAny(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog
                                                    materialDialog, @NonNull DialogAction dialogAction) {
                            if (dialogAction == DialogAction.POSITIVE) {
                                showDownloadDialog();
                                materialDialog.dismiss();
                            } else if (dialogAction == DialogAction.NEGATIVE) {

                            } else {

                                SettingManager.getInstance().setRemindUpdate(false);
                            }
                        }
                    })
                    .show();
        }
    }

    private String getContent() {
        if (updateContent == null) updateContent = "";
        updateContent = updateContent.replaceAll("\\$\\$\\$", "\n");
        return context.getResources().getString(R.string.update_content)
                + "\n" + updateContent;
    }

    private void showDownloadDialog() {

        if (mustUpdate) {
            progressDialog = new MaterialDialog.Builder(context)
                    .title(R.string.downloading_title)
                    .content(R.string.downloading_content)
                    .progress(false, 100, true)
                    .cancelable(false)
                    .onAny(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog
                                                    materialDialog, @NonNull DialogAction dialogAction) {

                        }
                    })
                    .show();
        } else {
            progressDialog = new MaterialDialog.Builder(context)
                    .title(R.string.downloading_title)
                    .content(R.string.downloading_content)
                    .progress(false, 100, true)
                    .negativeText("No")
                    .onAny(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog
                                                    materialDialog, @NonNull DialogAction dialogAction) {
                            if (dialogAction == DialogAction.NEGATIVE) {
                                materialDialog.dismiss();
                                isCancel = false;
                            }
                        }
                    })
                    .show();
        }

        downloadApp();
    }

    private void downloadApp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                InputStream in = null;
                FileOutputStream out = null;
                HttpURLConnection conn = null;
                try {
                    url = new URL(spec);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    long fileLength = conn.getContentLength();
                    in = conn.getInputStream();
                    File filePath = new File(FILE_PATH);
                    if(!filePath.exists()) {
                        filePath.mkdir();
                    }
                    out = new FileOutputStream(new File(FILE_NAME));
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    long readedLength = 0l;
                    while((len = in.read(buffer)) != -1) {
                        if(isCancel) {
                            break;
                        }
                        out.write(buffer, 0, len);
                        readedLength += len;
                        curProgress = (int) (((float) readedLength /
                                fileLength) * 100);
                        handler.sendEmptyMessage(UPDARE_TOKEN);
                        if(readedLength >= fileLength) {
                            progressDialog.dismiss();
                            handler.sendEmptyMessage(INSTALL_TOKEN);
                            break;
                        }
                    }
                    out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if(out != null) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(conn != null) {
                        conn.disconnect();
                    }
                }
            }
        }).start();
    }

    private void installApp() {
        File appFile = new File(FILE_NAME);
        if(!appFile.exists()) {
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" +
                appFile.toString()), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}

