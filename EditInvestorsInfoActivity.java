package group.lsg.resultinvestmentapp.Class;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import group.lsg.resultinvestmentapp.R;

public class EditInvestorsInfoActivity extends AppCompatActivity {
    private static DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    private String email;
    String[] genderTypes = {"Male", "Female"};

    AutoCompleteTextView gender1;


    private EditText pin1, others1,state_of_residence1,address1;
    private EditText date_of_birth1,email1,phone_number1;
    private EditText middle_name1,firstname1,surname1,lga1;
    private TextView pin,others,state_of_residence,address,gender;
    private TextView date_of_birth,phone_number,middle_name, lga;
    private TextView firstname,surname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client_info);
        Intent intent = getIntent();
        email = (String)
                intent.getSerializableExtra(AdminPanelActivity.CLIENT_KEY);

        Investors client = Databank.clients.get(email);

        pin = (TextView) findViewById(R.id.pin);
        others = (TextView) findViewById(R.id.others);
        state_of_residence = (TextView) findViewById(R.id.state_of_residence);
        address = (TextView) findViewById(R.id.address);
        gender = (TextView) findViewById(R.id.gender);
        date_of_birth = (TextView) findViewById(R.id.date_of_birth);
        email = (EditText) findViewById(R.id.email);
        phone_number = (TextView) findViewById(R.id.phone_number);
        middle_name = (TextView) findViewById(R.id.middle_name);
        firstname = (TextView) findViewById(R.id.firstname);
        lga = (TextView) findViewById(R.id.lga);
        surname = (TextView) findViewById(R.id.surname);


        firstname1 = (EditText) findViewById(R.id.firstname1);
        firstname1.setHint(client.getFirstName());
        surname1 = (EditText) findViewById(R.id.surname1);
        surname1.setHint(client.getSurname());

        email1 = (EditText) findViewById(R.id.pin1);
        email1.setHint(Databank.passwords.get(email));
        gender1 = (AutoCompleteTextView) findViewById(R.id.gender1);
        gender1.setHint(client.getGender());

        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, genderTypes);

        gender1.setAdapter(adapter);
        gender1.setThreshold(1);



        state_of_residence1 = (EditText) findViewById(R.id.state_of_residence1);
        state_of_residence1.setHint(client.getState().toString());
        lga1= (EditText) findViewById(R.id.lga1);
        lga1.setHint(client.getLga());
        others1 = (EditText) findViewById(R.id.others1);
        others1.setHint(client.getOthers1());

        phone_number1 = (EditText) findViewById(R.id.phone_number1);
        phone_number1.setHint(client.getPhone());
        middle_name1 = (EditText) findViewById(R.id.middle_name1);
        middle_name1.setHint(client.getMiddleName());

        pin1 = (EditText) findViewById(R.id.pin1);
        pin1.setHint(Databank.passwords.get(pin));
        address1 = (EditText) findViewById(R.id.address1);
        address1.setHint(client.getAddress());
        date_of_birth1 = (EditText) findViewById(R.id.date_of_birth1);
        date_of_birth1.setHint(date.format(client.getDate()));
        Button update_button = (Button) findViewById(R.id.update_button);

    }


    public void updateClientInfo(View view) {
        String firstNameX = firstname1.getText().toString();
        if(firstNameX.isEmpty()) {
            firstNameX = firstname.getHint().toString();
        }
        String lastNameX = surname1.getText().toString();
        if(lastNameX.isEmpty()) {
            lastNameX = surname1.getHint().toString();
        }
        String passwordX = pin1.getText().toString();
        if(passwordX.isEmpty()) {
            passwordX = pin1.getHint().toString();
        }
        String addressX = address1.getText().toString();
        if(addressX.isEmpty()) {
            addressX = address1.getHint().toString();
        }
        String middleNameX = middle_name1.getText().toString();
        if(middleNameX.isEmpty()) {
            middleNameX = middle_name1.getHint().toString();
        }
        String stateX = state_of_residence1.getText().toString();
        if(stateX.isEmpty()) {
            stateX = state_of_residence1.getHint().toString();
        }
        String genderX = gender1.getText().toString();
        if(genderX.isEmpty()) {
            genderX = gender1.getHint().toString();
        }
        String date_of_birthX = date_of_birth1.getText().toString();
        if(date_of_birthX.isEmpty()) {
            date_of_birthX = date_of_birth1.getHint().toString();
        }
        String emailX = email1.getText().toString();
        if(emailX.isEmpty()) {
            emailX = email1.getHint().toString();
        }
        String phoneX = phone_number1.getText().toString();
        if(phoneX.isEmpty()) {
            phoneX = phone_number1.getHint().toString();
        }
        String lgaX = lga1.getText().toString();
        if(lgaX.isEmpty()) {
            lgaX = lga1.getHint().toString();
        }

        Investors investor = null;
        try {

            Date BirthDate = date.parse(date_of_birthX);
            investor = new Investors(lastNameX,
                    firstNameX,middleNameX,genderX, lgaX, phoneX, emailX,
                    date_of_birthX,genderX, stateX,addressX,pinX);
            Constants.Database.investors.put(email, investor);
            Constants.Database.passwords.put(email, pin);

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Investor Updated!",
                    Toast.LENGTH_LONG);
            toast.show();

            finish();
        } catch (NumberFormatException e1) {
            creditCardNumberText.setError("Incorrect Format!");
            return;
        } catch (ParseException e1) {
            expiryDateText.setError("Incorrect Format!");
            return;
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

    // Prevents the user from pressing enter
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_ENTER)
        {
            // Ignore the enter key
            return true;
        }

        // Handle all other the keys in the default way
        return super.onKeyDown(keyCode, event);
    }
}
}
