package com.example.administrador.myapplication.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.administrador.myapplication.model.entities.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientContract {

    public static final String TABLE = "client";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String AGE = "age";
    public static final String PHONE = "phone";
    private static final String CEP = "cep";
    private static final String TIPODELOGRADORO = "tipoDeLogradouro";
    private static final String LOGRADOURO = "logradouro";
    private static final String BAIRRO = "bairro";
    private static final String CIDADE = "cidade";
    private static final String ESTADO = "estado";
    public static final String[] COLUMNS = {ID, NAME, AGE, PHONE, CEP, TIPODELOGRADORO, LOGRADOURO, BAIRRO, CIDADE, ESTADO};

    public static final String TABLE_USER = "user";
    public static final String NAME_USER = "name_user";
    public static final String ID_USER = "id_user";
    public static final String PASSWORD_USER = "password_user";
    public static final String[] COLUMNS_USER = {ID_USER, NAME_USER, PASSWORD_USER};

    public static String getSqlCreateTable() {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(NAME + " TEXT, ");
        sql.append(AGE + " INTEGER, ");
        sql.append(PHONE + " TEXT, ");
        sql.append(CEP + " TEXT, ");
        sql.append(TIPODELOGRADORO + " TEXT, ");
        sql.append(LOGRADOURO + " TEXT, ");
        sql.append(BAIRRO + " TEXT, ");
        sql.append(CIDADE + " TEXT, ");
        sql.append(ESTADO + " TEXT ");
        sql.append(" ); ");

        return sql.toString();
    }

    public static String getSqlCreateTableUser() {
        StringBuilder sqlUser = new StringBuilder();
        sqlUser.append("CREATE TABLE ");
        sqlUser.append(TABLE_USER);
        sqlUser.append(" ( ");
        sqlUser.append(ID_USER + " INTEGER PRIMARY KEY, ");
        sqlUser.append(NAME_USER + " TEXT, ");
        sqlUser.append(PASSWORD_USER + " TEXT ");
        sqlUser.append(" ); ");

        return sqlUser.toString();
    }

    public static String getCreateUserAdmin() {
        StringBuilder sqlUser = new StringBuilder();
        sqlUser.append("INSERT INTO ");
        sqlUser.append(TABLE_USER);
        sqlUser.append(" (");
        sqlUser.append("id_user, name_user, password_user ");
        sqlUser.append(ID_USER + ", ");
        sqlUser.append(NAME_USER + ", ");
        sqlUser.append(PASSWORD_USER + ", ");
        sqlUser.append(" )");
        sqlUser.append(" VALUES");
        sqlUser.append(" (1, 'admin', 'admin')");

        return sqlUser.toString();
    }


    public static ContentValues getContentValues(Client client) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, client.getCodigo());
        contentValues.put(NAME, client.getName());
        contentValues.put(AGE, client.getAge());
        contentValues.put(PHONE, client.getPhone());
        contentValues.put(CEP, client.getCep());
        contentValues.put(TIPODELOGRADORO, client.getTipoDeLogradouro());
        contentValues.put(LOGRADOURO, client.getLogradouro());
        contentValues.put(BAIRRO, client.getBairro());
        contentValues.put(CIDADE, client.getCidade());
        contentValues.put(ESTADO, client.getEstado());
        return contentValues;
    }

    public static Client bind(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            Client client = new Client();
            client.setCodigo(cursor.getLong(cursor.getColumnIndex(ClientContract.ID)));
            client.setName(cursor.getString(cursor.getColumnIndex(ClientContract.NAME)));
            client.setAge(cursor.getInt(cursor.getColumnIndex(ClientContract.AGE)));
            client.setPhone(cursor.getString(cursor.getColumnIndex(ClientContract.PHONE)));
            client.setCep(cursor.getString(cursor.getColumnIndex(ClientContract.CEP)));
            client.setTipoDeLogradouro(cursor.getString(cursor.getColumnIndex(ClientContract.TIPODELOGRADORO)));
            client.setLogradouro(cursor.getString(cursor.getColumnIndex(ClientContract.LOGRADOURO)));
            client.setBairro(cursor.getString(cursor.getColumnIndex(ClientContract.BAIRRO)));
            client.setCidade(cursor.getString(cursor.getColumnIndex(ClientContract.CIDADE)));
            client.setEstado(cursor.getString(cursor.getColumnIndex(ClientContract.ESTADO)));
            return client;
        }
        return null;
    }

    public static Client bindUser(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            Client client = new Client();
            client.setCodigo_admmin(cursor.getLong(cursor.getColumnIndex(ClientContract.ID_USER)));
            client.setName_admin(cursor.getString(cursor.getColumnIndex(ClientContract.NAME_USER)));
            client.setPassword_admin(cursor.getString(cursor.getColumnIndex(ClientContract.PASSWORD_USER)));
            return client;
        }
        return null;
    }

    public static List<Client> bindListUser(Cursor cursor) {
        List<Client> clients = new ArrayList<>();
        while (cursor.moveToNext()) {
            clients.add(bindUser(cursor));
        }
        return clients;
    }

    public static List<Client> bindList(Cursor cursor) {
        List<Client> clients = new ArrayList<>();
        while (cursor.moveToNext()) {
            clients.add(bind(cursor));
        }
        return clients;
    }


}



