package group.lsg.resultinvestmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class RINATabActivityInvestor extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinatab_investor);
        Resources resources = getResources();
        TabHost tabHost = getTabHost();

        Intent profileIntent = new Intent().setClass(this,
                RINAProfileActivity.class);
        TabHost.TabSpec tabSpecProfile = tabHost
                .newTabSpec("Investor's Profile")
                .setIndicator("", resources.getDrawable(R.drawable.ic_user))
                .setContent(profileIntent);

        Intent investmentIntent = new Intent().setClass(this,
                RINAInvestmentReport.class);
        TabHost.TabSpec tabSpecInvestment = tabHost
                .newTabSpec("Investment Reports")
                .setIndicator("", resources.getDrawable(R.drawable.ic_biz))
                .setContent(investmentIntent);


        Intent inboxIntent = new Intent().setClass(this, RINAInbox.class);
        TabHost.TabSpec tabSpecInbox = tabHost
                .newTabSpec("Inbox Notifications")
                .setIndicator("", resources.getDrawable(R.drawable.ic_inbox))
                .setContent(inboxIntent);

        Intent intentPersonal = new Intent().setClass(this,
                PersonalActivity.class);
        TabHost.TabSpec tabSpecPersonal = tabHost
                .newTabSpec("Referred Investors")
                .setIndicator("", resources.getDrawable(R.drawable.ic_user))
                .setContent(intentPersonal);

        tabHost.addTab(tabSpecProfile);
        tabHost.addTab(tabSpecInvestment);
        tabHost.addTab(tabSpecInbox);
        tabHost.addTab(tabSpecPersonal);

        //set Windows tab as default (zero based)
        tabHost.setCurrentTab(2);

    }

}