package group.lsg.resultinvestmentapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import group.lsg.resultinvestmentapp.Class.Application;

public class HelpActivity extends AppCompatActivity implements
        HelpFeedbackFragment.OnTextChangeListener {

    private MaterialViewPager mViewPager;

    private Toolbar toolbar;

    private HelpFragmentAdapter adapter = null;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_help);

        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);

        mViewPager.getPagerTitleStrip();
        mViewPager.getPagerTitleStrip().setAllCaps(false);
        mViewPager.getPagerTitleStrip().setUnderlineColor(Color.parseColor("#00000000"));
        mViewPager.getPagerTitleStrip().setIndicatorColor(Color.parseColor("#00000000"));
        mViewPager.getPagerTitleStrip().setUnderlineHeight(0);
        mViewPager.getPagerTitleStrip().setIndicatorHeight(0);

        setTitle("Help Feedback");

        toolbar = mViewPager.getToolbar();

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }

        View logo = findViewById(R.id.ic_rina);
        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                }
            });
        }

        adapter = new HelpFragmentAdapter(getSupportFragmentManager(), 2);
        mViewPager.getViewPager().setAdapter(adapter);
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        mViewPager.getPagerTitleStrip().invalidate();
        mViewPager.getViewPager().setOffscreenPageLimit(2);

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                return HeaderDesign.fromColorAndDrawable(
                        ContextCompat.getColor(Application.getAppContext(), R.color.blue),
                        ContextCompat.getDrawable(Application.getAppContext(),
                                R.drawable.shape_rectangle1));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MaterialViewPagerHelper.unregister(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private String feedbackText = "";
    private boolean exceed;
    @Override
    public void onTextChange(String text, boolean exceed) {
        feedbackText = text;
        this.exceed = exceed;
    }
}



