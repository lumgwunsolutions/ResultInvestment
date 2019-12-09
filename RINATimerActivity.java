package group.lsg.resultinvestmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class RINATimerActivity extends AppCompatActivity implements View.OnClickListener {
    private static TextView countdownTimerText;
    private static EditText minutes;
    private static Button startTimer, resetTimer;
    private static CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinatimer);
        Intent intent = getIntent();
        email = (String) intent.getSerializableExtra(MainActivity.
                EMAIL_KEY);

        countdownTimerText = (TextView) findViewById(R.id.countdownText);
        minutes = (EditText) findViewById(R.id.enterMinutes);
        startTimer = (Button) findViewById(R.id.startTimer);
        resetTimer = (Button) findViewById(R.id.resetTimer);

        setListeners();

    }
    //Set Listeners over button
    private void setListeners() {
        startTimer.setOnClickListener(this);
        resetTimer.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startTimer:
                //If CountDownTimer is null then start timer
                if (countDownTimer == null) {
                    String getMinutes = minutes.getText().toString();
                    if (!getMinutes.equals("") && getMinutes.length() > 0) {
                        int noOfMinutes = Integer.parseInt(getMinutes)
                                * 60 * 1000;//Convert minutes into milliseconds

                        startTimer(noOfMinutes);
                        startTimer.setText(getString(R.string.stopTimer));

                    } else
                        Toast.makeText(RINATimerActivity.this, "Please enter no. of Minutes.", Toast.LENGTH_SHORT).show();
                } else {
                    //Else stop timer and change text
                    stopCountdown();
                    startTimer.setMovementMethod(new ScrollingMovementMethod());
                    startTimer.setText(getString(R.string.startTimer));
                }
                break;
            case R.id.resetTimer:
                stopCountdown();//stop count down

                startTimer.setText(getString(R.string.startTimer));//Change text toStart Timer

                countdownTimerText.setText(getString(R.string.timer));//Change Timertext
                break;
        }


    }


    //Stop Countdown method
    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    //Start Countodwn method
    private void startTimer(int noOfMinutes) {
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                @SuppressLint("DefaultLocale") String hms = String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millis),
                        TimeUnit.MILLISECONDS.toMinutes(millis) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                        TimeUnit.MILLISECONDS.toSeconds(millis) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                countdownTimerText.setText(hms);//set text
            }

            public void onFinish() {

                countdownTimerText.setText("TIME'S UP!!"); //On finishchange timer text
                        countDownTimer = null;//set CountDownTimer to null

                startTimer.setText(getString(R.string.starTimer));//Change buttontext
            }
        }.start();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (resultCode == RESULT_OK) {
            Uri selectedFileURI = data.getData();
            filePath = getRealPathFromURI(selectedFileURI);
            if (requestCode == FLIGHT_REQUEST_CODE) {
                File flightsFile = new File(filePath);
                Intent intent = new Intent(this,
                        ConfirmUploadFlightActivity.class);
                intent.putExtra(FLIGHT_KEY, flightsFile);
                startActivity(intent);
            } else if (requestCode == CLIENT_REQUEST_CODE) {
                File clientsFile = new File(filePath);
                Intent intent = new Intent(this,
                        ConfirmUploadClientActivity.class);
                intent.putExtra(CLIENT_KEY, clientsFile);
                startActivity(intent);
            }
        }
    }

    /**
     * Gets path from Uri
     * @param uri the Uri
     */
    public String getRealPathFromURI(Uri uri) {
        final String docId = DocumentsContract.getDocumentId(uri);
        final String[] split = docId.split(":");
        final String type = split[0];
        return Environment.getExternalStorageDirectory() + "/" + split[1];

    }



}
