package group.lsg.resultinvestmentapp.Class;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.google.firebase.database.snapshot.LeafNode.LeafType.String;
import static group.lsg.resultinvestmentapp.Class.InvestmentPackage.COLUMN_APPROVER;
import static group.lsg.resultinvestmentapp.Class.InvestmentPackage.COLUMN_APPROVING_OFFICE;
import static group.lsg.resultinvestmentapp.Class.InvestmentPackage.COLUMN_DATE_OF_ACTIVATION;
import static group.lsg.resultinvestmentapp.Class.InvestmentPackage.COLUMN_DATE_OF_COMPLETION;
import static group.lsg.resultinvestmentapp.Class.InvestmentPackage.COLUMN_REMARKS;
import static group.lsg.resultinvestmentapp.Class.InvestmentPackage.COLUMN_TYPE;
import static group.lsg.resultinvestmentapp.Class.Investors.COLUMN_INVESTORS_PHONE_NUMBER;
import static group.lsg.resultinvestmentapp.Class.Investors.COLUMN_MIDDLENAME;
import static group.lsg.resultinvestmentapp.Class.Investors.COLUMN_STATE;
import static group.lsg.resultinvestmentapp.Class.NextOfKin.COLUMN_FIRSTNAME;
import static group.lsg.resultinvestmentapp.Class.NextOfKin.COLUMN_GENDER;
import static group.lsg.resultinvestmentapp.Class.NextOfKin.COLUMN_SURNAME;
import static group.lsg.resultinvestmentapp.Class.Queries.COLUMN_ID;
import static group.lsg.resultinvestmentapp.Class.Queries.COLUMN_INVESTORS_EMAIL_ADDRESS;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_INVESTOR_PACKAGE = "inv_pack";

    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    private static final String CREATE_TABLE_INVESTOR_PACKAGE = "CREATE TABLE "
            + TABLE_INVESTOR_PACKAGE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + Investors.COLUMN_ID + " INTEGER," +
            InvestmentPackage.COLUMN_ID + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    // Database Name
    private static final String DATABASE_NAME = "rina_db";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(InvestmentEvents.CREATE_TABLE_INVESTMENT_EVENTS);
        //db.execSQL(Investors.CREATE_TABLE_INVESTORS);
        db.execSQL(InvestmentPackage.CREATE_TABLE_INVESTMENT_PACKAGE);
        db.execSQL(CREATE_TABLE_INVESTOR_PACKAGE);
        db.execSQL(Revenues.CREATE_TABLE_REVENUES);


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Revenues.CREATE_TABLE_REVENUES);
        db.execSQL("DROP TABLE IF EXISTS " +
                InvestmentEvents.CREATE_TABLE_INVESTMENT_EVENTS);

        db.execSQL("DROP TABLE IF EXISTS " + Investors.CREATE_TABLE_INVESTORS);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_INVESTOR_PACKAGE);
        db.execSQL("DROP TABLE IF EXISTS " +
                InvestmentPackage.CREATE_TABLE_INVESTMENT_PACKAGE);


        // Create tables again
        onCreate(db);
    }
    public long createInvestorsAndPackage(long inv_id, long package_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Investors.COLUMN_ID, inv_id);
        values.put(InvestmentPackage.COLUMN_ID, package_id);
        values.put(KEY_CREATED_AT, getDateTime());

        long id = db.insert(TABLE_INVESTOR_PACKAGE, null, values);

        return id;
    }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


    public long insertRevenues(int id, float capital ,float
            interest,integer circle,  Date startDay, float total,String email) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values_revenue = new ContentValues();
        values_revenue.put(Revenues.COLUMN_INVESTORS_EMAIL_ADDRESS, email);
        values_revenue.put(Revenues.COLUMN_INITIAL_CAPITAL, capital);
        values_revenue.put(Revenues.COLUMN_INTEREST, interest);
        values_revenue.put(Revenues.COLUMN_CIRCLE, circle);
        values_revenue.put(Revenues.COLUMN_TOTAL, total);
        values_revenue.put(Revenues.COLUMN_START_DAY, start);
        values_revenue.put(Revenues.COLUMN_END_DAY, finish);
        values_revenue.put(Revenues.COLUMN_TIMESTAMP, timestamp);
        // insert row
        long id = db.insert(Revenues.TABLE_NAME, null, values_revenue);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
    public long insertInvestmentEvents(String event, String
            description,integer amount,String others) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values_event2 = new ContentValues();
        values_event2.put(InvestmentEvents.COLUMN_TITLE, event);
        values_event2.put(InvestmentEvents.COLUMN_DESCRIPTION, description);
        values_event2.put(InvestmentEvents.COLUMN_AMOUNT, amount);
        values_event2.put(InvestmentEvents.COLUMN_OTHERS, others);
        // insert row
        long id = db.insert(InvestmentEvents.TABLE_NAME, null, values_event2);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
    public long insertInvestmentPackages(int id_ , String type,
                                         double interestRate, double amountInvested, String duration,
                                         Date dateOfActivation, Date dateOfCompletion, String approver,
                                         String office, String remarks, String others) {

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values_package = new ContentValues();
        values_package.put(COLUMN_TYPE, type);
        values_package.put(InvestmentPackage.COLUMN_AMOUNT_OF_INVESTMENT,
                interestRate);
        values_package.put(InvestmentPackage.COLUMN_CURRENCY, amountInvested);
        values_package.put(InvestmentPackage.COLUMN_INTEREST_RATE,
                interestRate);
        values_package.put(COLUMN_DATE_OF_ACTIVATION,
                dateOfActivation);
        values_package.put(COLUMN_DATE_OF_COMPLETION,
                dateOfCompletion);
        values_package.put(COLUMN_APPROVER, approver);
        values_package.put(COLUMN_APPROVING_OFFICE, office);
        values_package.put(COLUMN_REMARKS, remarks);
        values_package.put(InvestmentPackage.COLUMN_OTHERS, others);
        // insert row
        long id = db.insert(InvestmentPackage.TABLE_NAME, null, values_package);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
    public void insertInvestors(int id_ ,String surname,
                                String first,String middlename, String phone,String email,
                                String state, String gender,String dob,
                                String photo, String amount, String others,String time) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values_investors = new ContentValues();
        values_investors.put(Investors.COLUMN_SURNAME, surname);
        values_investors.put(Investors.COLUMN_FIRSTNAME, first);
        values_investors.put(COLUMN_MIDDLENAME,middlename);
        values_investors.put(COLUMN_INVESTORS_PHONE_NUMBER, phone);
        values_investors.put(Investors.COLUMN_INVESTORS_EMAIL_ADDRESS, email);
        values_investors.put(COLUMN_STATE, state);
        values_investors.put(Investors.COLUMN_GENDER, gender);
        values_investors.put(Investors.COLUMN_ADDRESS, address);
        values_investors.put(Investors.COLUMN_DATE_OF_BIRTH, dob);
        values_investors.put(Investors.COLUMN_PHOTO, photo);
        values_investors.put(Investors.COLUMN_LGA, lga);
        values_investors.put(Investors.COLUMN_OTHERS, others);
        values_investors.put(Investors.COLUMN_TIMESTAMP, time);

        // insert row
        long id = db.insert(Investors.TABLE_NAME, null, values_investors);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Revenues getRevenues(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor_revenue = db.query(Revenues.TABLE_NAME,
                new String[]{Revenues.COLUMN_ID,
                        Revenues.COLUMN_INVESTORS_EMAIL_ADDRESS,
                        Revenues.COLUMN_INITIAL_CAPITAL, Revenues.COLUMN_INTEREST,
                        Revenues.COLUMN_CIRCLE,Revenues.COLUMN_TOTAL,Revenues.COLUMN_START_DAY,Revenues.COLUMN_END_DAY,Revenues.COLUMN_TIMESTAMP},
                Revenues.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor_revenue != null)
            cursor_revenue.moveToFirst();

        // prepare revenue object
        Revenues revenue = new Revenues(

                cursor_revenue.getInt(cursor_revenue.getColumnIndex(Revenues.COLUMN_ID)),

                cursor_revenue.getString(cursor_revenue.getColumnIndex(Revenues.COLUMN_INVESTORS_EMAIL_ADDRESS)),

                cursor_revenue.getString(cursor_revenue.getColumnIndex(Revenues.COLUMN_INITIAL_CAPITAL)),
                cursor_revenue.getString(cursor_revenue.getColumnIndex(
                        Revenues.COLUMN_INTEREST)),

                cursor_revenue.getString(cursor_revenue.getColumnIndex(Revenues.COLUMN_CIRCLE)),

                cursor_revenue.getString(cursor_revenue.getColumnIndex(Revenues.COLUMN_TOTAL)),

                cursor_revenue.getString(cursor_revenue.getColumnIndex(Revenues.COLUMN_START_DAY)),
                cursor_revenue.getString(cursor_revenue.getColumnIndex(
                        Revenues.COLUMN_END_DAY)),

                cursor_revenue.getString(cursor_revenue.getColumnIndex(Revenues.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor_revenue.close();

        return revenue;
    }

    public InvestmentEvents getInvestmentEvents(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor_event = db.query(InvestmentEvents.TABLE_NAME,
                new String[]{InvestmentEvents.COLUMN_ID,
                        InvestmentEvents.COLUMN_TITLE, InvestmentEvents.COLUMN_DESCRIPTION,
                        InvestmentEvents.COLUMN_AMOUNT,
                        InvestmentEvents.COLUMN_OTHERS,InvestmentEvents.COLUMN_TIMESTAMP},
                InvestmentEvents.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor_event != null)
            cursor_event.moveToFirst();

        // prepare iE object
        InvestmentEvents iE = new InvestmentEvents(

                cursor_event.getInt(cursor_event.getColumnIndex(InvestmentEvents.COLUMN_ID)),

                cursor_event.getString(cursor_event.getColumnIndex(InvestmentEvents.COLUMN_TITLE)),

                cursor_event.getString(cursor_event.getColumnIndex(InvestmentEvents.COLUMN_DESCRIPTION)),
                cursor_event.getString(cursor_event.getColumnIndex(
                        InvestmentEvents.COLUMN_AMOUNT)),

                cursor_event.getString(cursor_event.getColumnIndex(InvestmentEvents.COLUMN_OTHERS)),

                cursor_event.getString(cursor_event.getColumnIndex(InvestmentEvents.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor_event.close();

        return iE;
    }
    public InvestmentPackage getPackages(long id) {

        SQLiteDatabase db = this.getReadableDatabase();



        Cursor cursor_package = db.query(InvestmentPackage.TABLE_NAME,
                new String[]{InvestmentPackage.COLUMN_ID,
                        COLUMN_TYPE,
                        InvestmentPackage.COLUMN_AMOUNT_OF_INVESTMENT,
                        InvestmentPackage.COLUMN_CURRENCY,InvestmentPackage.COLUMN_INTEREST_RATE,
                        COLUMN_DATE_OF_ACTIVATION,
                        COLUMN_DATE_OF_COMPLETION,
                        InvestmentPackage.COLUMN_TIME,
                        COLUMN_APPROVER, COLUMN_APPROVING_OFFICE,
                        COLUMN_REMARKS, InvestmentPackage.COLUMN_OTHERS,
                        InvestmentPackage.COLUMN_TIMESTAMP},
                InvestmentPackage.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor_package != null)
            cursor_package.moveToFirst();


        // prepare package object
        InvestmentPackage package = new InvestmentPackage(
                cursor_package.getInt(cursor_package.getColumnIndex(InvestmentPackage.COLUMN_ID)),
                cursor_package.getString(cursor_package.getColumnIndex(COLUMN_TYPE)),
                cursor_package.getString(cursor_package.getColumnIndex(InvestmentPackage.COLUMN_AMOUNT_OF_INVESTMENT)),
                cursor_package.getString(cursor_package.getColumnIndex(InvestmentPackage.COLUMN_CURRENCY)),
                cursor_package.getString(cursor_package.getColumnIndex(InvestmentPackage.COLUMN_INTEREST_RATE)),
                cursor_package.getString(cursor_package.getColumnIndex(COLUMN_DATE_OF_ACTIVATION)),
                cursor_package.getString(cursor_package.getColumnIndex(COLUMN_DATE_OF_COMPLETION)),
                cursor_package.getString(cursor_package.getColumnIndex(InvestmentPackage.COLUMN_TIME)),
                cursor_package.getString(cursor_package.getColumnIndex(COLUMN_APPROVER)),
                cursor_package.getString(cursor_package.getColumnIndex(COLUMN_APPROVING_OFFICE)),
                cursor_package.getString(cursor_package.getColumnIndex(COLUMN_REMARKS)),
                cursor_package.getString(cursor_package.getColumnIndex(InvestmentPackage.COLUMN_OTHERS)),
                cursor_package.getString(cursor_package.getColumnIndex(InvestmentPackage.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor_package.close();

        return package;

    }

    public Investors getInvestors(long id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor_investor = db.query(Investors.TABLE_NAME,
                new String[]{Investors.COLUMN_ID, Investors.COLUMN_SURNAME,
                        Investors.COLUMN_FIRSTNAME,
                        COLUMN_MIDDLENAME, COLUMN_INVESTORS_PHONE_NUMBER,
                        Investors.COLUMN_INVESTORS_EMAIL_ADDRESS, COLUMN_STATE,
                        Investors.COLUMN_LGA, Investors.COLUMN_GENDER,
                        Investors.COLUMN_ADDRESS,Investors.COLUMN_DATE_OF_BIRTH,
                        Investors.COLUMN_PHOTO,Investors.COLUMN_OTHERS,
                        Investors.COLUMN_NEXTOFKIN,Investors.COLUMN_TIMESTAMP},
                Investors.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor_investor != null)
            cursor_investor.moveToFirst();

        // prepare investors object
        Investors investors = new Investors(
                cursor_investor.getInt(cursor_investor.getColumnIndex(Investors.COLUMN_ID)),
                cursor_investor.getString(cursor_investor.getColumnIndex(Investors.COLUMN_SURNAME)),
                cursor_investor.getString(cursor_investor.getColumnIndex(Investors.COLUMN_FIRSTNAME)),
                cursor_investor.getString(cursor_investor.getColumnIndex(COLUMN_MIDDLENAME)),
                cursor_investor.getString(cursor_investor.getColumnIndex(COLUMN_INVESTORS_PHONE_NUMBER)),
                cursor_investor.getString(cursor_investor.getColumnIndex(Investors.COLUMN_INVESTORS_EMAIL_ADDRESS)),
                cursor_investor.getString(cursor_investor.getColumnIndex(COLUMN_STATE)),
                cursor_investor.getString(cursor_investor.getColumnIndex(Investors.COLUMN_LGA)),
                cursor_investor.getString(cursor_investor.getColumnIndex(Investors.COLUMN_GENDER)),
                cursor_investor.getString(cursor_investor.getColumnIndex(Investors.COLUMN_ADDRESS)),
                cursor_investor.getString(cursor_investor.getColumnIndex(Investors.COLUMN_DATE_OF_BIRTH)),
                cursor_investor.getString(cursor_investor.getColumnIndex(Investors.COLUMN_PHOTO)),
                cursor_investor.getString(cursor_investor.getColumnIndex(Investors.COLUMN_OTHERS)),
                cursor_investor.getString(cursor_investor.getColumnIndex(Investors.COLUMN_NEXTOFKIN)),
                cursor_investor.getString(cursor_investor.getColumnIndex(Investors.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor_investor.close();

        return investors;

    }


    public List<Revenues> getAllRevenues() {
        List<Revenues> iRevenues = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Revenues.TABLE_NAME +
                " ORDER BY " +
                Revenues.COLUMN_START_DAY + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor revenue1 = db.rawQuery(selectQuery, null);
        if (revenue1.moveToFirst()) {
            do {
                Revenues rev = new Revenues();


                rev.setId(revenue1.getInt(revenue1.getColumnIndex(Revenues.COLUMN_ID)));

                rev.setInvestmentEvents(revenue1.getString(revenue1.getColumnIndex(Revenues.COLUMN_INVESTORS_EMAIL_ADDRESS)));

                rev.setInvestmentEvents(revenue1.getString(revenue1.getColumnIndex(Revenues.COLUMN_INITIAL_CAPITAL)));

                rev.setInvestmentEvents(revenue1.getString(revenue1.getColumnIndex(Revenues.COLUMN_INTEREST)));

                rev.setInvestmentEvents(revenue1.getString(revenue1.getColumnIndex(Revenues.COLUMN_CIRCLE)));

                rev.setInvestmentEvents(revenue1.getString(revenue1.getColumnIndex(Revenues.COLUMN_TOTAL)));

                rev.setInvestmentEvents(revenue1.getString(revenue1.getColumnIndex(Revenues.COLUMN_START_DAY)));

                rev.setInvestmentEvents(revenue1.getString(revenue1.getColumnIndex(Revenues.COLUMN_END_DAY)));
                rev.setTimestamp(revenue1.getString(revenue1.
                        getColumnIndex(Revenues.COLUMN_TIMESTAMP)));

                iRevenues.add(rev);
            } while (revenue1.moveToNext());
        }

        // close db connection
        db.close();

        // return iRevenues list
        return iRevenues;
    }

    public List<InvestmentEvents> getAllInvestmentEvents() {
        List<InvestmentEvents> iEs = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " +
                InvestmentEvents.TABLE_NAME + " ORDER BY " +
                InvestmentEvents.COLUMN_TITLE + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor event1 = db.rawQuery(selectQuery, null);
        if (event1.moveToFirst()) {
            do {
                InvestmentEvents iE = new InvestmentEvents();


                iE.setId(event1.getInt(event1.getColumnIndex(InvestmentEvents.COLUMN_ID)));

                iE.setInvestmentEvents(event1.getString(event1.getColumnIndex(InvestmentEvents.COLUMN_TITLE)));

                iE.setInvestmentEvents(event1.getString(event1.getColumnIndex(InvestmentEvents.COLUMN_DESCRIPTION)));

                iE.setInvestmentEvents(event1.getString(event1.getColumnIndex(InvestmentEvents.COLUMN_AMOUNT)));

                iE.setInvestmentEvents(event1.getString(event1.getColumnIndex(InvestmentEvents.COLUMN_OTHERS)));
                iE.setTimestamp(event1.getString(event1.
                        getColumnIndex(InvestmentEvents.COLUMN_TIMESTAMP)));

                iEs.add(iE);
            } while (event1.moveToNext());
        }

        // close db connection
        db.close();

        // return iEs list
        return iEs;
    }
    public List<Investors> getAllInvestors() {
        List<Investors> investors = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Investors.TABLE_NAME
                + " ORDER BY " +
                Investors.COLUMN_SURNAME + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor_e = db.rawQuery(selectQuery, null);
        if (cursor_e.moveToFirst()) {
            do {
                Investors inv = new Investors();

                inv.setId(cursor_e.getInt(cursor_e.getColumnIndex(Investors.COLUMN_ID)));

                inv.setInvestors(cursor_e.getString(cursor_e.getColumnIndex(Investors.COLUMN_SURNAME)));

                inv.setInvestors(cursor_e.getString(cursor_e.getColumnIndex(Investors.COLUMN_FIRSTNAME)));

                inv.setInvestors(cursor_e.getString(cursor_e.getColumnIndex(COLUMN_MIDDLENAME)));

                inv.setInvestors(cursor_e.getString(cursor_e.getColumnIndex(COLUMN_INVESTORS_PHONE_NUMBER)));

                inv.setInvestors(cursor_e.getString(cursor_e.getColumnIndex(Investors.COLUMN_INVESTORS_EMAIL_ADDRESS)));

                inv.setInvestors(cursor_e.getString(cursor_e.getColumnIndex(COLUMN_STATE)));

                inv.setInvestors(cursor_e.getString(cursor_e.getColumnIndex(Investors.COLUMN_LGA)));

                inv.setInvestors(cursor_e.getString(cursor_e.getColumnIndex(Investors.COLUMN_GENDER)));

                inv.setInvestors(cursor_e.getString(cursor_e.getColumnIndex(Investors.COLUMN_ADDRESS)));

                inv.setInvestors(cursor_e.getString(cursor_e.getColumnIndex(Investors.COLUMN_DATE_OF_BIRTH)));

                inv.setInvestors(cursor_e.getString(cursor_e.getColumnIndex(Investors.COLUMN_PHOTO)));

                inv.setInvestors(cursor_e.getString(cursor_e.getColumnIndex(Investors.COLUMN_NEXTOFKIN)));

                inv.setInvestors(cursor_e.getString(cursor_e.getColumnIndex(Investors.COLUMN_OTHERS)));
                inv.setInvestors(cursor_e.getString(cursor_e.
                        getColumnIndex(Investors.COLUMN_TIMESTAMP)));

                investors.add(inv);
            } while (cursor_e.moveToNext());
        }

        // close db connection
        db.close();

        // return investors list
        return investors;
    }

    public List<InvestmentPackage> getAllInvestmentPackages() {
        List<InvestmentPackage> iPs = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " +
                InvestmentPackage.TABLE_NAME + " ORDER BY " +
                COLUMN_TYPE + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor event1 = db.rawQuery(selectQuery, null);
        if (event1.moveToFirst()) {
            do {
                InvestmentPackage iP2 = new InvestmentPackage();


                iP2.setId(event1.getInt(event1.getColumnIndex(InvestmentPackage.COLUMN_ID)));

                iP2.setInvestmentEvents(event1.getString(event1.getColumnIndex(COLUMN_TYPE)));

                iP2.setInvestmentEvents(event1.getString(event1.getColumnIndex(InvestmentPackage.COLUMN_AMOUNT_OF_INVESTMENT)));

                iP2.setInvestmentEvents(event1.getString(event1.getColumnIndex(InvestmentPackage.COLUMN_CURRENCY)));

                iP2.setInvestmentEvents(event1.getString(event1.getColumnIndex(InvestmentPackage.COLUMN_INTEREST_RATE)));

                iP2.setInvestmentEvents(event1.getString(event1.getColumnIndex(COLUMN_DATE_OF_ACTIVATION)));

                iP2.setInvestmentEvents(event1.getString(event1.getColumnIndex(COLUMN_DATE_OF_COMPLETION)));

                iP2.setInvestmentEvents(event1.getString(event1.getColumnIndex(InvestmentPackage.COLUMN_TIME)));

                iP2.setInvestmentEvents(event1.getString(event1.getColumnIndex(COLUMN_APPROVER)));

                iP2.setInvestmentEvents(event1.getString(event1.getColumnIndex(COLUMN_APPROVING_OFFICE)));

                iP2.setInvestmentEvents(event1.getString(event1.getColumnIndex(COLUMN_REMARKS)));

                iP2.setInvestmentEvents(event1.getString(event1.getColumnIndex(InvestmentPackage.COLUMN_OTHERS)));
                iP2.setTimestamp(event1.getString(event1.
                        getColumnIndex(InvestmentPackage.COLUMN_TIMESTAMP)));

                iPs.add(iP2);
            } while (event1.moveToNext());
        }

        // close db connection
        db.close();

        // return iPs list
        return iPs;
    }



    public int getRevenuesCount() {
        String countQuery = "SELECT  * FROM " + Revenues.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor_revenue = db.rawQuery(countQuery, null);

        int count = cursor_revenue.getCount();
        cursor_revenue.close();
        return count;
    }

    public int getInvestorsCount() {
        String countQuery = "SELECT  * FROM " + Investors.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor_I = db.rawQuery(countQuery, null);

        int count = cursor_I.getCount();
        cursor_I.close();


        // return count
        return count;
    }
    public int getInvestmentPackageCount() {
        String countQuery = "SELECT  * FROM " + InvestmentPackage.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor_IP = db.rawQuery(countQuery, null);

        int count = cursor_IP.getCount();
        cursor_IP.close();


        // return count
        return count;
    }
    public int getInvestmentEventsCount() {
        String countQuery = "SELECT  * FROM " + InvestmentEvents.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor_IEV = db.rawQuery(countQuery, null);

        int count = cursor_IEV.getCount();
        cursor_IEV.close();


        // return count
        return count;
    }
    public int updateInvestors(Investors investors) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(Investors.COLUMN_SURNAME, investors.getInvestors());
        values.put(Investors.COLUMN_FIRSTNAME, investors.getInvestors());
        values.put(COLUMN_MIDDLENAME, investors.getInvestors());
        values.put(COLUMN_INVESTORS_PHONE_NUMBER,
                investors.getInvestors());
        values.put(Investors.COLUMN_INVESTORS_EMAIL_ADDRESS,
                investors.getInvestors());
        values.put(COLUMN_STATE, investors.getInvestors());
        values.put(Investors.COLUMN_GENDER, investors.getInvestors());
        values.put(Investors.COLUMN_ADDRESS, investors.getInvestors());
        values.put(Investors.COLUMN_DATE_OF_BIRTH, investors.getInvestors());
        values.put(Investors.COLUMN_PHOTO, investors.getInvestors());
        values.put(Investors.COLUMN_OTHERS, investors.getInvestors());
        values.put(Investors.COLUMN_NEXTOFKIN, investors.getInvestors());
        values.put(Investors.COLUMN_TIMESTAMP, investors.getInvestors());
        // updating row
        return db.update(Investors.TABLE_NAME, values,
                Investors.COLUMN_ID + " = ?",
                new String[]{String.valueOf(investors.getId())});
    }
    public int updateRevenues(Revenues revenue2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Revenues.COLUMN_INVESTORS_EMAIL_ADDRESS,
                revenue2.getRevenues());
        values.put(Revenues.COLUMN_INITIAL_CAPITAL, revenue2.getRevenues());
        values.put(Revenues.COLUMN_INTEREST, revenue2.getRevenues());
        values.put(Revenues.COLUMN_CIRCLE, revenue2.getRevenues());

        values.put(Revenues.COLUMN_TOTAL, revenue2.getRevenues());
        values.put(Revenues.COLUMN_START_DAY, revenue2.getRevenues());
        values.put(Revenues.COLUMN_END_DAY, revenue2.getRevenues());
        // updating row
        return db.update(Revenues.TABLE_NAME, values,
                Revenues.COLUMN_ID + " = ?",
                new String[]{String.valueOf(revenue2.getId())});
    }

    public int updateInvestmentEvents(InvestmentEvents iE) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(InvestmentEvents.COLUMN_TITLE, iE.getInvestmentEvents());
        values.put(InvestmentEvents.COLUMN_DESCRIPTION,
                iE.getInvestmentEvents());
        values.put(InvestmentEvents.COLUMN_AMOUNT, iE.getInvestmentEvents());
        values.put(InvestmentEvents.COLUMN_OTHERS, iE.getInvestmentEvents());
        // updating row
        return db.update(InvestmentEvents.TABLE_NAME, values,
                InvestmentEvents.COLUMN_ID + " = ?",
                new String[]{String.valueOf(iE.getId())});
    }

    public int updateInvestPackage(InvestmentPackage investmentPs) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(InvestmentPackage.COLUMN_ID, investmentPs.getPackage());
        values.put(COLUMN_TYPE, investmentPs.getPackage());
        values.put(InvestmentPackage.COLUMN_AMOUNT_OF_INVESTMENT,
                investmentPs.getPackage());
        values.put(InvestmentPackage.COLUMN_CURRENCY,
                investmentPs.getPackage());
        values.put(InvestmentPackage.COLUMN_INTEREST_RATE,
                investmentPs.getPackage());
        values.put(COLUMN_DATE_OF_ACTIVATION,
                investmentPs.getPackage());
        values.put(COLUMN_DATE_OF_COMPLETION,
                investmentPs.getPackage());
        values.put(InvestmentPackage.COLUMN_TIME, investmentPs.getPackage());


        values.put(COLUMN_APPROVER,
                investmentPs.getPackage());
        values.put(COLUMN_APPROVING_OFFICE,
                investmentPs.getPackage());
        values.put(COLUMN_REMARKS, investmentPs.
                getPackage());
        values.put(InvestmentPackage.COLUMN_OTHERS, investmentPs.getPackage());
        // updating row
        return db.update(InvestmentPackage.TABLE_NAME, values,
                InvestmentPackage.COLUMN_ID + " = ?",
                new String[]{String.valueOf(investmentPs.getId())});
    }



    ContentValues values_revenue = new ContentValues();
        values_revenue.put(Revenues.COLUMN_INVESTORS_EMAIL_ADDRESS, email);
        values_revenue.put(Revenues.COLUMN_INITIAL_CAPITAL, capital);
        values_revenue.put(Revenues.COLUMN_INTEREST, interest);
        values_revenue.put(Revenues.COLUMN_CIRCLE, circle);
        values_revenue.put(Revenues.COLUMN_TOTAL, total);
        values_revenue.put(Revenues.COLUMN_START_DAY, start);
        values_revenue.put(Revenues.COLUMN_END_DAY, finish);
        values_revenue.put(Revenues.COLUMN_TIMESTAMP, timestamp);
    // insert row



    public void deleteRevenues(Revenues re) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Revenues.TABLE_NAME, Revenues.COLUMN_ID + " = ?",
                new String[]{String.valueOf(re.getId())});
        db.close();
    }

    public void deleteInvestmentEvents(InvestmentEvents iE) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(InvestmentEvents.TABLE_NAME,
                InvestmentEvents.COLUMN_ID + " = ?",
                new String[]{String.valueOf(iE.getId())});
        db.close();
    }
    public void deleteInvestors(Investors investors) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Investors.TABLE_NAME, Investors.COLUMN_ID + " = ?",
                new String[]{String.valueOf(investors.getId())});
        b.close();
    }
    public void deleteInvestmentPackage(InvestmentPackage iPs) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(InvestmentPackage.TABLE_NAME,
                InvestmentPackage.COLUMN_ID + " = ?",
                new String[]{String.valueOf(iPs.getId())});
        db.close();
    }
    public Cursor queueAll_SortBy_Office(){
        String[] columns = new String[]{COLUMN_ID,
                COLUMN_APPROVING_OFFICE,
                COLUMN_TYPE,COLUMN_DATE_OF_ACTIVATION,COLUMN_APPROVER,COLUMN_REMARKS,COLUMN_DATE_OF_COMPLETION};
        Cursor cursor = sqLiteDatabase.query(InvestmentPackage.TABLE_NAME, columns,
                null, null, null, null, COLUMN_APPROVING_OFFICE);

        return cursor;
    }
    public Cursor queueAll_SortBy_Remark(){
        String[] columns = new String[]{COLUMN_ID, COLUMN_APPROVING_OFFICE,
                COLUMN_TYPE,COLUMN_DATE_OF_ACTIVATION,COLUMN_APPROVER,COLUMN_REMARKS,COLUMN_DATE_OF_COMPLETION};
        Cursor cursor = sqLiteDatabase.query(InvestmentPackage.TABLE_NAME, columns,
                null, null, null, null, COLUMN_REMARKS);

        return cursor;
    }
    public Cursor queueAll_SortBy_Type(){
        String[] columns = new String[]{COLUMN_ID, COLUMN_APPROVING_OFFICE,
                COLUMN_TYPE,COLUMN_DATE_OF_ACTIVATION,COLUMN_APPROVER,COLUMN_REMARKS,COLUMN_DATE_OF_COMPLETION};
        Cursor cursor = sqLiteDatabase.query(InvestmentPackage.TABLE_NAME, columns,
                null, null, null, null, COLUMN_TYPE);

        return cursor;
    }
    public Cursor queueAll_SortBy_Name(){
        String[] columns = new
                String[]{COLUMN_ID,COLUMN_SURNAME,COLUMN_FIRSTNAME,COLUMN_MIDDLENAME,
                COLUMN_INVESTORS_PHONE_NUMBER,COLUMN_INVESTORS_EMAIL_ADDRESS,

                COLUMN_GENDER,COLUMN_STATE};
        Cursor cursor = sqLiteDatabase.query(Investors.TABLE_NAME, columns,
                null, null, null, null, COLUMN_FIRSTNAME);

        return cursor;
    }
    public Cursor queueAll_SortBy_PhoneNumber(){
        String[] columns = new
                String[]{COLUMN_ID,COLUMN_SURNAME,COLUMN_FIRSTNAME,COLUMN_MIDDLENAME,
                COLUMN_INVESTORS_PHONE_NUMBER,COLUMN_INVESTORS_EMAIL_ADDRESS,

                COLUMN_GENDER,COLUMN_STATE};
        Cursor cursor = sqLiteDatabase.query(Investors.TABLE_NAME, columns,
                null, null, null, null, COLUMN_INVESTORS_PHONE_NUMBER);

        return cursor;
    }
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
