package com.mantrova.mytodolist;

import android.support.v4.app.Fragment;

public class TodoListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new TodoListFragment();
    }
}
