package com.example.krishabh.sos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by krishabh on 02/09/16.
 */
public class EmergencyContacts extends AppCompatActivity {

    private ArrayList<RowItemForContacts> mItemArray;
    ArrayList<RowItemForContacts> rowItems;
    ListView mylistview;
    CustomAdapterContacts adapter;

    private static final String TAG = EmergencyContacts.class.getSimpleName();
    private static final int REQUEST_CODE_PICK_CONTACTS = 1;

    private String contactID;

    private SharedPreferences mSharedPreferences;
    SharedPreferenceActivity sh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sh = new SharedPreferenceActivity(this);

        setListItems();

        Snackbar snackbar = Snackbar
                .make(mylistview, "Long click to delete a contact", Snackbar.LENGTH_SHORT);
        snackbar.show();

    }


    public void setListItems(){

        RowItemForContacts item;
        Set<String> names = sh.returnContactName(this);
        Set<String> numbers = sh.returnContactNumber(this);
        Bitmap image = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_account_circle_black_24dp);
        Iterator itrNames = names.iterator();
        Iterator itrNumbers = numbers.iterator();

        mylistview = (ListView) findViewById(R.id.contact_list);
        rowItems = new ArrayList<>();

        while (itrNames.hasNext()){
            item = new RowItemForContacts(itrNames.next().toString(), itrNumbers.next().toString(), image);
            rowItems.add(item);
        }

        adapter = new CustomAdapterContacts(EmergencyContacts.this, rowItems);
        mylistview.setAdapter(adapter);
        mylistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {



                String name = rowItems.get(pos).getContactName();
                String number = rowItems.get(pos).getPhoneNumber();
                sh.removeContact(name,number);
                rowItems.remove(pos);
                adapter.notifyDataSetChanged();
                return true;
            }
        });


    }



    public void onClickSelectContact(View btnSelectContact) {

        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uriContact;
        RowItemForContacts item;
        String name,number;
        Bitmap image;

        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();
            if(hasPhone(uriContact)){
                name = retrieveContactName(uriContact);
                number = retrieveContactNumber(uriContact);
                image = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_account_circle_black_24dp); ;

                sh.saveContact(this,name,number);

                item = new RowItemForContacts(name, number, image);
                rowItems.add(item);
                adapter.notifyDataSetChanged();

            }
            else {

                Snackbar snackbar = Snackbar
                        .make(mylistview, "Contact has no phone number", Snackbar.LENGTH_SHORT);
                snackbar.show();

            }


        }
    }

    private String retrieveContactNumber(Uri uriContact) {

        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID = getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Log.d(TAG, "Contact ID: " + contactID);

        Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }

        cursorPhone.close();

        return contactNumber;
    }

    private boolean hasPhone(Uri uriContact){

        Cursor cursor = getContentResolver().query(uriContact, null, null, null, null);
        int hasNumber = 0;

        if (cursor.moveToFirst()) {

            hasNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));

        }

        cursor.close();

        if(hasNumber!=0)
            return true;
        else
            return false;


    }

    private String retrieveContactName(Uri uriContact) {

        String contactName = null;
        Cursor cursor = getContentResolver().query(uriContact, null, null, null, null);

        if (cursor.moveToFirst()) {

            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

        }

        cursor.close();

        return contactName;

    }







}