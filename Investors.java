package group.lsg.resultinvestmentapp.Class;

import android.net.Uri;
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

public class Investors extends BmobObject implements Serializable,Parcelable{
    public static final String TABLE_NAME = "investors";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_MIDDLENAME = "middlename";
    public static final String COLUMN_INVESTORS_PHONE_NUMBER = "phone";
    public static final String COLUMN_INVESTORS_EMAIL_ADDRESS = "email";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_LGA = "lga";

    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_DATEOFBIRTH = "dob";
    public static final String COLUMN_PHOTO = "photo";
    public static final String COLUMN_OTHERS = "others";
    public static final String COLUMN_NEXTOFKIN = "nextofkin";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    int id_;
    String surname;
    String firstName;
    String middleName;
    String phone;
    String email;
    String state;
    String name;
    String lga;
    Boolean gender;
    String address;
    String dob;
    Uri imageUrl;
    String others;
    String nextOfKin;
    private long createdDate;

    public static final String CREATE_TABLE_INVESTORS =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_SURNAME + " TEXT,"
                    + COLUMN_FIRSTNAME + " TEXT,"
                    + COLUMN_MIDDLENAME + " INTEGER,"
                    + COLUMN_INVESTORS_PHONE_NUMBER + " TEXT,"
                    + COLUMN_INVESTORS_EMAIL_ADDRESS + " TEXT,"
                    + COLUMN_STATE + " TEXT,"
                    + COLUMN_LGA + " TEXT,"
                    + COLUMN_GENDER + " TEXT,"
                    + COLUMN_ADDRESS + " INTEGER,"
                    + COLUMN_DATEOFBIRTH + " TEXT,"
                    + COLUMN_PHOTO + " URI,"
                    + COLUMN_NEXTOFKIN + " TEXT,"
                    + COLUMN_OTHERS + " TEXT,"
                    + COLUMN_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Investors() {
        getValue(Investors.class);
    }


    public Investors(String surname,String firstName,
                     String middleName,String phone, String email,String state,
                     Boolean gender,String address,int id_, String imageUrl,String
                             others, String nextOfKin,long createdDate) {

        this.surname = surname;
        this.createdDate = Calendar.getInstance().getTimeInMillis();
        this.others = others;
        this.id_ = id_;
        this.imageUrl=imageUrl;
        this.firstName = firstName;
        this.gender = gender;
        this.middleName = middleName;
        this.phone=phone;
        this.name = name;
        this.email = email;
        this.state = state;
        this.address=address;
        this.nextOfKin=nextOfKin;


    }

    public Investors(Parcel parcel1) {
        id_ = parcel1.readInt();
        surname = parcel1.readString();
        firstName = parcel1.readString();
        middleName = parcel1.readString();
        phone = parcel1.readString();
        email = parcel1.readString();
        state = parcel1.readString();
        lga = parcel1.readString();
        byte tmpGender = parcel1.readByte();
        gender = tmpGender == 0 ? null : tmpGender == 1;
        address = parcel1.readString();
        dob = parcel1.readString();
        imageUrl = parcel1.readParcelable(Uri.class.getClassLoader());
        others = parcel1.readString();
        nextOfKin = parcel1.readString();
        createdDate = parcel1.readLong();
    }

    public static final Creator<Investors> CREATOR = new Creator<Investors>() {
        @Override
        public Investors createFromParcel(Parcel in) {
            return new Investors(in);
        }

        @Override
        public Investors[] newArray(int size) {
            return new Investors[size];
        }
    };

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getNextOfKin() {
        return nextOfKin;
    }

    public void setNextOfKin(String nextOfKin) {
        this.nextOfKin = nextOfKin;
    }
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;

    }
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;

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
    public String getgender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> investorsResult = new HashMap<>();

        investorsResult.put("surname", surname);
        investorsResult.put("firstName", firstName);
        investorsResult.put("middleName", middleName);
        investorsResult.put("email", email);
        investorsResult.put("phone", phone);
        investorsResult.put("address", address);
        investorsResult.put("state", state);
        investorsResult.put("gender", gender);
        investorsResult.put("others", others);
        investorsResult.put("nextOfKin", nextOfKin);
        investorsResult.put("createdDate", createdDate);
        investorsResult.put("createdDateText",
                FormatterUtil.getFirebaseDateFormat().format(new Date(createdDate)));

        return investorsResult;
    }


    public static final ThreadLocal<Creator<Investors>> INVESTORS = new
            ThreadLocal<Creator<Investors>>() {
                @Override
                protected Creator<Investors> initialValue() {
                    return new Creator<Investors>() {
                        @Override
                        public Investors createFromParcel(Parcel parcel1) {
                            return new Investors(parcel1);
                        }

                        @Override
                        public Investors[] newArray(int i) {
                            return new Investors[0];

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
        parcel.writeString(surname);
        parcel.writeString(firstName);
        parcel.writeString(middleName);
        parcel.writeString(phone);
        parcel.writeString(email);
        parcel.writeString(state);
        parcel.writeString(lga);
        parcel.writeByte((byte) (gender == null ? 0 : gender ? 1 : 2));
        parcel.writeString(address);
        parcel.writeString(dob);
        parcel.writeParcelable(imageUrl, i);
        parcel.writeString(others);
        parcel.writeString(nextOfKin);
        parcel.writeLong(createdDate);
    }
}