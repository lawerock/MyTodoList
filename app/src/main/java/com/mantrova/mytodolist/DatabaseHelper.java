package com.mantrova.mytodolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Таня on 18.02.2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns {

    public static final String DATABASE_TABLE = "tasks";
    public static final String TASK_ID_COLUMN = "task_id";
    public static final String TASK_NAME_COLUMN = "task_name";
    public static final String DATABASE_NAME = "mydatabase.db";
    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + DATABASE_TABLE + " (" + BaseColumns._ID + " integer primary key autoincrement, "
            + TASK_ID_COLUMN + " text not null, "
            + TASK_NAME_COLUMN + " text not null);";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    //    context.deleteDatabase(DATABASE_NAME);
    }

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Запишем в журнал
        Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);

        // Удаляем старую таблицу и создаём новую
        db.execSQL("DROP TABLE IF IT EXIST " + DATABASE_TABLE);
        // Создаём новую таблицу
        onCreate(db);

    }
}
