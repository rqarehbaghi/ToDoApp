package com.qarehbaghi.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Reza on 2016-09-30.
 */

public class todoDatabase extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "todoDatabase.db";
    public static final String TABLE_NAME = "TWODOIST_TABLE";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "ITEM_TEXT";

    public todoDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,ITEM_TEXT TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST" + TABLE_NAME);
        onCreate(db);
    }

    public void insertData(ToDoItemListData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, data.getText());
        long id = db.insert(TABLE_NAME, null, contentValues);
        data.setId(id);
    }

    public ArrayList<ToDoItemListData> getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<ToDoItemListData> toDoItemListDataList = new ArrayList<>();
        String[] columns = {COL_1, COL_2};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            ToDoItemListData toDoItemListData = new ToDoItemListData();
            toDoItemListData.setId(cursor.getInt(0));
            toDoItemListData.setText(cursor.getString(1));
            toDoItemListDataList.add(toDoItemListData);
            cursor.moveToNext();
        }
        return toDoItemListDataList;
    }

    public void deleteData(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = {String.valueOf(id)};
        db.delete(TABLE_NAME, "ID = ?", args);
    }

    public void updateData(ToDoItemListData data) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = {String.valueOf(data.getId())};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, data.getText());

        db.update(TABLE_NAME, contentValues, "ID = ?", args);
    }
}
