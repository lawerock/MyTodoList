package com.mantrova.mytodolist;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Таня on 13.02.2015.
 */
public class TaskLab {
    private ArrayList<Task> mTasks;
    public static TaskLab sTaskLab;
    private Context mAppContext;

    private TaskLab(Context appContext) {
        mAppContext = appContext;
        mTasks = new ArrayList<Task>();
    }

    public static TaskLab get(Context c){
        if (sTaskLab==null)
            sTaskLab = new TaskLab(c.getApplicationContext());
        return sTaskLab;
    }

    public Task getTask(UUID id) {
        for (Task t : mTasks) {
            if (t.getId().equals(id))
                return t;
        }
        return null;
    }

    public void addTask(Task t){
        mTasks.add(t);
    }

    public ArrayList<Task> getTasks(){
       return mTasks;
    }
}
