package group.lsg.resultinvestmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import group.lsg.resultinvestmentapp.Class.RINAPrefManager;

public class ThankYouActivity extends AppCompatActivity {
    private TextView thank_you_text;
    private ProgressBar thankyou_progressBar;
    public static final String THANK_YOU_EXTRA_KEY =
            "ThankYou.THANK_YOU_EXTRA_KEY";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        thank_you_text = (TextView) findViewById(R.id.thank_you);
        thankyou_progressBar=(ProgressBar)findViewById(R.id.ProgressBar_thank_you);
        thankyou_progressBar.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                whoYouBe();
                showSnackBar("Opening the RINA User Views....");

                finish();
            }
        }, 1000);
    }
    public void whoYouBe() {
        if (sharedPreferences.getString(RINAPrefManager.EMAIL.equals
                ("info@rina.limited.com" ) && RINAPrefManager.PIN.equals
                ("0703884310200")); {
            Intent i = new Intent(ThankYouActivity.this, RINATabActivityAdmin.class);
            i.putExtra(ThankYouActivity.THANK_YOU_EXTRA_KEY,
                    i);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(ThankYouActivity.this, RINATabActivityInvestor.class);
            i.putExtra(ThankYouActivity.THANK_YOU_EXTRA_KEY,
                    i);
            startActivity(i);
            finish();

        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public ProgressBar getProgressView() {
        return getProgressView();
    }
    }

