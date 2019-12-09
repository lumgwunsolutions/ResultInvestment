package group.lsg.resultinvestmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.eftimoff.androipathview.PathView;

public class RINASplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    private ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinasplash);
        getSupportActionBar().hide();


        final PathView pathView = (PathView) findViewById(R.id.pathView);
        pathView.getPathAnimator()
                .delay(1000)
                .duration(1000)
                .interpolator(new AccelerateDecelerateInterpolator())
                .start();

        pathView.useNaturalColors();
        pathView.setFillAfter(true);



        final Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.lineTo(length / 4f, 0.0f);
        path.lineTo(length, height / 2.0f);
        path.lineTo(length / 4f, height);
        path.lineTo(0.0f, height);
        path.lineTo(length * 3f / 4f, height / 2f);
        path.lineTo(0.0f, 0.0f);
        path.close();

        pathView.setPath(path);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent enterIntent = new
                        Intent(RINASplashActivity.this, PreLoginSignUpActivity.class);
                enterIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(enterIntent);
                finish();


            }
        }, SPLASH_TIME_OUT);

    }
}


