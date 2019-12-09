package group.lsg.resultinvestmentapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.liveo.ui.RoundedImageView;
import butterknife.BindView;

public class PreLoginSignUpActivity extends AppCompatActivity implements View.OnClickListener,
        LoginView, LoaderManager.LoaderCallbacks {

    private static final String TAG =
            PreLoginSignUpActivity.class.getSimpleName();
    @BindView(R.id.login_or_signup) TextView or;
    @BindView(R.id.login_write_up)  TextView act;
    //@BindView(R.id.signup_layout) LinearLayout sig;
    @BindView(R.id.user_login) Button user_login;
    @BindView(R.id.sign_up) Button sign_up;
    @BindView(R.id.user_pix) RoundedImageView logo_rina;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_login_sign_up);
        sign_up = (Button) findViewById(R.id.sign_up);
        user_login = (Button) findViewById(R.id.user_login);
        act = (TextView) findViewById(R.id.login_write_up);
        or = (TextView) findViewById(R.id.login_or_signup);
        logo_rina = (RoundedImageView) findViewById(R.id.user_pix);


        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PreLoginSignUpActivity.this,
                        RINASignUpActivity.class));
                finish();
            }
        });
        user_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PreLoginSignUpActivity.this,
                        RINALoginActivity.class));
                finish();
            }
        });
        // Set up the login form.
      /*  if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/

    }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.options_menu, menu);
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, RINASplashActivity.class);
                startActivity(homeIntent);
                return true;
            case android.R.id.settings:
                Intent myAccountIntent = new Intent(this,
                        MyAccountActivity.class);
                startActivity(myAccountIntent);
                return true;
            case android.R.id.about:
                Intent aboutIntent = new Intent(this, AboutUsActivity.class);
                startActivity(aboutIntent);
                return true;
            case android.R.id.help:
                Intent helpIntent = new Intent(this, HelpUsActivity.class);
                startActivity(helpIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }
            @Override
    public void onClick(View view) {

    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
