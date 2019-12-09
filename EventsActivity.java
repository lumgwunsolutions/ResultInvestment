package group.lsg.resultinvestmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Helper;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import group.lsg.resultinvestmentapp.Class.InvestmentEvents;

public class EventsActivity extends AppCompatActivity implements
        EnterEvent.OnFragmentInteractionListener,
        ViewEvent.OnFragmentInteractionListener{

    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    static List<InvestmentEvents> events = new ArrayList<>();

    public void onFragmentInteraction(Uri u) {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        findViewById(R.id.enterEvent).setVisibility(View.GONE);
        findViewById(R.id.viewEvent).setVisibility(View.GONE);
        Helper.SetBalance(this);

        // Custom adapter.
        adapter = new CusAdapter(this,
                android.R.layout.simple_list_item_1, listItems);

        // ListView ID.
        final ListView lv = findViewById(R.id.list);

        lv.setAdapter(adapter);

        // When the list view item is clicked.
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View
                    view, int i, long l) {

                findViewById(R.id.viewEvent).setVisibility(View.VISIBLE);
                findViewById(R.id.list).setVisibility(View.GONE);
                findViewById(R.id.addNew).setVisibility(View.GONE);
                String s = (String) adapterView.getItemAtPosition(i);
                Log.d("mytag", s);

                // Use HashMap. This will do for now.
                for (InvestmentEvents ie : events) {

                    if (ie.title == s) {

                        EditText t = findViewById(R.id.Title2);
                        t.setText(ie.title);
                        EditText d = findViewById(R.id.description);
                        d.setText(ie.description);
                        EditText dt = findViewById(R.id.date_event);
                        dt.setText(ie.date);
                        EditText a = findViewById(R.id.amount);
                        a.setText(ie.amount);
                        EditText o = findViewById(R.id.others);
                        o.setText(ie.others);
                    }
                }

            }
        });

        Button addNewBtn = findViewById(R.id.addNew);

        addNewBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                findViewById(R.id.enterEvent).setVisibility(View.VISIBLE);
                findViewById(R.id.list).setVisibility(View.GONE);
                findViewById(R.id.addNew).setVisibility(View.GONE);
            }
        });

        Button submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {

            // Adding to the database when the user creates an event.
            @Override
            public void onClick(View v) {

                // FirebaseAuth xAuth = FirebaseAuth.getInstance();
                DatabaseReference xDatabase =
                        FirebaseDatabase.getInstance().getReference().child("Events");

                EditText title = findViewById(R.id.title);
                EditText description = findViewById(R.id.description);
                EditText date1 = findViewById(R.id.date);
                EditText amount = findViewById(R.id.amount);
                EditText others = findViewById(R.id.others);

                xDatabase.child(title.getText().toString()).child("description").setValue(description.getText().toString());

                xDatabase.child(title.getText().toString()).child("others").setValue(others.getText().toString());

                xDatabase.child(title.getText().toString()).child("amount").setValue(amount.getText().toString());

                xDatabase.child(title.getText().toString()).child("date").setValue(date.getText().toString());

                findViewById(R.id.enterEvent).setVisibility(View.GONE);
                findViewById(R.id.list).setVisibility(View.VISIBLE);
                findViewById(R.id.addNew).setVisibility(View.VISIBLE);
                Toast.makeText(EventsActivity.this, "Your submission will be reviewed by an admin.",
                        Toast.LENGTH_LONG).show();
            }
        });

        Button invest = findViewById(R.id.invest);

        invest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                findViewById(R.id.viewEvent).setVisibility(View.GONE);
                findViewById(R.id.list).setVisibility(View.VISIBLE);
                findViewById(R.id.addNew).setVisibility(View.VISIBLE);


                String investmentAmount = ((EditText)
                        findViewById(R.id.investmentAmount)).getText().toString();
                String investmentPackage = ((Spinner)
                        findViewById(R.id.investmentPackage)).getText().toString();
                String title = ((EditText)
                        findViewById(R.id.investmentTitle2)).getText().toString();

                // Error checking to allow user to invest only if theuser has enough funds.
                if (Integer.parseInt(investmentAmount) >
                        Integer.parseInt(((TextView) findViewById(R.id.balance))
                                .getText()
                                .toString()
                                .replace("N", "")
                                .replace("Balance: ", ""))) {

                    Toast.makeText(EventsActivity.this, "You don't have enough balance.",
                            Toast.LENGTH_LONG).show();

                    return;
                }

                Toast.makeText(EventsActivity.this, "You will get notified about your investment. Thank You for partnering with Us.",
                        Toast.LENGTH_LONG).show();

                // RNG embedded class to determine the outcome of the
                investment.class InvestmentResult extends AsyncTask<String, Void, Void> {
                    AppCompatActivity a;
                    boolean succ = false;
                    final String eventTitle;
                    String investmentPackage;
                    int investmentAmount;
                    double percentageIncrease;
                    String eventDate;

                    public InvestmentResult(AppCompatActivity a,String
                            eventTitle ,String investmentPackage,int investmentAmount,double percentageIncrease, String eventDate) {
                        this.a = a;
                        this.eventTitle = eventTitle;
                        this.eventDate = eventDate;
                        this.percentageIncrease = percentageIncrease;
                        this.investmentPackage = investmentPackage;
                        this.investmentAmount = investmentAmount;
                    }

                    protected Void doInBackground(String... event) {

                        try {

                            eventTitle = event[0];
                            investmentAmount = Integer.parseInt(event[1]);
                            Thread.sleep(20000);

                        } catch (InterruptedException e) {

                            // Some error.
                        }

                        return null;
                    }

                    protected void onPostExecute(Void unused) {

                        Random r = new Random();

                        if (r.nextBoolean()) {

                            Toast.makeText(a, "Your Investment of the " + investmentPackage + " has received " +
                                    Integer.toString((int) percentageIncrease) + " Naira Dividend.",
                                    Toast.LENGTH_LONG).show();

                            Helper.AddBalance(a, investmentAmount +
                                    investmentAmount*percentageIncrease );

                        }
                        DatabaseReference event =
                                FirebaseDatabase.getInstance().getReference().child("Events").child(eventTitle);
                        event.removeValue();

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            events.removeIf(new Predicate<EventsActivity>() {

                                @Override
                                public boolean test(EventsActivity iEvent) {

                                    if (iEvent.title.equals(eventTitle))
                                        return true;
                                    return false;
                                }
                            });
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
                InvestmentResult br = new InvestmentResult(EventsActivity.this);
                br.execute(title, investmentAmount);

            }
        });

        DatabaseReference db = FirebaseDatabase.getInstance().getReference()
                .child("Events");

        db.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot event : dataSnapshot.getChildren()) {

                    addItems(event.getKey());
                    EventsActivity be = new EventsActivity();
                    be.title = event.getKey();
                    be.description = ((HashMap<String, String>)
                            event.getValue()).get("description");
                    events.add(be);
                }
            }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    // ...
                }
            });

        public void addItems(String s) {

            listItems.add(s);
            adapter.notifyDataSetChanged();
        }

        class CusAdapter extends ArrayAdapter<String> implements
                View.OnClickListener {

            CusAdapter(Context c, int x, ArrayList<String> s) { super(c, x, s); }

            @Override
            public void onClick(View v) {

                int position = (Integer) v.getTag();
                Object object = getItem(position);

                String teams = (String) object;
                Log.d("mytag", teams);
            }
        }

    }
}

