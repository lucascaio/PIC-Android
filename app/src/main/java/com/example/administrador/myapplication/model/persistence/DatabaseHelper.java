package com.example.administrador.myapplication.model.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.administrador.myapplication.model.entities.Client;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String BANCO_DADOS = "MY_DATABASE";
    public static final int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DatabaseHelper.BANCO_DADOS, null, DatabaseHelper.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ClientContract.getSqlCreateTable());
        db.execSQL(ClientContract.getSqlCreateTableUser());
        db.execSQL(ClientContract.getCreateUserAdmin());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
