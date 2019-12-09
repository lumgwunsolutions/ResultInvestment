package group.lsg.resultinvestmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import group.lsg.resultinvestmentapp.Class.Investors;

public class RINASignUpActivity extends AppCompatActivity {
    private EditText lastName, firstName,initial,email,phoneNumber;
    private EditText dob ,address ,nid ,dp;
    private Spinner state ;
    private Spinner gender;
    private Spinner packag2;
    private TextView or ,text_org;
    private Button submit,signin,upload_pix;
    private FirebaseAuth rinaAuth;
    private DatabaseReference rinaDatabase;
    private ProgressDialog rinaProgress;
    private ProgressBar prog;
    private DatePickerDialog datePickerDialog;
    public static final String USER_KEY = "clientKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinasign_up);
        rinaAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();


        rinaDatabase =
                FirebaseDatabase.getInstance().getReference().child("Investors");

        Investors investors = new Investors();

        rinaProgress = new ProgressDialog(this);
        prog = (ProgressBar) findViewById(R.id.ProgressBar_signup);
        or = (TextView) findViewById(R.id.or);
        text_org = (TextView) findViewById(R.id.text_org);

        upload_pix = (Button) findViewById(R.id.add_profile_pix);
        signin = (Button) findViewById(R.id.signin);
        submit = (Button) findViewById(R.id.submit_signup);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignUp();
            }
        });
        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                datePickerDialog.show();
            }

        });
        signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent loginIntent = new
                        Intent(RINASignUpActivity.this, RINALoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
            }
        });
        upload_pix.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent pictureIntent = new
                        Intent(RINASignUpActivity.this, RINAPictureActivity.class);
                pictureIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(pictureIntent);
            }
        });
        // Set up Calendar view for the datePickerDialog.
        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new
                DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int
                            monthOfYear, int dayOfMonth) {

                        // Select the date and dismiss the date picker dialog.
                        dob.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
                        datePickerDialog.dismiss();
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
    }
    private void startSignUp() {
        lastName = (EditText) findViewById(R.id.edit_lastname);
        final String lastNameX = lastName.getText().toString().trim();

        firstName = (EditText) findViewById(R.id.edit_firstname);
        final String firstNameX = firstName.getText().toString().trim();



        initial = (EditText) findViewById(R.id.edit_initial);
        final String initialX = initial.getText().toString().trim();


        final String emailX = email.getText().toString().trim();
        email = (EditText) findViewById(R.id.email_investor);


        final String addressX = address.getText().toString().trim();
        address = (EditText) findViewById(R.id.address_investor);

        final String phoneNumberX = phoneNumber.getText().toString().trim();
        phoneNumber = (EditText) findViewById(R.id.phone_investor);


        dob = (EditText) findViewById(R.id.dob);
        final String dobX = dob.getText().toString().trim();

       /* final String stateX = state.getText().toString().trim();
        state = (Spinner) findViewById(R.id.state);

        final String packageX = packag2.getText().toString().trim();
        packag2 = (Spinner) findViewById(R.id.packag2);

        final String genderX = gender.getText().toString().trim();
        gender = (Spinner) findViewById(R.id.gender);*/

        //lga = (Spinner) findViewById(R.id.lga);
        //final String lgaX = lga.getText().toString().trim();
        //if (lgaX.isEmpty()) {
        //    lga.setError("First Names cannot be left empty!");
        //    return;
        //}


        rinaProgress.setMessage("Signing Up...");
        rinaProgress.show();

        if (lastNameX.isEmpty()) {
            lastName.setError("Last Name cannot be left empty!");
            return;
        }
        if (firstNameX.isEmpty()) {
            firstName.setError("First Name cannot be left empty!");
            return;
        }
        if (addressX.isEmpty()) {
            address.setError("Address cannot be left empty!");
            return;
        }
        if (initialX.isEmpty()) {
            initial.setError("Middle Name  or Initial cannot be left empty!");
            return;
        }
        if (emailX.isEmpty()) {
            email.setError("Email Address cannot be left empty!");
            return;
        }
        if (phoneNumberX.isEmpty()) {
            phoneNumber.setError("Phone Number cannot be left empty!");
            return;
        }
        if (dobX.isEmpty()) {
            dob.setError("Date of Birth cannot be left empty!");
            return;
        }
     /*  if (stateX.isEmpty()) {
            state.setError("State cannot be left empty!");
            return;
        }
        if (packageX.isEmpty()) {
            package.setError("Please select a package!");
            return;
        }
        if (genderX.isEmpty()) {
            gender.setError("Please,tell us if you are a male or female!");
            return;
        }*/


    }
    // Method to check if email is legit
    public static boolean isEmailValid(String email) {

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    /* End of Reference */

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
