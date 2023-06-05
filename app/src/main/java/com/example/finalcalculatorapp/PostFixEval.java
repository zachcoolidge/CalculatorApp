package com.example.finalcalculatorapp;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Stack;
//import DSFinal.PostFixConstruction;
import java.util.Queue;
import java.text.DecimalFormat;
public class PostFixEval extends Activity {
    SQL_Database sql_database = new SQL_Database(this);
    private TextView result;

    /**
     * Evaluates a postfix notation expression
     * @param queue - A queue that contains the postfix notation
     * @return - returns the evaluation of expression as a double.
     * @throws Exception - Throws exception if error in postfix notation
     */
    public static double evaluate_post_fix(Queue<String> queue) throws Exception{

        Stack<Double> nums = new Stack<>();
        StringBuilder exp = new StringBuilder();
        if(MainActivity.pre_comp_ans.containsKey(queue+""))
            return MainActivity.pre_comp_ans.get(queue+"");
            //result.setText(Double.toString(MainActivity.pre_comp_ans.get(queue+"")));
        else{
        for(String elmnt: queue) {
            exp.append(elmnt);
            if (isNumeric(elmnt) || isDub(elmnt))
                nums.push(Double.parseDouble(elmnt));
            else {
                if (nums.size() < 1 || nums.empty()) {
                    throw new Exception("Invalid input, try again");
                }
                switch (elmnt) {
                    case "+":
                        double num2 = nums.pop();
                        double num1 = nums.pop();
                        nums.push(num1 + num2);
                        break;
                    case "-":
                        num2 = nums.pop();
                        num1 = nums.pop();
                        nums.push(num1 - num2);
                        break;
                    case "*":
                        num2 = nums.pop();
                        num1 = nums.pop();
                        nums.push(num1 * num2);
                        break;
                    case "%":
                        num2 = nums.pop();
                        num1 = nums.pop();
                        nums.push(num1 % num2);
                        break;
                    case "/":
                        num2 = nums.pop();
                        num1 = nums.pop();
                        if (num2 == 0) {
                            throw new RuntimeException("Divide by zero error, check input!");
                        }
                        nums.push(num1 / num2);
                        break;
                    case "!":
                        num1 = nums.pop();
                        if (isInt(num1) && num1 > -1) {
                            nums.push((double) recFact((int) num1));
                        }
                        break;
                    case "^":
                        num2 = nums.pop();
                        num1 = nums.pop();
                        nums.push(Math.pow(num1,num2));
                        break;
                    case "sin":
                        num1 = nums.pop();
                        nums.push(Math.sin(num1));
                        break;
                    case "cos":
                        num1 = nums.pop();
                        nums.push(Math.cos(num1));
                        break;
                    case "tan":
                        num1 = nums.pop();
                        nums.push(Math.tan(num1));
                        break;
                    case "cot":
                        num1 = nums.pop();
                        nums.push(1 / Math.tan(num1));
                        break;
                    case "sec":
                        num1 = nums.pop();
                        nums.push(1 / Math.cos(num1));
                        break;
                    case "csc":
                        num1 = nums.pop();
                        nums.push(1 / Math.sin(num1));
                        break;
                    case "ln":
                        num1 = nums.pop();
                        nums.push(Math.log(num1));
                        break;
                    case "log":
                        num1 = nums.pop();
                        nums.push(Math.log10(num1));
                        break;
                    case "abs":
                        num1 = nums.pop();
                        nums.push(Math.abs(num1));
                        break;
                    case "sqrt":
                        num1 = nums.pop();
                        nums.push(Math.sqrt(num1));
                        break;
                    default:
                        System.out.println("Unknown Character, check input!");
                        break;
                }
            }
        }
        }
        if(nums.size() >1)
            throw new Exception("Invalid input try again!");
        else {
            if(nums.peek()<1000000) {
                //MainActivity.pre_comp_ans.put(queue + "", nums.peek());
                DecimalFormat decimalFormat = new DecimalFormat("#.##########");
                decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
                return Double.parseDouble(decimalFormat.format(nums.pop()));
            }else {
                DecimalFormat decimalFormat = new DecimalFormat("#.########E0");
                decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
                return Double.parseDouble(decimalFormat.format(nums.pop()));
            }
        }
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
//Where I got the isNumeric method - https://www.baeldung.com/java-check-string-number#:~:text=Perhaps%20the%20easiest%20and%20the,Double.parseDouble(String)



