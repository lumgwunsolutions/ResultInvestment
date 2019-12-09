package group.lsg.resultinvestmentapp.Class;

import java.io.Serializable;

public abstract class User implements Serializable {
    private String lastName;
    private String firstNames;
    private String email;
    private String phoneNumber;
    private String gender;
    private String address;
    private String town;
    private String lga;
    private String state;

    public User(String lastName, String firstNames, String email,
                String phoneNumber) {
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.address = address;
        this.town = town;
        this.firstNames = firstNames;
        this.email = email;
        this.state = state;


    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstNames() {
        return firstNames;
    }

    public void setFirstNames(String firstNames) {
        this.firstNames = firstNames;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s", lastName, firstNames, email);
    }
}