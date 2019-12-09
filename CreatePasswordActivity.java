package group.lsg.resultinvestmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreatePasswordActivity extends AppCompatActivity {
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);
        Intent intent = getIntent();
        email = (String)
                intent.getSerializableExtra(RINALoginActivity.EMAIL_KEY);
    }

    /**
     * Creates a new account.
     * @param view the view
     */
    public void createAccount (View view) {
        EditText passwordText = (EditText)
                findViewById(R.id.create_password_field);
        String password = passwordText.getText().toString();
        if (password.isEmpty()) {
            passwordText.setError("Password cannot be left empty!");
            return;
        }

        if (Databank.investors.containsKey(email)){
            Databank.passwords.put(email, password);
        } else if (Databank.admins.containsKey(email)){
            Databank.passwords.put(email,password);
        }
        Toast toast = Toast.makeText(getApplicationContext(), "Account Updated!", Toast.LENGTH_LONG);
        toast.show();
        finish();
    }
}


