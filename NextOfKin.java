package group.lsg.resultinvestmentapp.Class;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static group.lsg.resultinvestmentapp.Class.InvestmentEvents.COLUMN_DATE;

@SuppressLint("ParcelCreator")
public class NextOfKin  implements Serializable, Parcelable {
    public static final String TABLE_NAME = "kin";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_MIDDLENAME = "middlename";
    public static final String COLUMN_PHONE_NUMBER = "phone";
    public static final String COLUMN_EMAIL_ADDRESS = "email";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_OTHERS = "others";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    int id_;
    String surname;
    String firstName;
    String middleName;
    String phone;
    String email;
    String state;
    Boolean gender;
    String address;
    String others;
    private long createdDate;

    public static final String CREATE_TABLE_INVESTORS =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_SURNAME + " TEXT,"
                    + COLUMN_FIRSTNAME + " TEXT,"
                    + COLUMN_MIDDLENAME + " INTEGER,"
                    + COLUMN_PHONE_NUMBER + " TEXT,"
                    + COLUMN_EMAIL_ADDRESS + " TEXT,"
                    + COLUMN_STATE + " TEXT,"
                    + COLUMN_GENDER + " TEXT,"
                    + COLUMN_ADDRESS + " INTEGER,"
                    + COLUMN_OTHERS + " TEXT,"
                    + COLUMN_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public NextOfKin() {
        getValue(Investors.class);

    }


    public NextOfKin(String surname,String firstName,
                     String middleName,String phone, String email,String state,
                     Boolean gender,String address,int id_, String others, long createdDate) {

        this.surname = surname;
        this.createdDate = Calendar.getInstance().getTimeInMillis();
        this.others = others;
        this.id_ = id_;
        this.firstName = firstName;
        this.gender = gender;
        this.middleName = middleName;
        this.phone=phone;
        this.email = email;
        this.state = state;
        this.address=address;


    }
    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }


    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }

    public long getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getOthers() {
        return others;
    }
    public void setOthers(String others) {
        this.others = others;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> kin = new HashMap<>();

        kin.put("surname", surname);
        kin.put("firstName", firstName);
        kin.put("middlename", middleName);
        kin.put("email", email);
        kin.put("phone", phone);
        kin.put("address", address);
        kin.put("state", state);
        kin.put("gender", gender);
        kin.put("others", others);
        kin.put("createdDate", createdDate);
        kin.put("createdDateText",
                FormatterUtil.getFirebaseDateFormat().format(new Date(createdDate)));

        return kin;
    }

    public static final ThreadLocal<Creator<NextOfKin>> INVESTORS = new
            ThreadLocal<Creator<NextOfKin>>() {
                @Override
                protected Creator<NextOfKin> initialValue() {
                    return new Creator<NextOfKin>() {
                        @Override
                        public NextOfKin createFromParcel(Parcel parcel1) {
                            return new NextOfKin(parcel1);
                        }

                        @Override
                        public NextOfKin[] newArray(int i) {
                            return new NextOfKin[0];

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

    }
}