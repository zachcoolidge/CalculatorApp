package com.example.finalcalculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

public class Integral extends AppCompatActivity{
    static HashMap<String, Double> pre_comp_ans = new HashMap<String, Double>();
    private EditText input;
    private TextView result;
    private String ans;
    private GridLayout funcScreenLayout;
    private Button selectedFuncButton;
    private GridLayout mainScreenLayout;
    private EditText a_bound;
    private EditText b_bound;
    SQL_Database sql_database = new SQL_Database(this);
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.integration_layout); // telling java where to look in the xml file
        result = (TextView) findViewById((R.id.result));
        input = (EditText) findViewById(R.id.editText_input);
        input.requestFocus();
        input.setShowSoftInputOnFocus(false);
        a_bound = (EditText) findViewById(R.id.editText_a_bound);
        b_bound = (EditText) findViewById(R.id.editText_b_bound);
        funcScreenLayout = findViewById(R.id.func_screen_layout);
        mainScreenLayout = findViewById(R.id.main_screen_layout);
        funcScreenLayout.setVisibility(View.GONE);
        ;
        SQL_Database sqlDB = new SQL_Database(this);
        // Assuming you have the following ArrayList of HashMap<String, Double>
        ArrayList<HashMap<String, Double>> data = sqlDB.getAllExp();
        // Get a reference to your ListView
        ListView listView = findViewById(R.id.history_listview);

        // Create a custom ArrayAdapter to populate the ListView
        ArrayAdapter<HashMap<String, Double>> adapter = new ArrayAdapter<HashMap<String, Double>>(this, R.layout.explistentry, R.id.exp, data) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                // Get the HashMap at the current position
                HashMap<String, Double> item = getItem(position);

                // Get the key and value from the HashMap
                String expression = item.keySet().iterator().next();
                Double value = item.get(expression);

                // Set the expression and value in the TextViews of the ListView item
                TextView expressionTextView = view.findViewById(R.id.exp);
                TextView valueTextView = view.findViewById(R.id.value);

                expressionTextView.setText(expression);
                valueTextView.setText(String.valueOf(value));

                return view;
            }

        };
        listView.setAdapter(adapter);
        closeKeyboard();
        Button funcButton = findViewById(R.id.button_func);
        funcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Show the "func" screen and hide the main screen
                mainScreenLayout.setVisibility(View.GONE);
                funcScreenLayout.setVisibility(View.VISIBLE);
            }
        });
        Button numButton = findViewById(R.id.button_123);
        numButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the "func" screen and hide the main screen
                mainScreenLayout.setVisibility(View.VISIBLE);
                funcScreenLayout.setVisibility(View.GONE);
            }
        });
        EditText editText = findViewById(R.id.editText_input);
        editText.setEllipsize(TextUtils.TruncateAt.START);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                editText.setSingleLine(true);
                editText.setMaxLines(1);
            }
        });
    }
    public void equalsButton(View view) throws Exception {
        String in = input.getText().toString();
        Double a = Double.parseDouble(a_bound.getText().toString());
        Double b = Double.parseDouble(b_bound.getText().toString());
        Double val = IntegralEvaluate.eval(in,a,b);
        result.setText(Double.toString(val));
        //input.setText("");
        //result.setText("");
    }
    //    protected void onPause(){
//        super.onPause();
//        sql_database.insertExpression(pre_comp_ans);
//        Log.d("Paused","App paused");
//    }
    public void zeroButtonClick(View view) {
        // Get the currently selected EditText field
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "0");
        selectedEditText.setSelection(cursorPosition + 1);
    }

    public void oneButtonClick(View view){
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "1");
        selectedEditText.setSelection(cursorPosition + 1);
    }
    public void twoButtonClick(View view){
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "2");
        selectedEditText.setSelection(cursorPosition + 1);
    }
    public void threeButtonClick(View view){
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "3");
        selectedEditText.setSelection(cursorPosition + 1);
    }
    public void fourButtonClick(View view){
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "4");
        selectedEditText.setSelection(cursorPosition + 1);
    }
    public void fiveButtonClick(View view){
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "5");
        selectedEditText.setSelection(cursorPosition + 1);
    }
    public void sixButtonClick(View view){
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "6");
        selectedEditText.setSelection(cursorPosition + 1);
    }
    public void sevenButtonClick(View view){
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "7");
        selectedEditText.setSelection(cursorPosition + 1);
    }
    public void eightButtonClick(View view){
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "8");
        selectedEditText.setSelection(cursorPosition + 1);
    }
    public void nineButtonClick(View view){
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "9");
        selectedEditText.setSelection(cursorPosition + 1);
    }
    public void addButtonClick(View view){
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "+");
        selectedEditText.setSelection(cursorPosition + 1);
    }
    public void multButtonClick(View view){
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "*");
        selectedEditText.setSelection(cursorPosition + 1);
    }
    public void divButtonClick(View view){
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "/");
        selectedEditText.setSelection(cursorPosition + 1);
    }
    public void subButtonClick(View view){
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "-");
        selectedEditText.setSelection(cursorPosition + 1);
    }
    public void decimalButtonClick(View view){
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, ".");
        selectedEditText.setSelection(cursorPosition + 1);
    }
    public void piButtonClick(View view){
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "π");
        selectedEditText.setSelection(cursorPosition + 1);
    }
    public void exponentButtonClick(View view){
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "^");
        selectedEditText.setSelection(cursorPosition + 1);
    }
    public void backButtonClick(View view){
        // Get the currently selected EditText field
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        if(cursorPosition>0)
            selectedEditText.setSelection(cursorPosition - 1);
    }
    public void forwardButtonClick(View view){
        // Get the currently selected EditText field
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        if(cursorPosition<selectedEditText.getText().toString().length())
            selectedEditText.setSelection(cursorPosition + 1);
    }
    //    public void backspaceButtonClick(View view){
