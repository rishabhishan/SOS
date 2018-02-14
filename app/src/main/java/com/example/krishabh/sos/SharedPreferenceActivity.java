package com.example.krishabh.sos;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by rishu on 3/6/2016.
 */
public class SharedPreferenceActivity {
    SharedPreferences pSharedPref;

    public static final String PACKAGE_NAME = "com.google.android.gms.location.sample.locationaddress";
    ;

    public static final String SHARED_PREFERENCES_NAME = PACKAGE_NAME + ".SHARED_PREFERENCES_NAME";


    public SharedPreferenceActivity(){}

    public SharedPreferenceActivity(Context context) {

        pSharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

    }




    protected Set<String> returnContactName(Context context) {

        Set<String> s = new HashSet<String>(pSharedPref.getStringSet("contactsName", new HashSet<String>()));
        return s;

    }


    protected void removeContact(String name, String number){

        SharedPreferences.Editor editor = pSharedPref.edit();
        Set<String> names = new HashSet<String>(pSharedPref.getStringSet("contactsName", new HashSet<String>()));
        Set<String> numbers = new HashSet<String>(pSharedPref.getStringSet("contactsNumber", new HashSet<String>()));
        names.remove(name);
        numbers.remove(number);
        editor.putStringSet("contactsName", names);
        editor.putStringSet("contactsNumber", numbers);
        editor.commit();

    }


    protected Set<String> returnContactNumber(Context context) {

        Set<String> s = new HashSet<String>(pSharedPref.getStringSet("contactsNumber", new HashSet<String>()));
        return s;

    }




    protected void saveForceValue(Context context, String str) {


        SharedPreferences.Editor editor = pSharedPref.edit();
        editor.putString("forceValue", str);
        editor.commit();

    }

    protected String returnForceValue(Context context) {


        return pSharedPref.getString("forceValue","0.00");


    }


    protected void saveOnOff(Context context, Boolean bool) {

        SharedPreferences.Editor editor = pSharedPref.edit();
        editor.putBoolean("onOff", bool);
        editor.commit();

    }

    protected boolean returnOnOff(Context context) {


        return pSharedPref.getBoolean("onOff",false);


    }


    protected void saveEmergencyMessage(Context context, String str) {


        SharedPreferences.Editor editor = pSharedPref.edit();
        editor.putString("emergencyMessage", str);
        editor.commit();
    }


    protected String returnEmergencyMessage(Context context) {


        return pSharedPref.getString("emergencyMessage","Set SOS message");


    }



    protected void saveContact(Context context, String name, String number) {


        SharedPreferences.Editor editor = pSharedPref.edit();
        Set<String> contNames = new HashSet<String>(pSharedPref.getStringSet("contactsName", new HashSet<String>()));

        if(name==null)
            name = "No Name Found";
        contNames.add(name);
        editor.putStringSet("contactsName", contNames);

        Set<String> contNumbers = new HashSet<String>(pSharedPref.getStringSet("contactsNumber", new HashSet<String>()));
        if(number==null)
            number = "No Number Found";
        contNumbers.add(number);
        editor.putStringSet("contactsNumber", contNumbers);


        editor.commit();

    }


}
