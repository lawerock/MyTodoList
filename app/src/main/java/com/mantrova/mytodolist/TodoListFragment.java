package com.mantrova.mytodolist;

import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

import java.util.ArrayList;


/**
 * Created by Таня on 13.02.2015.
 */
public class TodoListFragment extends SherlockListFragment {

    private ArrayList<Task> mTasks;
    DatabaseHelper dbHelper;
    SQLiteDatabase sdb;
    TaskAdapter adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mTasks = TaskLab.get(getActivity()).getTasks();
        TaskAdapter adapter = new TaskAdapter(mTasks);
        setListAdapter(adapter);
        setRetainInstance(true);
        dbHelper = new DatabaseHelper(getActivity().getApplicationContext(), "mydatabase.db", null, 1);
        sdb = dbHelper.getWritableDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        View v = inflater.inflate(R.layout.list_fragment_tasks, parent, false);
        ListView listView = (ListView) v.findViewById(android.R.id.list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSS");

                return false;
            }
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu, com.actionbarsherlock.view.MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_todo_list, menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.menu_task_context, menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ((TaskAdapter) getListAdapter()).notifyDataSetChanged();
    }

    public void onListItemClick(ListView l, View v, final int position, long id) {
        final TextView titleTextView =
                (TextView) v.findViewById(R.id.task_list_item_titleTextView);

        getSherlockActivity().startActionMode(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.menu_task_context, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, com.actionbarsherlock.view.MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_item_delete_task:
                        adapter = (TaskAdapter) getListAdapter();
                        TaskLab taskLab = TaskLab.get(getActivity());
                        for (int i = adapter.getCount() - 1; i >= 0; i--) {
                            if (getListView().isItemChecked(i)) {
                                sdb.delete("tasks", dbHelper.TASK_ID_COLUMN + " = " + "'" + adapter.getItem(i).getId() + "'", null);
                                taskLab.deleteTask(adapter.getItem(i));
                            }
                        }
                        mode.finish();
                        adapter.notifyDataSetChanged();
                        return true;
                    case R.id.menu_item_edit_task:
                        Task t = ((TaskAdapter) getListAdapter()).getItem(position);
                        Intent i = new Intent(getActivity(), TaskActivity.class);
                        i.putExtra(TaskFragment.EXTRA_CRIME_ID, t.getId());
                        startActivityForResult(i, 0);

                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
    //            titleTextView.setActivated(false);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_task:
                Task t = new Task();
                Intent i = new Intent(getActivity(), TaskActivity.class);
                i.putExtra(TaskFragment.EXTRA_CRIME_ID, t.getId());
                startActivityForResult(i, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        int position = info.position;
//        TaskAdapter adapter = (TaskAdapter) getListAdapter();
//        Task task = adapter.getItem(position);
//
//        switch (item.getItemId()) {
//            case R.id.menu_item_delete_task:
//                TaskLab.get(getActivity()).deleteTask(task);
//                adapter.notifyDataSetChanged();
//                sdb.delete("tasks", dbHelper.TASK_ID_COLUMN + " = " + "'" + task.getId() + "'", null);
//                return true;
//        }
//        return super.onContextItemSelected(item);
//    }

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
                    (TextView) convertView.findViewById(R.id.task_list_item_titleTextView);
            titleTextView.setText(t.getTitle());

            return convertView;
        }
    }
}