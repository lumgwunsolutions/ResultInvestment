package group.lsg.resultinvestmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import group.lsg.resultinvestmentapp.Class.DatabaseHelper;
import group.lsg.resultinvestmentapp.Class.Investors;
import group.lsg.resultinvestmentapp.Class.MyDividerItemDecoration;
import group.lsg.resultinvestmentapp.Class.RecyclerTouchListener;

public class InvestorsListActivity extends AppCompatActivity {
    private NotesAdapter mAdapter;
    private List<Investors> investorsList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noInvestorsView;

    private DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investors_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coordinatorLayout = findViewById(R.id.coordinator_layout);
        recyclerView = findViewById(R.id.recycler_view);
        noInvestorsView = findViewById(R.id.empty_investors_view);

        db = new DatabaseHelper(this);

        investorsList.addAll(db.getAllInvestors());

        FloatingActionButton fab = (FloatingActionButton)
                findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInvestorsDialog(false, null, -1);
            }
        });

        mAdapter = new InvestorsAdapter(this, investorsList);
        RecyclerView.LayoutManager mLayoutManager = new
                LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new
                MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        toggleEmptyInvestors();


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));
    }


    private void createInvestors(String rinaInvestors) {

        long id = db.insertInvestors(rinaInvestors);

        Investors investors = db.getInvestors(id);

        if (investors != null) {
            investorsList.add(0, investors);

            mAdapter.notifyDataSetChanged();

            toggleEmptyInvestors();
        }
    }


    private void updateInvestors(String rinaInvestors, int position) {
        Investors investors = investorsList.get(position);
        investors.setNote(rinaInvestors);

        db.updateInvestors(investors);

        investorsList.set(position, investors);
        mAdapter.notifyItemChanged(position);

        toggleEmptyInvestors();
    }


    private void deleteInvestors(int position) {
        db.deleteInvestors(investorsList.get(position));

        investorsList.remove(position);
        mAdapter.notifyItemRemoved(position);

        toggleEmptyInvestors();
    }


    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete",
                "Add Payment Details" ,"Add Package Details"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showInvestorsDialog(true,
                            investorsList.get(position), position);
                } else {
                    deleteInvestors(position);
                }
            }
        });
        builder.show();
    }

    private void showInvestorsDialog(final boolean shouldUpdate,
                                     final Investors investors, final int position) {
        LayoutInflater layoutInflaterAndroid =
                LayoutInflater.from(getApplicationContext());
        View view =
                layoutInflaterAndroid.inflate(R.layout.investors_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new
                AlertDialog.Builder(InvestorsListActivity.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputInvestors = view.findViewById(R.id.investors);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString("Investors's Name") : getString(R.string."Edit Investor"));

        if (shouldUpdate && investors != null) {
            inputInvestors.setText(investors.getInvestors());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "update" : "save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {

                            }
                        })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface
                                                        dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(inputInvestors.getText().toString())) {
                    Toast.makeText(InvestorsListActivity.this, "Enter Investors!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }
                // check if user updating investors
                if (shouldUpdate && investors != null) {
                    updateInvestors(inputInvestors.getText().toString(), position);
                } else {
                    // create new note
                    createInvestors(inputInvestors.getText().toString()); } }
        });
    }


    private void toggleEmptyInvestors() {
        // you can check investorsList.size() > 0

        if (db.getInvestorsCount() > 0) {
            noInvestorsView.setVisibility(View.GONE);
        } else {
            noInvestorsView.setVisibility(View.VISIBLE);
        }
    }
}
