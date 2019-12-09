package group.lsg.resultinvestmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RINATimerView extends AppCompatActivity implements View.OnClickListener {
    private static TextView months, days, hours, mins, seconds;
    private static Button b, resetTimer, startTimer;
    private static CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rinatimer_view);
        months = (TextView) findViewById(R.id.month);
        days = (TextView) findViewById(R.id.day);
        hours = (TextView) findViewById(R.id.hour);
        mins = (TextView) findViewById(R.id.min);

        b = (Button) findViewById(R.id.stop_timer);
        b.setText("cancel");
        resetTimer = (Button) findViewById(R.id.reset);
        b.setText("reset timer");
        setListeners();

    }

    private void setListeners() {
        startTimer.setOnClickListener(this);
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stop:
                if (countDownTimer == null) {
                    String getMinutes = minutes.getText().toString();
                    if (!getMinutes.equals("") && getMinutes.length() > 0) {
                        int noOfMinutes = Integer.parseInt(getMinutes)
                                * 60 * 1000;//Convert minutes into milliseconds

                        startTimer(noOfMinutes);
                        startTimer.setText(getString(R.string.stop_timer));

                    } else
                        Toast.makeText(RINATimerView.this, "Please enter no. of Minutes.", Toast.LENGTH_SHORT).show();
                } else {
                    //Else stop timer and change text

                }
        }
    }
}
