package com.mantrova.mytodolist;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Таня on 13.02.2015.
 */
public class TaskFragment extends SherlockFragment {
    Task mTask;
    EditText mTitleField;
    public static final String EXTRA_CRIME_ID = "com.mantrova.mytodolist.task_id";
    DatabaseHelper dbHelper;
    SQLiteDatabase sdb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID taskId = (UUID) getActivity().getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        mTask = TaskLab.get(getActivity()).getTask(taskId);
        dbHelper = new DatabaseHelper(getActivity().getApplicationContext(), "mydatabase.db", null, 1);
        sdb = dbHelper.getWritableDatabase();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu, com.actionbarsherlock.view.MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_task, menu);
    }

  //  @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task, parent, false);

    //    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSherlockActivity().getSupportActionBar().setTitle("New task");
   //     }

        if (mTask == null)
            mTask = new Task();
        mTitleField = (EditText) v.findViewById(R.id.task_title);
        mTitleField.setText(mTask.getTitle());
        mTitleField.postDelayed(new Runnable() {

            @Override
            public void run() {
                InputMethodManager keyboard = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(mTitleField, 0);
            }
        }, 200);

        mTitleField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
             //   mTask.setTitle(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
            }

            public void afterTextChanged(Editable c) {
            }
        });

        return v;
    }

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (NavUtils.getParentActivityIntent(getActivity()) != null)
                    NavUtils.navigateUpFromSameTask(getActivity());
                return true;
            case R.id.menu_item_save_task:
                mTitleField = (EditText) getActivity().findViewById(R.id.task_title);

                mTask.setTitle(mTitleField.getText().toString());
                if (mTask.getTitle() != null) {
                    ContentValues newValues = new ContentValues();
                    newValues.put(dbHelper.TASK_ID_COLUMN, mTask.getId().toString());
                    newValues.put(dbHelper.TASK_NAME_COLUMN, mTask.getTitle());

                    String Query = "Select * from " + dbHelper.DATABASE_TABLE + " where " + dbHelper.TASK_ID_COLUMN + " = " + "'" + mTask.getId() + "'";
                    Cursor cursor = sdb.rawQuery(Query, null);
                    cursor.getCount();
                    if (cursor.getCount() <= 0) {
                        TaskLab.get(getActivity()).addTask(mTask);
                        sdb.insert("tasks", null, newValues);
                    } else {
                        String where = "task_id" + "=" + "'" + mTask.getId() + "'";
                        sdb.update("tasks", newValues, where, null);
                    }
                }
                if (NavUtils.getParentActivityIntent(getActivity()) != null)
                    NavUtils.navigateUpFromSameTask(getActivity());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    public boolean CheckIsDataAlreadyInDB(String TableName, String dbfield, String fieldValue) {
//        String Query = "Select * from " + dbHelper.DATABASE_TABLE + " where " + dbHelper.TASK_ID_COLUMN + " = " + "'" + mTask.getId() + "'";
//        Cursor cursor = sdb.rawQuery(Query, null);
//        cursor.getCount();
//        if(cursor.getCount() <= 0)
//            return false;
//        return true;
//    }

    @Override
    public void onPause() {
        super.onPause();

//        ArrayList <Task> tt = TaskLab.get(getActivity()).getTasks();
//        for (Task t : tt)
//            System.out.println(t.getTitle());
//        TaskLab.get(getActivity()).saveTasks(TaskLab.get(getActivity()).getTasks());

    }
}
