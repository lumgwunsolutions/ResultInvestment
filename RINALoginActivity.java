package group.lsg.resultinvestmentapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import br.liveo.ui.RoundedImageView;
import butterknife.BindView;
import group.lsg.resultinvestmentapp.Class.Investors;
import group.lsg.resultinvestmentapp.Class.PrefManager;
import group.lsg.resultinvestmentapp.Class.RINAPrefManager;

import static android.media.audiofx.AudioEffect.SUCCESS;

public class RINALoginActivity extends  AppCompatActivity {

    Button b1, b2, signup_button;
    EditText ed1,ed2;
    private CheckBox remember_me;
    private TextView tx1, tx0,or,tx2,tx3;
    private RoundedImageView logo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinalogin);
    }

    public void loadAdminFile(View view) {
        ed1 = (EditText) findViewById(R.id.edtview1
        ed2 = (EditText) findViewById(R.id.edtview0);
        logo = (RoundedImageView) findViewById(R.id.edtview0);
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b1);
        signup_button = (Button) findViewById(R.id.sign_up);
        tx0 = (TextView) findViewById(R.id.textview00);
        tx1 = (TextView) findViewById(R.id.textview01);
        tx2 = (TextView) findViewById(R.id.textview2);
        tx3 = (TextView) findViewById(R.id.textview3);
        tx1.setVisibility(View.GONE);
        //or = (TextView) findViewById(R.id.or_write_up);
        remember_me =(CheckBox)findViewById(R.id.remember_me);
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RINALoginActivity.this,
                        RINASignUpActivity.class));
                finish();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isAdmin = false;
                final String registeredEmail =
                        ed1.getText().toString().trim();
                final String userPin = ed2.getText().toString().trim();

                if(registeredEmail.equals("rinaadmin@rina.com") &&
                        userPin.equals("0703884310200")){
                    Toast.makeText(getApplicationContext(),"Login you into the Admin's Interface", Toast.LENGHT_SHORT).show();
                    Intent adminIntent = new Intent(this,
                            RINATabAActivityAdmin.class);
                    startActivity(adminIntent);
                }return isAdmin;


                Investors investors = new Investors();
                int emailhint = Investors.getEmail();
                int pinhint = Investors.getPin();
            else if (registeredEmail.equals(emailhint) && userPin.
                        equals(pinhint)){
                    Toast.makeText(getApplicationContext(),"Login you into the Investor's Interface", Toast.LENGHT_SHORT).show();
                    Intent intent = new Intent(this,
                            RINATabAActivityInvestors.class);
                    startActivity(intent);
                }else{
                    View focusView = null;
                    boolean cancel = false;
                    if (TextUtils.isEmpty(userPin)) {
                        ed2.setError("Registered Pin cannot be left empty!");
                        focusView = ed2;
                        cancel = true;

                        if (TextUtils.isEmpty(registeredEmail)) {
                            ed1.setError("Registered Email cannot be left empty!");
                            focusView = ed1;
                            cancel = true;

                            Toast.makeText(getApplicationContext(),"Wrong Credentials", Toast.LENGHT_SHORT).show();
                                    tx1.setVisibility(View.VISIBLE);
                            tx1.setBackgroundColor(Color.RED);
                            counter--;
                            tx1.setText(Integer.toString(counter));
                            if(counter ==0){
                                b1.setEnabled(false);
                            }
                        }
                    }
                }
            }
        });
               b2.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       finish();
                   }
               });
            }
        }