package com.mantrova.mytodolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Таня on 13.02.2015.
 */
public class TaskLab {
    private ArrayList<Task> mTasks;
    public static TaskLab sTaskLab;
    private Context mAppContext;

    DatabaseHelper dbHelper;
    SQLiteDatabase sdb;

    private TaskLab(Context appContext) {
        mAppContext = appContext;
        mTasks = new ArrayList<Task>();
        dbHelper = new DatabaseHelper(mAppContext, "mydatabase.db", null, 1);
        sdb = dbHelper.getWritableDatabase();
        Cursor cursor = sdb.query("tasks", new String[] {dbHelper.TASK_ID_COLUMN, dbHelper.TASK_NAME_COLUMN},
                null, null,
                null, null, null) ;

        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            String id = cursor.getString(cursor.getColumnIndex(dbHelper.TASK_ID_COLUMN));
            String title = cursor.getString(cursor.getColumnIndex(dbHelper.TASK_NAME_COLUMN));

            Task new_task = new Task(id, title);
            mTasks.add(new_task);
            cursor.moveToNext();
        }
        cursor.close();

    }

    public static TaskLab get(Context c) {
        if (sTaskLab == null)
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

    public void addTask(Task t) {
        mTasks.add(t);
    }

    public void deleteTask(Task t) {
        mTasks.remove(t);
    }

    public ArrayList<Task> getTasks() {
        return mTasks;
    }

    public void saveTasks(ArrayList<Task> tasks) {

      //
       // sdb = dbHelper.getWritableDatabase();
        sdb.delete("tasks", null, null);

        ContentValues newValues = new ContentValues();

        for (Task t : tasks) {
            newValues.put(dbHelper.TASK_ID_COLUMN, t.getId().toString());
            newValues.put(dbHelper.TASK_NAME_COLUMN, t.getTitle());
            sdb.insert("tasks", null, newValues);
        }
    }
}
