package com.example.finalcalculatorapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class SQL_Database extends SQLiteOpenHelper {
    int count = 0;

    public SQL_Database(Context appContext) {
        super(appContext, "expressions.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query = "CREATE TABLE table1 (expression TEXT PRIMARY KEY, solution NUMERIC)";
        database.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) { //updating table
        String query = "DROP TABLE IF EXISTS table1";
        database.execSQL(query);
        onCreate(database);

    }

    public void insertExpression(HashMap<String, Double> expressions) {
        SQLiteDatabase database = this.getWritableDatabase(); //saying find the database i'm using open it and make it writeable

        ContentValues values = new ContentValues();
        for(String key: expressions.keySet()) {
            values.put("expression", key);
            values.put("solution", expressions.get(key));
            database.insert("table1", null, values);
            Log.d("Inserted", "Data inserted");
        }
        database.close();
    }
    public ArrayList<HashMap<String, Double>> getAllExp(){
        ArrayList<HashMap<String, Double>> expressionArrayList = new ArrayList<HashMap<String, Double>>();
        String selectQuery = "SELECT * FROM table1";
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                HashMap<String, Double> pre_comp_ans = new HashMap<>();
                pre_comp_ans.put(cursor.getString(0),cursor.getDouble(1));
                expressionArrayList.add(pre_comp_ans);
                count++;

            } while(cursor.moveToNext());
        }
        cursor.close();
        return expressionArrayList;
    }
    public void deleteAll(){
        SQLiteDatabase database = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM table1";
        database.execSQL(deleteQuery);
        Log.d("Deleted", "Expression deleted");


    }

    //    public ArrayList<HashMap<String, String>> getAllCustomers(){
//        ArrayList<HashMap<String, String>> customerArrayList = new ArrayList<HashMap<String, String>>();
//        String selectQuery = "SELECT * FROM customerTable ORDER BY lastName";
//        SQLiteDatabase database = this.getWritableDatabase();
//
//        Cursor cursor = database.rawQuery(selectQuery, null);
//        if(cursor.moveToFirst()){
//            do{
//                HashMap<String, String> customerMap = new HashMap<>();
//                customerMap.put("custID",cursor.getString(0));
//                customerMap.put("firstName", cursor.getString(1));
//                customerMap.put("lastName", cursor.getString(2));
//                customerMap.put("custEMail", cursor.getString(3));
//                customerMap.put("custDate", cursor.getString(4));
//                customerMap.put("custPhone", cursor.getString(5));
//                customerMap.put("custZip", cursor.getString(6));
//
//                customerArrayList.add(customerMap);
//                count++;
//
//            } while(cursor.moveToNext());
//        }
//        cursor.close();
//        return customerArrayList;
//    }
//    public HashMap<String, String> getCustomerInfo(String id){
//        HashMap<String, String> customerMap = new HashMap<>();
//        SQLiteDatabase database = this.getWritableDatabase();
//        String selectQuery = "SELECT * FROM customerTable WHERE custID='" + id + "'";
//
//        Cursor cursor = database.rawQuery(selectQuery, null); //return null if nothing there
//        if(cursor.moveToFirst()){
//            do{
//                customerMap.put("custID",cursor.getString(0));
//                customerMap.put("firstName", cursor.getString(1));
//                customerMap.put("lastName", cursor.getString(2));
//                customerMap.put("custEMail", cursor.getString(3));
//                customerMap.put("custDate", cursor.getString(4));
//                customerMap.put("custPhone", cursor.getString(5));
//                customerMap.put("custZip", cursor.getString(6));
//            } while(cursor.moveToNext());
//        }
//        cursor.close();
//        return customerMap;
//    }
//    public static class DbConnection { //sets a connection up with DB browser and opens a specific file
//        private static final String data_base_location = "jdbc:sqlite:C:\\Users\\zachc\\AndroidStudioProjects\\FinalCalculatorApp\\expressions.db"; //java database connection
//
//        //C:\Users\Jose0\Documents\customer_info.db
//        public static Connection connect() {
//            Connection con = null; //creating a connection object con and setting it to null
//            try {
//                con = DriverManager.getConnection(data_base_location);// goes out to the file and open a connection to the file
//                Log.d("Connected", "DB connected");
//            } catch (SQLException e) {
//                Log.d("Error catch" ,e + ""); //e is a generic error message from SQLException
//            }
//            return con;
//        }
//
//    }
//
//    public static void addExpression(String exp, Double val){
//        Connection con = DbConnection.connect();
//        PreparedStatement ps = null;
//        try {
//            String sql = "INSERT INTO table1(expression, value) VALUES(?,?)"; // commands that it is sending to sql
//            ps = con.prepareStatement(sql);
//            ps.setString(1,exp);
//            ps.setDouble(2,val);
//            int rowsAffected = ps.executeUpdate();
//            if (rowsAffected == 1) {
//                con.commit(); // commit the transaction if one row was inserted
//                System.out.println("Inserted");
//            } else {
//                System.out.println("No rows inserted");
//            }
//        }catch (SQLException e){
//            System.out.println(e+"");
//        } finally {
//            try {
//                assert ps != null; // confirms the ps is not null
//                ps.close();
//            } catch (SQLException e){
//                System.out.println(e+"");
//            }
//        }
//    }



    public static <E> void println(E item){
        System.out.println(item);
    }
}

