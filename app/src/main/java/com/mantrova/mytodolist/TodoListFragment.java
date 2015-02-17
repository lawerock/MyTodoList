package com.mantrova.mytodolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Таня on 13.02.2015.
 */
public class TodoListFragment extends ListFragment {

    private ArrayList <Task> mTasks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mTasks = TaskLab.get(getActivity()).getTasks();
        TaskAdapter adapter = new TaskAdapter(mTasks);
        setListAdapter(adapter);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        View v = inflater.inflate(R.layout.list_fragment_tasks, parent, false);

        return v;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_todo_list, menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((TaskAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_new_task:
                Intent i = new Intent(getActivity(), TaskActivity.class);
        //        i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
               startActivityForResult(i, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class TaskAdapter extends ArrayAdapter<Task> {
        public TaskAdapter(ArrayList<Task> tasks) {
            super(getActivity(), android.R.layout.simple_list_item_1, tasks);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_task, null);
            }

            Task t = getItem(position);

            TextView titleTextView =
                    (TextView)convertView.findViewById(R.id.task_list_item_titleTextView);
            titleTextView.setText(t.getTitle());

            return convertView;
        }
    }
}
