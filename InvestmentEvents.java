package group.lsg.resultinvestmentapp.Class;

import android.annotation.SuppressLint;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SuppressLint("ParcelCreator")
public
class InvestmentEvents implements Serializable, Parcelable {

    public static final String TABLE_NAME = "investment events";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DATE= "date";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_OTHERS = "others";
    public static final String COLUMN_TIMESTAMP = ;
    int id_;
    public String title;
    public String description;
    public long date;
    public String amount;
    public String others;
    public static final String CREATE_TABLE_INVESTMENT_EVENTS =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_AMOUNT + " INTEGER,"
                    + COLUMN_OTHERS + " TEXT,"
                    + COLUMN_DATE + " DATETIME DEFAULTCURRENT_TIMESTAMP"
            + ")";

    public InvestmentEvents(Parcel parcel) {
        getValue(InvestmentEvents.class);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public InvestmentEvents(int title, String description,
                            String date, String amount, String others, String id_1) {
        this.title = title;
        this.amount = amount;
        this.others = others;
        this.id_ = id_1;
        this.description=description;
        this.date = Calendar.getInstance().getTimeInMillis();

    }
    public String geTtitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public long getgender() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
    public int getId_1() {
        return id_;
    }

    public void setId_1(int id_1) {
        this.id_ = id_1;
    }

    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> invEventsResult = new HashMap<>();

        invEventsResult.put("title", title);
        invEventsResult.put("description", description);
        invEventsResult.put("amount", amount);
        invEventsResult.put("others", others);
        invEventsResult.put("date", date);
        invEventsResult.put("createdDateText",
                FormatterUtil.getFirebaseDateFormat().format(new Date(date)));

        return invEventsResult;
    }
    public static final ThreadLocal<Creator<InvestmentEvents>>
            INVESTORSEVENTS = new
            ThreadLocal<Creator<InvestmentEvents>>() {
                @Override
                protected Creator<InvestmentEvents> initialValue() {
                    return new Creator<InvestmentEvents>() {
                        @Override
                        public InvestmentEvents createFromParcel(Parcel parcel) {
                            return new InvestmentEvents(parcel);
                        }

                        @Override
                        public InvestmentEvents[] newArray(int i) {
                            return new InvestmentEvents[0];

                        }
                    };
                }
            };
}
