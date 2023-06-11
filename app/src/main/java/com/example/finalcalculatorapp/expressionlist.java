//package com.example.finalcalculatorapp;
//
//import android.app.ListActivity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//import android.widget.SimpleAdapter;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class expressionlist extends ListActivity {
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        SQL_Database sqlDB = new SQL_Database(this);
//        // Assuming you have the following ArrayList of HashMap<String, Double>
//        ArrayList<HashMap<String, Double>> data = sqlDB.getAllExp();
//
//        // Get a reference to your ListView
//        ListView listView = findViewById(R.id.history_listview);
//
//        // Create a custom ArrayAdapter to populate the ListView
//        ArrayAdapter<HashMap<String, Double>> adapter = new ArrayAdapter<HashMap<String, Double>>(this, R.layout.explistentry, R.id.exp, data) {
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View view = super.getView(position, convertView, parent);
//
//                // Get the HashMap at the current position
//                HashMap<String, Double> item = getItem(position);
//
//                // Get the key and value from the HashMap
//                String expression = item.keySet().iterator().next();
//                Double value = item.get(expression);
//
//                // Set the expression and value in the TextViews of the ListView item
//                TextView expressionTextView = view.findViewById(R.id.exp);
//                TextView valueTextView = view.findViewById(R.id.value);
//
//                expressionTextView.setText(expression);
//                valueTextView.setText(String.valueOf(value));
//
//                return view;
//            }
//
//        };
//        listView.setAdapter(adapter);
//// Set the adapter to the ListView
//    }
//}
