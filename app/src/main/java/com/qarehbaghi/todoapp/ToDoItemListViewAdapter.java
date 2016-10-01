package com.qarehbaghi.todoapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Reza on 2016-09-26.
 */

public class ToDoItemListViewAdapter extends ArrayAdapter<ToDoItemListData> {

    private Context context;
    private int resID;
    private ArrayList<ToDoItemListData> data;
    private boolean selectionModeActive = false;

    public ToDoItemListViewAdapter(Context context, int resource, ArrayList<ToDoItemListData> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resID = resource;
        this.data = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        CustomHolder holder;

        final ToDoItemListData lvTodoItem = data.get(position);
        if(rowView == null) {
            LayoutInflater inflate = ((Activity) context).getLayoutInflater();
            rowView = inflate.inflate(resID, null);

            holder = new CustomHolder();
            holder.chkBox = (CheckBox) rowView.findViewById(R.id.chkBoxItem);
            holder.chkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.get(position).setItemToggled(!data.get(position).isItemToggled());
                }
            });
            holder.tvItem = (TextView) rowView.findViewById(R.id.tvItem);

            rowView.setTag(holder);
        } else {
            holder = (CustomHolder) rowView.getTag();
        }

        holder.tvItem.setText(lvTodoItem.getText());
        if(selectionModeActive) {
            holder.chkBox.setVisibility(View.VISIBLE);
        } else {
            holder.chkBox.setVisibility(View.GONE);
            holder.chkBox.setChecked(false);
        }

        return rowView;
    }

    public boolean isSelectionModeActive() {
        return selectionModeActive;
    }

    public void setSelectionModeActive(boolean selectionModeActive) {
        this.selectionModeActive = selectionModeActive;
    }

    public class CustomHolder {
        CheckBox chkBox;
        TextView tvItem;
    }
}
