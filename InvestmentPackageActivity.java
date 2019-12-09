package group.lsg.resultinvestmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

import group.lsg.resultinvestmentapp.Class.DatabaseHelper;
import group.lsg.resultinvestmentapp.Class.InvestmentPackage;
import group.lsg.resultinvestmentapp.Class.Investors;

public class InvestmentPackageActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    private EditText amountInvested, circle, others;
    private Spinner type;
    private Spinner currency;
    private Spinner office;
    private Spinner interestRate;
    private Spinner remark;
    private Date dateO;
    private Date dateOfCompletion;
    private Calendar calendar;
    private TextView userId, tag, dob, approver;
    private Button submit_button;
    private FirebaseAuth rinaAuth;
    private ProgressDialog rinaProgress;
    private Button submit;
    private DatabaseReference rinaDatabase;
    private DatePickerDialog datePickerDialog;
    private ProgressBar prog;

    public static final String USER_KEY = "clientKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.package_layout);
        rinaAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();


        rinaDatabase =
                FirebaseDatabase.getInstance().getReference().child("Investors");

        Investors investors = new Investor();
        InvestmentPackage packag2 = new InvestmentPackage();

        rinaProgress = new ProgressDialog(this);
        prog = (ProgressBar) findViewById(R.id.ProgressBar_package);

        submit = (Button) findViewById(R.id.submit_signup);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                setUpPackage();
            }
        });
        dateO.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                datePickerDialog.show();
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
                        dateO.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                        datePickerDialog.dismiss();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setUpPackage() {
        DatabaseHelper dbHandler = new
                DatabaseHelper(InvestmentPackageActivity.this);

        dbHandler.insertInvestmentPackages(type, amountInvested, interestRate, currency, duration, dateOfActivation, dateOfCompletion,
                approver, office, remarks, others, time);
        Intent intent = new Intent(InvestmentPackageActivity.this, AdminPanelActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Details Inserted Successfully", Toast.LENGTH_SHORT).show();
    }

    int lName = Investors.setLastName(lastName);
    int fName = Investors.setFirstName(firstName);
    int initial = Investors.getMiddleName(middleName);
    String names = lName +"" fName+"" initial;

    amountInvested1 = (EditText) findViewById(R.id.amountInvested);
    final String amountInvested =
            amountInvested.getText().toString().trim();
    date1 = (EditText) findViewById(R.id.date_of_completion);

    duration1 = (EditText) findViewById(R.id.circle);
    final String duration = duration1.getText().toString().trim();


    final String others = others1.getText().toString().trim();
    others1 = (EditText) findViewById(R.id.others);


    final String userId = userId1.getText().toString().trim();
    userId1 = (TextView) findViewById(R.id.userId);

    approver1 = (TextView) findViewById(R.id.approver);
    final String approver = approver1.getText().toString();

        if (amountInvested1.isEmpty()) {
        amountInvested1.setError("Amount Invested cannot be left empty!");
        return;
    }
        if (duration1.isEmpty()) {
        duration1.setError("Circle of Investment cannot be left empty!");
        return;
    }
        if (approver1.isEmpty()) {
        approver1.setError("Officer's Name cannot be left empty!");
        return;
    }


    type = (Spinner) findViewById(R.id.type);
    ArrayAdapter<CharSequence> adapterType = ArrayAdapter.
            createFromResource(this, R.array.type,
                    android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapterType);
        type.setOnItemSelectedListener(this);


    office = (Spinner) findViewById(R.id.office);
    ArrayAdapter<CharSequence> adapterOffice = ArrayAdapter.
            createFromResource(this, R.array.office,
                    android.R.layout.simple_spinner_item);
        adapterOffice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        office.setAdapter(adapterOffice);
        office.setOnItemSelectedListener(this);

    interestRate = (Spinner) findViewById(R.id.interestRate);
    ArrayAdapter<CharSequence> adapterInterestRate = ArrayAdapter.
            createFromResource(this, R.array.interestRate,
                    android.R.layout.simple_spinner_item);
        adapterInterestRate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interestRate.setAdapter(adapterInterestRate);
        interestRate.setOnItemSelectedListener(this);



    currency = (Spinner)findViewById(R.id.currency);

    ArrayAdapter adapterCurrency = ArrayAdapter.createFromResource(
            this, R.array.currency, android.R.layout.simple_spinner_item);
        adapterCurrency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currency.setAdapter(adapterCurrency);
        currency.setOnItemSelectedListener(this);



    remark = (Spinner) findViewById(R.id.remark);
    ArrayAdapter adapterRemark = ArrayAdapter.createFromResource(
            this, R.array.remark, android.R.layout.simple_spinner_item);
        adapterCurrency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        remark.setAdapter(adapterRemark);
        remark.setOnItemSelectedListener(this);


            rinaProgress.setMessage("Summiting Package, Please wait...");
            rinaProgress.show();

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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (view.getItemId()) {
            String text = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

        @Override
        public void onNothingSelected (AdapterView < ? > parent){

        }
    }




