package group.lsg.resultinvestmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class RINATabActivityAdmin extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinatab_admin);
        Resources ressources = getResources();
        TabHost tabHost = getTabHost();

        Intent basicPackageIntent = new Intent().setClass(this,
                RINABasicInvestors.class);
        TabHost.TabSpec tabBasic = tabHost
                .newTabSpec("Basic Package")
                .setIndicator("", ressources.getDrawable(R.drawable.ic_user))
                .setContent(basicPackageIntent);

        Intent standardInvestorsIntent = new Intent().setClass(this,
                RINAStandardInvestors.class);
        TabHost.TabSpec tabStandard = tabHost
                .newTabSpec("Standard Package")
                .setIndicator("", ressources.getDrawable(R.drawable.ic_biz))
                .setContent(standardInvestorsIntent);


        Intent premiumIntent = new Intent().setClass(this,
                RINAPremiumInvestors.class);
        TabHost.TabSpec tabPremium = tabHost
                .newTabSpec("Premium Package")
                .setIndicator("", ressources.getDrawable(R.drawable.ic_noti))
                .setContent(premiumIntent);

        Intent customIntent = new Intent().setClass(this,
                RINACustomInvestors.class);
        TabHost.TabSpec tabCustom = tabHost
                .newTabSpec("Custom Package")
                .setIndicator("", ((Resources) ressources).getDrawable(R.drawable.ic_user))
                .setContent(customIntent);

        tabHost.addTab(tabBasic);
        tabHost.addTab(tabStandard);
        tabHost.addTab(tabPremium);
        tabHost.addTab(tabCustom);

        //set Windows tab as default (zero based)
        tabHost.setCurrentTab(2);
    }

}



