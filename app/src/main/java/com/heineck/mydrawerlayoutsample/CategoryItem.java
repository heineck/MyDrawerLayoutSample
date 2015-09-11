package com.heineck.mydrawerlayoutsample;

/**
 * Created by vheineck on 11/09/15.
 */
public class CategoryItem {

    private int icon;
    private String title;

    public CategoryItem(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
