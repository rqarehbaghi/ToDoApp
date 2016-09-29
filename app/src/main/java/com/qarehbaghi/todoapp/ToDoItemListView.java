package com.qarehbaghi.todoapp;

/**
 * Created by Reza on 2016-09-26.
 */

public class ToDoItemListView {

    private boolean itemToggled;
    private String text;
    private int index;

    public ToDoItemListView() {
        super();
    }

    public ToDoItemListView(String text, int index) {
        this.itemToggled = false;
        this.text = text;
        this.index = index;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
