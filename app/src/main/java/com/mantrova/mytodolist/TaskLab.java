package com.mantrova.mytodolist;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;

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

    public void addTask(Task t){
        mTasks.add(t);
    }

    public ArrayList<Task> getTasks(){
       return mTasks;
    }
}
