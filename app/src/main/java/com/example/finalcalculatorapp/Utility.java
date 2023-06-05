package com.example.finalcalculatorapp;

import java.util.HashMap;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;
public class Utility {
    HashMap<String,Integer> operatorMap = hashPrec();
    /**
     * checks if an incoming operator has higher precedence over the top operator on the stack
     *
     * @param in_operator - the incoming operator to be evaluated before being pushed on the stack
     * @param top_operator - the top operator on the stack
     * @return - returns true if operator is less than the top operator on the stack.
     */
    public static boolean higher_precedence(String in_operator,String top_operator){
        HashMap<String,Integer> operatorMap = hashPrec();
        if(operatorMap.containsKey(in_operator))
            return operatorMap.get(in_operator) < operatorMap.get(top_operator);
        return false;
    }

    /**
     * constructs a HashMap of operators with values containing precedence values
     *
     * @return - returns a HashMap of all operators and their respected precedence values.
     */
    public static HashMap<String,Integer> hashPrec(){
        HashMap<String, Integer> operatorMap = new HashMap<String, Integer>();
        operatorMap.put("+", 3);
        operatorMap.put("-", 3);
        operatorMap.put("*", 2);
        operatorMap.put("/", 2);
        operatorMap.put("^",1);
        operatorMap.put("!",0);
        operatorMap.put("%", 2);
        operatorMap.put("sin",1);
        operatorMap.put("cos",1);
        operatorMap.put("tan",1);
        operatorMap.put("cot",1);
        operatorMap.put("csc",1);
        operatorMap.put("sec",1);
        operatorMap.put("ln",1);
        operatorMap.put("log",1);
        operatorMap.put("abs",1);
        operatorMap.put("sqrt",1);
        operatorMap.put("(",4);
        operatorMap.put(")",4);
        return operatorMap;
    }
    /**
     * checks if the incoming string function is a valid alphabetic function, such as trig, sqrt, ln, etc
     * @param func - a string of an alphabetic function
     * @return - returns true iff valid function
     */
    public static boolean alphabetic_function_valid(String func){
        return func.equals("sin") || func.equals("cos") || func.equals("tan") || func.equals("cot") || func.equals("csc") || func.equals("sec") || func.equals("ln") || func.equals("log") || func.equals("abs") || func.equals("sqrt");
    }
    @SuppressWarnings("unused")
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public static boolean isInt(double num){
        return Math.floor(num) == num;
    }
    public static int recFact(int num){
        if(num==0 || num ==1)
            return 1;
        return num * recFact(num-1);
    }
    public static boolean isDub(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
