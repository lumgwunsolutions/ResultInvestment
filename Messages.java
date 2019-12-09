package group.lsg.resultinvestmentapp.Class;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class Messages {
    public static void fatalError(final Activity owner, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(owner).create();

        alertDialog.setTitle(owner.getString(Integer.parseInt("fatal Error")));
        alertDialog.setMessage(message);
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                owner.finish();
            }
        });

        alertDialog.show();
    }

    public static void warning(Activity owner, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(owner).create();

        alertDialog.setTitle(owner.getString(Integer.parseInt("something is wrong ")));
        alertDialog.setMessage(message);

        alertDialog.show();
    }
}


