package group.lsg.resultinvestmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RINAAdminPanelActivity extends AppCompatActivity {
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_menu);
        Intent intent = getIntent();
        email = (String)
                intent.getSerializableExtra(RINATabActivityAdmin.EMAIL_KEY,editInvestorsInfo);

    }
    public void startInvestorsList(View view) {
        Intent intent1 = new Intent(this, InvestorsListActivity.class);
        startActivity(intent1);
    }
    public void pinCreation(View view) {
        Intent intent2 = new Intent(this, CreatePasswordActivity.class);
        startActivity(intent2);
    }


    public void startInvestorsPackage(View view) {
        Intent intent3 = new Intent(this, InvestmentPackageActivity.class);
        startActivity(intent3);
    }
    public void startAdminTab(View view) {
        Intent intent4 = new Intent(this, RINATabActivityAdmin.class);
        startActivity(intent4);
    }
    public void startHelpList(View view) {
        Intent intent5 = new Intent(this, HelpActivity.class);
        startActivity(intent5);
    }
    public void startInvestorsTab(View view) {
        Intent intent6 = new Intent(this, RINATabActivityInvestor.class);
        startActivity(intent6);
    }

}




