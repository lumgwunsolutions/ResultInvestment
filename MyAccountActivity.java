package group.lsg.resultinvestmentapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MyAccountActivity extends AppCompatActivity {
    private DrawerLayout xDrawerLayout;
    private ActionBarDrawerToggle xToggle;
    private FirebaseAuth xAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        Helper.SetBalance(this);
        Helper.SetUsername(this);
        Helper.SetDOB(this);
        Helper.SetEmail(this);


        xAuth = FirebaseAuth.getInstance();

        /* Top nav. */
        xDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        xToggle = new ActionBarDrawerToggle(this, xDrawerLayout,
                R.string.open, R.string.close);

        xDrawerLayout.addDrawerListener(xToggle);
        xToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView xNavigationView = (NavigationView)
                findViewById(R.id.top_nav_id);

        /* Activity Change when Item from Top Navigator is Clicked */
        xNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_account:
                        Intent accountActivity = new
                                Intent(getApplicationContext(), MyAccountActivity.class);
                        accountActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(accountActivity);
                        return true;
                        case R.id.nav_home:
                            Intent homeActivity = new Intent(getApplicationContext(), RINALoginActivity.class);
                            homeActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(homeActivity);
                            return true;
                            case R.id.nav_events:
                                Intent eventsActivity = new
                                        Intent(getApplicationContext(), EventsActivity.class);
                                eventsActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(eventsActivity);
                                return true;
                                case R.id.inbox:
                                    Intent sendActivity = new Intent(getApplicationContext(), RINAInboxActivity.class);
                                    sendActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(sendActivity);
                                    return true;
                                    case R.id.new_investor:
                                        Intent adActivity = new Intent(Intent adActivity = new
                                                Intent(getApplicationContext(), AddNewInvestorActivity.class);
                                        adActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(adActivity);
                                        return true;
                                        case R.id.nav_logout:
                                            xAuth.signOut();

                                            return true;
                }
                return true;
            }
        });
    }

    // Method that allows the navigator to open.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(xToggle.onOptionsItemSelected(item)) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

