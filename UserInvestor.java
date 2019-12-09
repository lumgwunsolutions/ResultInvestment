package group.lsg.resultinvestmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserInvestor extends ParentActivity implements
        View.OnClickListener{
    Button personal, corporate;
    private Intent intent;
    TextView headername;
    ImageView ic_back;
    String Allinfo;
    int GETRESULTADDEDPAYMENT = 100;
    boolean isforaddpayment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        isforaddpayment = getIntent().getBooleanExtra("isforaddpayment", false);
        personal=(Button) findViewById(R.id.personal1);
        corporate=(Button) findViewById(R.id.corporate1);
        Allinfo= getIntent().getExtras().getString("Allinfo");
        personal.setOnClickListener(this);
        corporate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == corporate){
            intent = new Intent(Payment.this,AddCorporatePaymentMethodeActivity.class);
            intent.putExtra("isforaddpayment", isforaddpayment);
            intent.putExtra("Allinfo", Allinfo);
            startActivity(intent);
        }
        else if(view == personal){
            intent = new Intent(Payment.this, PaymentMethodeActivity.class);
            intent.putExtra("isforaddpayment", isforaddpayment);
            intent.putExtra("Allinfo",Allinfo);
            intent.putExtra("accountType","P");
            intent.putExtra("email","");
            startActivity(intent);
        }

    }
}