//        // Get the currently selected EditText field
//        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
//        int cursorPosition = selectedEditText.getSelectionStart();
//        // Append "9" to the text in the selected EditText field
//        String currentValue = selectedEditText.getText().toString();
//        if(currentValue.length()>0) {
//            String newValue = currentValue.substring(0, currentValue.length() - 1);
//            selectedEditText.setText(newValue);
//            selectedEditText.setSelection(cursorPosition - 1);
//        }
//    }
    public void backspaceButtonClick(View view) {
        // Get the currently selected EditText field
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        if (cursorPosition > 0) {
            // Delete the character before the cursor position
            editableText.delete(cursorPosition - 1, cursorPosition);
            selectedEditText.setSelection(cursorPosition - 1);
        }
    }
    public void leftparButtonClick(View view) {
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "(");
        selectedEditText.setSelection(cursorPosition + 1);
    }
    public void rightparButtonClick(View view) {
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, ")");
        selectedEditText.setSelection(cursorPosition + 1);
    }
    public void eulerButtonClick(View view) {
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "e");
        selectedEditText.setSelection(cursorPosition + 1);
    }
    public void xButtonClick(View view) {
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "x");
        selectedEditText.setSelection(cursorPosition + 1);
    }

    public void clearButtonClick(View view){

        sql_database.deleteAll();
    }
    public void saveButtonClick(View view){
        sql_database.insertExpression(pre_comp_ans);
    }

    //    public void funcButtonClick(View view){
//
//    }
    public void derButtonClick(View view){

    }
    public void expressionButtonClick(View view){
        Intent theIntent = new Intent(getApplication(), MainActivity.class);
        startActivity(theIntent);
    }
    public void ansButtonClick(View view){
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        if(ans!=null) {
            editableText.insert(cursorPosition, ans);
            selectedEditText.setSelection(cursorPosition + ans.length());
        }
        else{
            //TODO -- snackbar error
        }
    }
    public void absButtonClick(View view) {
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "abs()");
        selectedEditText.setSelection(cursorPosition + 4);
    }
    public void sinButtonClick(View view) {
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "sin()");
        selectedEditText.setSelection(cursorPosition + 4);
    }
    public void cosButtonClick(View view) {
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "cos()");
        selectedEditText.setSelection(cursorPosition + 4);
    }
    public void tanButtonClick(View view) {
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();
        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "tan()");
        selectedEditText.setSelection(cursorPosition + 4);
    }
    public void cotButtonClick(View view) {
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "cot()");
        selectedEditText.setSelection(cursorPosition + 4);
    }
    public void cscButtonClick(View view) {
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "csc()");
        selectedEditText.setSelection(cursorPosition + 4);
    }
    public void secButtonClick(View view) {
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "sec()");
        selectedEditText.setSelection(cursorPosition + 4);
    }
    public void lnButtonClick(View view) {
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "ln()");
        selectedEditText.setSelection(cursorPosition + 3);
    }
    public void logButtonClick(View view) {
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "log()");
        selectedEditText.setSelection(cursorPosition + 4);
    }
    public void sqrtButtonClick(View view) {
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "sqrt()");
        selectedEditText.setSelection(cursorPosition + 5);
    }
    public void factButtonClick(View view) {
        EditText selectedEditText = (EditText) findViewById(getCurrentFocus().getId());
        int cursorPosition = selectedEditText.getSelectionStart();
        // Get the text from the selected EditText field
        Editable editableText = selectedEditText.getText();

        // Insert "0" at the cursor position
        editableText.insert(cursorPosition, "!");
        selectedEditText.setSelection(cursorPosition + 1);
    }

    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if(view!= null){
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}
