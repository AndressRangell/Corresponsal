package com.example.corresponsal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.corresponsal.modelo.Bank;
import com.example.corresponsal.modelo.Client;
import com.example.corresponsal.modelo.User;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {

    private Context context;
    private static final String name = "corresponsal.db";
    private static final int version = 1;

    private static final String table_user = "usuario";
    private static final String column_id_user = "id_usuario";
    private static final String column_email_user = "email_usuario";
    private static final String column_password_user = "password_usuario";
    private static final String column_name_user = "nombre_usuario";
    private static final String column_quota_user = "cupo_usuario";

    private static final String table_bank = "banco";
    private static final String column_id_bank = "id_banco";
    private static final String column_name_bank = "nombre_banco";
    private static final String column_image_bank = "imagen_banco";

    private static final String table_client = "cliente";
    private static final String column_id_client = "id_cliente";
    private static final String column_name_client = "nombre_cliente";
    private static final String column_card_client = "cedula_cliente";
    private static final String column_count_client = "cuenta_cliente";
    private static final String column_pin_client = "pin_cliente";
    private static final String column_balance_client = "saldo_cliente";
    private static final String column_bank_client = "banco_cliente";

    public DataBase(@Nullable Context context) {
        super(context, name, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String queryUser = "Create table " + table_user + "(" +
                column_id_user + " integer primary key," +
                column_email_user + " text unique," +
                column_password_user + " text," +
                column_name_user + " text," +
                column_quota_user + " integer);";
        db.execSQL(queryUser);

        String queryBank = "Create table " + table_bank + "(" +
                column_id_bank + " integer primary key," +
                column_name_bank + " text," +
                column_image_bank + " text);";
        db.execSQL(queryBank);

        String queryClient = "Create table " + table_client + "(" +
                column_id_client + " integer primary key," +
                column_name_client + " text," +
                column_card_client + " integer," +
                column_count_client + " integer unique," +
                column_pin_client + " integer," +
                column_balance_client + " integer," +
                column_bank_client + " integer," +
                "UNIQUE(" + column_card_client + ", " + column_bank_client + ")," +
                "foreign key(" + column_bank_client + ") references " + table_bank + "(" + column_id_bank + "));";
        db.execSQL(queryClient);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_user);
        db.execSQL("DROP TABLE IF EXISTS " + table_bank);
        db.execSQL("DROP TABLE IF EXISTS " + table_client);
        onCreate(db);
    }

    public User validateLogin (String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + table_user  + " WHERE " + column_email_user + " = '" + email
                + "' AND " + column_password_user + " = '" + password + "'";
        User user = new User();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()){
                user.setId(cursor.getString(0));
                user.setEmail(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setName(cursor.getString(3));
                user.setSaldo(cursor.getDouble(4));
            }
            return user;
        }
        return null;
    }

    public boolean addUsers (ArrayList<User> users) {
        boolean process = false;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + table_user);
        for (User i: users) {
            ContentValues cv = new ContentValues();
            cv.put(column_id_user, i.getId());
            cv.put(column_email_user, i.getEmail());
            cv.put(column_password_user, i.getPassword());
            cv.put(column_name_user, i.getName());
            cv.put(column_quota_user, i.getSaldo());
            long result = db.insert(table_user, null, cv);
            if (result == -1) {
                process = false;
                return process;
            } else {
                process = true;
            }
        }
        return process;
    }

    public boolean addBanks (ArrayList<Bank> banks) {
        boolean process = false;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + table_bank);
        db.execSQL("DELETE FROM " + table_client);
        for (Bank i: banks) {
            ContentValues cv = new ContentValues();
            cv.put(column_id_bank, i.getId());
            cv.put(column_name_bank, i.getName());
            cv.put(column_image_bank, i.getImgLogo());
            addClients(i.getUsuariosBanco());
            long result = db.insert(table_bank, null, cv);
            if (result == -1) {
                process = false;
                return process;
            } else {
                process = true;
            }
        }
        return process;

    }

    public boolean addClients (ArrayList<Client> clients) {
        boolean process = false;
        SQLiteDatabase db = getWritableDatabase();
        for (Client i: clients) {
            ContentValues cv = new ContentValues();
            cv.put(column_id_client, i.getId());
            cv.put(column_name_client, i.getName());
            cv.put(column_card_client, i.getCedula());
            cv.put(column_count_client, i.getCuenta());
            cv.put(column_pin_client, i.getPIN());
            cv.put(column_balance_client, i.getSaldo());
            cv.put(column_bank_client, i.getIdBanco());
            long result = db.insert(table_client, null, cv);
            if (result == -1) {
                process = false;
                return process;
            } else {
                process = true;
            }
        }
        return process;
    }

    public ArrayList<Bank> getListBanks(){
        ArrayList<Bank> listBanks = new ArrayList<Bank>();
        Bank bank = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + table_bank;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() != 0){
            while (cursor.moveToNext()){
                bank = new Bank();
                bank.setId(cursor.getString(0));
                bank.setName(cursor.getString(1));
                bank.setImgLogo(cursor.getString(2));

                listBanks.add(bank);
            }
        }
        return listBanks;
    }

    public ArrayList<String> validateConsult(String numberCount, String numberCard, String pin, int idBank){
        ArrayList<String> answer = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + table_client + " WHERE " + column_count_client + " = " + numberCount
                + " AND " + column_card_client + " = " + numberCard + " AND " + column_pin_client + " = " + pin
                + " AND " + column_bank_client + " = " + idBank;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() != 0){
            while (cursor.moveToNext()){
                answer.add(cursor.getString(1));
                answer.add(cursor.getString(5));
            }
            //ArrayList(name, balance)
            return answer;
        }else{
            return null;
        }
    }

    public boolean validateConsign(String numberCount, String numberCard, String amount, int idBank, int idUser){
        boolean flag = false;
        int balance = 0;
        int quota = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + table_client + ", " + table_user + " WHERE " + column_count_client + " = " + numberCount
                + " AND " + column_id_user + " = " + idUser + " AND " + column_bank_client + " = " + idBank
                + " AND " + column_quota_user + " > " + amount;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() != 0){

            while (cursor.moveToNext()){
                balance = cursor.getInt(5) + Integer.parseInt(amount);
                quota = cursor.getInt(11) - Integer.parseInt(amount);
            }

            SQLiteDatabase db2 = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(column_balance_client, balance);
            String[] count = {numberCount};
            db2.update(table_client, cv, column_count_client + " = ?", count);

            cv = new ContentValues();
            cv.put(column_quota_user, quota);
            String[] user = {String.valueOf(idUser)};
            db2.update(table_user, cv, column_id_user + " = ?", user);

            flag = true;

        }
        return flag;
    }

    public boolean validateDrawal(String numberCount, String numberCard, String pin, String amount, int idBank, int idUser){
        boolean flag = false;
        int balance = 0;
        int quota = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + table_client + ", " + table_user + " WHERE " + column_count_client + " = " + numberCount
                + " AND " + column_id_user + " = " + idUser + " AND " + column_bank_client + " = " + idBank
                + " AND " + column_balance_client + " > " + amount;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() != 0){

            while (cursor.moveToNext()){
                balance = cursor.getInt(5) - Integer.parseInt(amount);
                quota = cursor.getInt(11) + Integer.parseInt(amount);
            }

            SQLiteDatabase db2 = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(column_balance_client, balance);
            String[] count = {numberCount};
            db2.update(table_client, cv, column_count_client + " = ?", count);

            cv = new ContentValues();
            cv.put(column_quota_user, quota);
            String[] user = {String.valueOf(idUser)};
            db2.update(table_user, cv, column_id_user + " = ?", user);

            flag = true;

        }
        return flag;
    }

    public String getQuota(String idUser){
        SQLiteDatabase db = this.getReadableDatabase();
        String quota = "";
        String query = "SELECT " + column_quota_user + " FROM " + table_user + " WHERE " + column_id_user + " = " + idUser;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() != 0){
            while (cursor.moveToNext()){
                quota = cursor.getString(0);
            }
        }
        return quota;
    }

}
