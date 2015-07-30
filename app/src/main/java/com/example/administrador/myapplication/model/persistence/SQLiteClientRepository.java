package com.example.administrador.myapplication.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

public class SQLiteClientRepository implements ClientRepository {

    private static SQLiteClientRepository SINGLETON_INSTANCE;

    private SQLiteClientRepository(){
        super();
    }


    public static ClientRepository getInstanceClientRepository(){
        if(SINGLETON_INSTANCE == null){
            SINGLETON_INSTANCE = new SQLiteClientRepository();
        }
        return SINGLETON_INSTANCE;
    }


    @Override
    public void save(Client client) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = ClientContract.getContentValues(client);
        if(client.getCodigo() == null){
            db.insert(ClientContract.TABLE, null, values);}
        else{
            String[] strings = {client.getCodigo().toString()};
            db.update(ClientContract.TABLE, values, ClientContract.ID+"=?", strings);
        }
        db.close();
        helper.close();
    }

    @Override
    public List<Client> getAll() {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        return ClientContract.bindList(db.query(ClientContract.TABLE, ClientContract.COLUMNS, null, null, null, null, ClientContract.NAME));
    }

    public List<Client> getAllUsers() {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        return ClientContract.bindListUser(db.query(ClientContract.TABLE_USER, ClientContract.COLUMNS_USER, null, null, null, null, ClientContract.NAME_USER));
    }

    @Override
    public void delete(Client cliente) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] valores = {cliente.getCodigo().toString()};
        db.delete(ClientContract.TABLE, ClientContract.ID+"=?", valores);
        db.close();
        helper.close();
    }


}
