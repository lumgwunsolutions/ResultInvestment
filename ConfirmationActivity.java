package group.lsg.resultinvestmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import group.lsg.resultinvestmentapp.Class.PrefManager;

public class ConfirmationActivity extends AppCompatActivity {
    private Button rinaConfirmBtn;
    private CheckBox rinaCheckBox;
    private TextView textV1;
    private TextView textV2;
    private TextView textV3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        textV1 = (TextView) findViewById(R.id.text1);
        textV2 = (TextView) findViewById(R.id.text2);
        textV3 = (TextView) findViewById(R.id.text3);

        PrefManager prefManager = new PrefManager(getApplicationContext());
        if (prefManager.isFirstTimeLaunch()) {
            prefManager.setFirstTimeLaunch(false);
            rinaConfirmBtn = (Button) findViewById(R.id.confirmbtn);
            rinaCheckBox = (CheckBox) findViewById(R.id.checkBox2);


   rinaCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (compoundButton.isChecked()) {
                        rinaConfirmBtn.setEnabled(true);
                        rinaConfirmBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent signUpIntent = new
                                        Intent(ConfirmationActivity.this, PreLoginSignUpActivity.class);
                                signUpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(signUpIntent);
                            }
                        });


                    } else if (!compoundButton.isChecked()) { //If the check box is not clicked don 't do anything.

                        rinaConfirmBtn.setEnabled(false);
                    }
                }

            });

        }
    }
}

