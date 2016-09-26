package com.qarehbaghi.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class EditItemActivity extends AppCompatActivity {

    private String todoItem;
    private int position;
    private EditText etItemEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        todoItem = getIntent().getStringExtra("Item");
        position = getIntent().getIntExtra("Position", 0);
        etItemEditor = (EditText) findViewById(R.id.etItemEditor);
        etItemEditor.setText(todoItem);
        etItemEditor.setSelection(etItemEditor.getText().length());
    }

    public void saveItem(View view) {
        //Save the item first
        Intent data = new Intent();
        data.putExtra("EditedItem", etItemEditor.getText().toString());
        data.putExtra("Position", position);
        setResult(RESULT_OK, data);
        this.finish();
    }
}
