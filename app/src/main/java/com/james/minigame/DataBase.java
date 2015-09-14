package com.james.minigame;

import android.provider.ContactsContract;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by James on 14.09.2015.
 */
@Database(name = DataBase.DBNAME, version = 1)
public class DataBase {
    public static final String DBNAME= "app_db";
}
