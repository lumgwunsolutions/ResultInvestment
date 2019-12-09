package group.lsg.resultinvestmentapp.Class;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import static group.lsg.resultinvestmentapp.Class.InvestmentEvents.COLUMN_DATE;

public class Queries implements Serializable,Parcelable{
    public static final String TABLE_NAME = "queries";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_INVESTORS_NAME = "name";
    public static final String COLUMN_INVESTORS_PHONE_NUMBER = "phone";
    public static final String COLUMN_INVESTORS_EMAIL_ADDRESS = "email";
    public static final String COLUMN_INVESTORS_CUSTOMER_NUMBER = "customerNo";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    private String investorId;

    int id_;
    String title;
    String description;
    String name;
    String phone;
    String customerNo;
    String email;
    public static final String CREATE_TABLE_QUERIES =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_INVESTORS_NAME + " INTEGER,"
                    + COLUMN_INVESTORS_PHONE_NUMBER + " TEXT,"
                    + COLUMN_INVESTORS_EMAIL_ADDRESS + " TEXT,"
                    + COLUMN_INVESTORS_CUSTOMER_NUMBER + " TEXT,"
                    + COLUMN_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Queries(Parcel parcel3) {
        id_ = parcel3.readInt();
        title = parcel3.readString();
        description = parcel3.readString();
        name = parcel3.readString();
        phone = parcel3.readString();
        customerNo = parcel3.readString();
        email = parcel3.readString();
    }

    public Queries(String title,String description, String investorId) {
        this.title = title;
        this.investorId = investorId;
        this.id_ = id_;
        this.description=description;

    }

    public static final Creator<Queries> CREATOR = new Creator<Queries>() {
        @Override
        public Queries createFromParcel(Parcel in) {
            return new Queries(in);
        }

        @Override
        public Queries[] newArray(int size) {
            return new Queries[size];
        }
    };

    public String getTitle() {
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

    public String getOthers() {
        return investorId;
    }

    public void setInvestorId(String investorId) {
        this.investorId = investorId;

    }
    public static final ThreadLocal<Parcelable.Creator<Queries>> QR = new
            ThreadLocal<Parcelable.Creator<Queries>>() {
                @Override
                protected Parcelable.Creator<Queries> initialValue() {
                    return new Parcelable.Creator<Queries>() {
                        @Override
                        public Queries createFromParcel(Parcel parcel3) {
                            return new Queries(parcel3);
                        }

                        @Override
                        public Queries[] newArray(int i) {
                            return new Queries[0];

                        }
                    };
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id_);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(customerNo);
        parcel.writeString(email);
    }
}

