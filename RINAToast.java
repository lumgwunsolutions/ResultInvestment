package group.lsg.resultinvestmentapp.Class;

import android.graphics.Color;

import com.github.johnpersano.supertoasts.library.SuperToast;

import group.lsg.resultinvestmentapp.Application;

public class RINAToast {
    private static RINAToast ourInstance = new RINAToast();

    public static RINAToast getInstance() {
        return ourInstance;
    }

    private RINAToast() {
    }

    public void showToast(int text, int color) {
        SuperToast.cancelAllSuperToasts();
        SuperToast superToast = new SuperToast(Application.getAppContext());
        superToast.setAnimations(RinaUtil.TOAST_ANIMATION);
        superToast.setDuration(SuperToast.Duration.SHORT);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setTextSize(SuperToast.TextSize.SMALL);
        superToast.setText(Application.getAppContext().getResources().getString(text));
        superToast.setBackground(color);
        superToast.show();
    }

    public void showToast(String text, int color) {
        SuperToast.cancelAllSuperToasts();
        SuperToast superToast = new SuperToast(Application.getAppContext());
        superToast.setAnimations(RinaUtil.TOAST_ANIMATION);
        superToast.setDuration(SuperToast.Duration.SHORT);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setTextSize(SuperToast.TextSize.SMALL);
        superToast.setText(text);
        superToast.setBackground(color);
        superToast.show();
    }
}
