package com.mantrova.mytodolist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import java.util.UUID;


public class TaskActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        UUID taskId = (UUID) getIntent()
                .getSerializableExtra(TaskFragment.EXTRA_CRIME_ID);
        return new TaskFragment();
    }
}
