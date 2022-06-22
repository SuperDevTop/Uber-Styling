package com.foxy.arrive5.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.foxy.arrive5.R;
import com.foxy.arrive5.Response.ContactListResponse;

import java.util.ArrayList;
import java.util.List;

public class Contact_Adapter extends BaseAdapter {
    List<ContactListResponse.ContactListBean> contact;
    private Context context;
    private ArrayList<Contact_Model> arrayList;
    private List myList = new ArrayList<String>();
    private OnItemClick mCallback;

    public Contact_Adapter(Context context, List<ContactListResponse.ContactListBean> contact, OnItemClick mCallback) {
        this.context = context;
        this.contact = contact;
        this.mCallback = mCallback;
    }

    @Override
    public int getCount() {
        return contact.size();
    }

    @Override
    public ContactListResponse.ContactListBean getItem(int position) {
        return contact.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ContactListResponse.ContactListBean model = contact.get(position);
        ViewHodler holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_view, parent, false);
            holder = new ViewHodler();
            holder.contactName = (TextView) convertView
                    .findViewById(R.id.contactName);
            holder.imgDone = (CheckBox) convertView.findViewById(R.id.imgDone);
            holder.contactNumber = (TextView) convertView
                    .findViewById(R.id.contactNumber);
            convertView.setTag(holder);
        } else {
            holder = (ViewHodler) convertView.getTag();
        }
        if (!model.getFirst_name().equals("")
                && model.getFirst_name() != null && !model.getLast_name().equals("") && model.getLast_name() != null) {
            holder.contactName.setText(model.getFirst_name() + " " + model.getLast_name());
        } else {
            holder.contactName.setText("No Name");
        }

        if (!model.getMobile().equals("")
                && model.getMobile() != null) {
            holder.contactNumber.setText(model.getMobile());
        } else {
            holder.contactNumber.setText("CONTACT NUMBER - n"
                    + "No Contact Number");
        }
//        holder.imgDone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!myList.contains(model.getMobile())) {
//                    myList.add(model.getMobile());
//                    mCallback.onClick(Collections.singletonList(model.getMobile()));
//                }
//            }
//        });

        holder.imgDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!myList.contains(model.getMobile())) {
                        myList.add(model.getMobile());
                        mCallback.onClick(myList);
                    }
                } else {
                    if (myList.contains(model.getMobile())) {
                        myList.remove(model.getMobile());
                        mCallback.onClick(myList);
                    }
                }
            }
        });
        return convertView;
    }

    public interface OnItemClick {
        void onClick(List myList);
    }
    private class ViewHodler {
        CheckBox imgDone;
        TextView contactName, contactNumber, contactEmail, contactOtherDetails;
    }
}
