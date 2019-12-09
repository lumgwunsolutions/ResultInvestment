package group.lsg.resultinvestmentapp.Class;

import android.animation.ObjectAnimator;
import android.os.Build;
import android.view.View;

import com.daimajia.androidanimations.library.BaseViewAnimator;

public class logoShowAnimation extends BaseViewAnimator {
    @Override
    protected void prepare(View target) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getAnimatorAgent().playTogether(

                    ObjectAnimator.ofFloat(target,"translationY",target.getMeasuredHeight(),
                            -40,20,-10,5,0),
                    ObjectAnimator.ofFloat(target,"alpha",0,1,1,1)
            );
        }
    }
}

