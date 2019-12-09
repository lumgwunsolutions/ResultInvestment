package group.lsg.resultinvestmentapp.Class;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InvestmentPackage implements Serializable, LazyLoading {
    public static final String TABLE_NAME = "investment pacakge";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_AMOUNT_OF_INVESTMENT = "amountInvested";
    public static final String COLUMN_CURRENCY= "currency";
    public static final String COLUMN_INTEREST_RATE= "interestRate";
    public static final String COLUMN_DATE_OF_ACTIVATION= "dateOfActivation";
    public static final String COLUMN_DATE_OF_COMPLETION= "dateOfCompletion";
    public static final String COLUMN_TIME = "duration";
    public static final String COLUMN_APPROVER = "approver";
    public static final String COLUMN_APPROVING_OFFICE = "office";
    public static final String COLUMN_REMARKS = "remarks";
    public static final String COLUMN_OTHERS = "others";

    int id_;
    public String type;
    public double interestRate;
    public double amountInvested;
    public String duration;
    public long dateOfActivation;
    public Date dateOfCompletion;
    public String approver;
    public String office;
    public String remarks;
    public String others;

    @SuppressLint("SimpleDateFormat")
    private static DateFormat dateTime = new
            SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static DateFormat date = new SimpleDateFormat("yyyy-MM-dd");

    public static final String CREATE_TABLE_INVESTMENT_PACKAGE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TYPE + " TEXT,"
                    + COLUMN_AMOUNT_OF_INVESTMENT + " double,"
                    + COLUMN_INTEREST_RATE + " double,"
                    + COLUMN_TIME + " TEXT,"
                    + COLUMN_APPROVER + " TEXT,"
                    + COLUMN_APPROVING_OFFICE + " TEXT,"
                    + COLUMN_REMARKS + " TEXT,"
                    + COLUMN_OTHERS + " TEXT,"
                    + COLUMN_DATE_OF_ACTIVATION + " DATETIME DEFAULTCURRENT_TIMESTAMP"
            + COLUMN_DATE_OF_COMPLETION + " DATETIME"

            + ")";

    public InvestmentPackage() {
        getValue(InvestmentPackage.class);

    }




    public InvestmentPackage(int id_, String type, double
            amountInvested,double interestRate,String currency,String
                                     duration,Date dateOfActivation, Date dateOfCompletion, String
                                     approver,String office,String remarks,String others) {
        this.type = type;
        this.interestRate = interestRate;
        this.amountInvested = amountInvested;
        this.currency = currency;
        this.duration=duration;
        this.approver = approver;
        this.office = office;
        this.others = others;
        this.remarks=remarks;
        this.dateOfCompletion=dateOfCompletion;
        this.dateOfActivation = Calendar.getInstance().getTimeInMillis();

    }
    public String geType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public double getAmountInvested() {
        return amountInvested;
    }

    public void setAmountInvested(double amountInvested) {
        this.amountInvested = amountInvested;
    }
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public int getId_() {
        return id_;
    }

    public void setId_(int id_) {
        this.id_ = id_;
    }
}
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }
    public String getOthers() {
        return others;
    }

    @Override
    public String toString() {
        String type = getType();
        Date dateOfActivation = getDateOfActivation();
        double amountInvested = getAmountInvested();
        String currency = getCurrency();
        String duration = getDuration();
        String approver = getApprover();
        String office = getOffice();
        String others = getOthers();
        String remarks = getRemarks();
        double interestRate = getInterestRate();

        // Returning the string formatted version
        return String.format("%s;%s;%s;%s;%s;%s;%s;%.2f;%.2f;%s", type,
                dateTime.format(dateOfActivation), currency, duration, approver,
                office,remarks, interestRate, amountInvested, others);
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setOffice(double interestRate) {
        this.interestRate = interestRate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getDateOfActivation() {
        return dateOfActivation;
    }

    public void setDateOfActivation(date dateOfActivation) {
        this.dateOfActivation = dateOfActivation;
    }
    public date getDateOfCompletion() {
        return dateOfCompletion;
    }

    public void setDateOfCompletion(date dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> invPackage = new HashMap<>();

        invPackage.put("Type", type);
        invPackage.put("Amount Invested", amountInvested);
        invPackage.put("Currency", currency);
        invPackage.put("duration", duration);
        invPackage.put("Approved By:", approver);
        invPackage.put("Interest Rate", interestRate);
        invPackage.put("Office", office);
        invPackage.put("Approval office", office);
        invPackage.put("Remarks", remarks);
        invPackage.put("Date Of Completion", dateTime.format(new
                Date(String.valueOf(dateOfCompletion)) ));
        invPackage.put("date Of Activation",
                FormatterUtil.getFirebaseDateFormat().format(new
                        Date(String.valueOf(dateOfActivation))));

        return invPackage;
    }
    public static final ThreadLocal<Parcelable.Creator<InvestmentPackage>>
            INVESTMENTPACKAGE = new
            ThreadLocal<Parcelable.Creator<InvestmentPackage>>() {
                @Override
                protected Parcelable.Creator<InvestmentPackage> initialValue() {
                    return new Parcelable.Creator<InvestmentPackage>() {
                        @Override
                        public InvestmentPackage createFromParcel(Parcel parcel) {
                            return new InvestmentPackage(parcel);
                        }

                        @Override
                        public InvestmentPackage[] newArray(int i) {
                            return new InvestmentPackage[0];

                        }
                    };
                }
            };

    @Override
    public ItemType getItemType() {
        return null;
    }

    @Override
    public void setItemType(ItemType itemType) {

    }
}