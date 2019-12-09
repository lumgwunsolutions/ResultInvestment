package group.lsg.resultinvestmentapp;


import com.google.firebase.database.DataSnapshot;

import java.io.Serializable;

public class Profile implements Serializable {
    private String id;
    private String lastname;
    private String firstName;
    private String middleName;
    private String username;
    private String email;
    private String photoUrl;
    private long customerNumber;
    private String registrationToken;

    public Profile() {
        // Default constructor required for calls to
        DataSnapshot.getValue(Profile.class);
    }

    public Profile(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(long customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }
}

