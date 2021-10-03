package com.example.taller2;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class ContactsAdapter extends CursorAdapter {
    private final static int CONTACT_ID_INDEX = 0 ;
    private final static int CONTACT_NAME_INDEX = 1 ;

    public ContactsAdapter(Context context, Cursor c , int flags){
        super(context, c , flags);
    }

    @Override
    public View newView(Context context, Cursor c , ViewGroup parent){
        return LayoutInflater.from(context).inflate(
                R.layout.contact_adapter_layout,
                parent,
                false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView idContactText = view.findViewById(R.id.idContact);
        TextView nameContactText = view.findViewById(R.id.nameContact);

        int idCont = cursor.getInt(CONTACT_ID_INDEX);

        String nameCont = cursor.getString(CONTACT_NAME_INDEX);

        idContactText.setText(String.valueOf(idCont));
        nameContactText.setText(nameCont);
    }

}
