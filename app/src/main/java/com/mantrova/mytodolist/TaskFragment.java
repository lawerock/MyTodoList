package com.mantrova.mytodolist;

import android.annotation.TargetApi;
import android.content.Context;
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

/**
 * Created by Таня on 13.02.2015.
 */
public class TaskFragment extends Fragment {
    Task mTask;
    EditText mTitleField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_task, menu);
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task, parent, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            getActivity().getActionBar().setTitle("New task");
        }

        mTask = new Task();
        mTitleField = (EditText) v.findViewById(R.id.task_title);
        mTitleField.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                InputMethodManager keyboard = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(mTitleField, 0);
            }
        }, 200);

        mTitleField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mTask.setTitle(c.toString());
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
            }

            public void afterTextChanged(Editable c) {
            }
        });

        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (NavUtils.getParentActivityIntent(getActivity()) != null)
                    NavUtils.navigateUpFromSameTask(getActivity());
                return true;
            case R.id.menu_item_save_task:
                if (mTask.getTitle() != null)
                    TaskLab.get(getActivity()).addTask(mTask);
                if (NavUtils.getParentActivityIntent(getActivity()) != null)
                    NavUtils.navigateUpFromSameTask(getActivity());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
