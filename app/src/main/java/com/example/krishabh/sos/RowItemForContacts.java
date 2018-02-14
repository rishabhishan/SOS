package com.example.krishabh.sos;

import android.graphics.Bitmap;

/**
 * Created by rishu on 3/8/2016.
 */
public class RowItemForContacts {


    private String contactName;
    private String phoneNumber;
    private Bitmap contactImage;


    public RowItemForContacts(String contactName, String phoneNumber, Bitmap contactImage) {

        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
        this.contactImage = contactImage;
    }

    public String getContactName() {
        return contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public Bitmap getContactImage() {
        return contactImage;
    }


    public void setContactName(String contactName) {
        this.contactName = contactName;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setContactImage(Bitmap contactImage) {
        this.contactImage = contactImage;
    }


}
