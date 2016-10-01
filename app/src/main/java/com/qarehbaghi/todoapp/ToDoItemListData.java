package com.qarehbaghi.todoapp;

/**
 * Created by Reza on 2016-09-26.
 */

public class ToDoItemListData {

    private boolean itemToggled;
    private String text;
    private long id;

    public ToDoItemListData() {
        super();
    }

    public ToDoItemListData(String text, int id) {
        this.itemToggled = false;
        this.text = text;
        this.id = id;
    }

    public boolean isItemToggled() {
        return itemToggled;
    }

    public void setItemToggled(boolean itemToggled) {
        this.itemToggled = itemToggled;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
