package com.example.krishabh.sos;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rishu on 3/8/2016.
 */
public class CustomAdapterContacts extends BaseAdapter {

    Context context;
    List<RowItemForContacts> rowItems;

    CustomAdapterContacts(Context context, List<RowItemForContacts> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    /* private view holder class */
    private class ViewHolder {
        TextView contactName;
        TextView contactNumber;
        ImageView contactImage;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();

            holder.contactName = (TextView) convertView
                    .findViewById(R.id.contactName);
            holder.contactNumber = (TextView) convertView
                    .findViewById(R.id.contactNumber);

            holder.contactImage = (ImageView) convertView
                    .findViewById(R.id.contactImage);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RowItemForContacts row_pos = rowItems.get(position);

        // holder.profile_pic.setImageResource(row_pos.getProfile_pic_id());
        holder.contactName.setText(row_pos.getContactName());
        holder.contactNumber.setText(row_pos.getPhoneNumber());
        holder.contactImage.setImageBitmap(row_pos.getContactImage());


        return convertView;
    }


}
