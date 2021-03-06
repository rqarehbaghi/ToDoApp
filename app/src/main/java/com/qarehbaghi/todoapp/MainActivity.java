package com.qarehbaghi.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ToDoItemListData> todoItems;
    private ToDoItemListViewAdapter aTodoAdapter;
    private ListView lvItems;
    private EditText etEditText;
    private todoDatabase myDb;

    private final int EDIT_VIEW_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView)findViewById(R.id.lvItems);
        lvItems.setAdapter(aTodoAdapter);
        etEditText = (EditText)findViewById(R.id.etEditText);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(aTodoAdapter.isSelectionModeActive()) {
                    int i = 0;
                } else {
                    Intent editIntent = new Intent(MainActivity.this, EditItemActivity.class);
                    editIntent.putExtra("Item", todoItems.get(position).getText());
                    editIntent.putExtra("Position", position);
                    startActivityForResult(editIntent, EDIT_VIEW_REQUEST_CODE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(aTodoAdapter.isSelectionModeActive()) {
            aTodoAdapter.setSelectionModeActive(false);
            aTodoAdapter.notifyDataSetChanged();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if (requestCode == EDIT_VIEW_REQUEST_CODE) {
                String editedItem = data.getExtras().getString("EditedItem");
                int position = data.getIntExtra("Position", 0);
                ToDoItemListData todoItem = todoItems.get(position);
                todoItem.setText(editedItem);
                myDb.updateData(todoItem);
                Toast.makeText(this, "\"" + editedItem + "\"" + " Saved", Toast.LENGTH_SHORT).show();
            }

            aTodoAdapter.notifyDataSetChanged();
        }
    }

    private void populateArrayItems() {
        myDb = new todoDatabase(this);
        todoItems = myDb.getData();
        aTodoAdapter = new ToDoItemListViewAdapter(this, R.layout.custom_todo_item, todoItems);
    }

    public void onAddItem(View view) {
        String enteredText = etEditText.getText().toString().trim();
        if(!enteredText.equals("")) {
            ToDoItemListData todoItem = new ToDoItemListData(enteredText, todoItems.size());
            aTodoAdapter.add(todoItem);
            myDb.insertData(todoItem);
            etEditText.setText("");
        }
    }

    public void deleteItems(MenuItem item) {
        if(!aTodoAdapter.isSelectionModeActive()) {
            aTodoAdapter.setSelectionModeActive(true);
        } else {
            int prevSize = todoItems.size();
            Iterator<ToDoItemListData> todoItemsIterator = todoItems.iterator();
            while (todoItemsIterator.hasNext()) {
                ToDoItemListData nextItem = todoItemsIterator.next();
                if (nextItem.isItemToggled()) {
                    myDb.deleteData(nextItem.getId());
                    todoItemsIterator.remove();
                }
            }
            aTodoAdapter.setSelectionModeActive(false);
            int numItemsDeleted = prevSize - todoItems.size();
            if(numItemsDeleted > 0) {
                Toast.makeText(this, numItemsDeleted + " Item" + (numItemsDeleted > 1 ? "s" : "") + " Deleted", Toast.LENGTH_SHORT).show();
            }
        }
        aTodoAdapter.notifyDataSetChanged();
    }
}
