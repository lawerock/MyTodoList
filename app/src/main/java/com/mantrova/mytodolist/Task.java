package com.mantrova.mytodolist;

import java.util.UUID;

/**
 * Created by Таня on 13.02.2015.
 */
public class Task {

    private String mTitle;
    private UUID mId;

    public Task(){
        mId = UUID.randomUUID();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public UUID getId(){
        return mId;
    }
}
