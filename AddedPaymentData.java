package group.lsg.resultinvestmentapp.Class;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class AddedPaymentData implements Serializable, Parcelable {

    paymentId,customerId,corporateemail,corporategateway,gatewayId
    private String paymentId;
    private String customerId;
    private String gatewayId;
    private String corporateemail;
    private String corporategateway;


    public AddedPaymentData(String paymentId, String customerId,
                            String corporateemail, String corporategateway, String gatewayId) {

        this.paymentId = paymentId;
        this.customerId = customerId;
        this.corporateemail = corporateemail;
        this.corporategateway = corporategateway;
        this.gatewayId = gatewayId;
    }


    public String getPaymentId() {
        return paymentId;
    }


    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getCustomerId() {
        return customerId;
    }


    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getatewayId() {
        return gatewayId;
    }


    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public Date getCorporateemail() {
        return corporateemail;
    }


    public void setCorporateemail(String corporateemail) {
        this.corporateemail = corporateemail;
    }

    public Date getCorporategateway() {
        return corporategateway;
    }


    public void setCorporategateway(String corporategateway) {
        this.corporategateway = corporategateway;
    }

    @Override
    public String toString() {
        String corporategateway = getCorporategateway();
        String corporateemail = getCorporateemail();
        String gatewayId = getGatewayId();
        String customerId = getCustomerId();
        String paymentId = getPaymentId();


    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}