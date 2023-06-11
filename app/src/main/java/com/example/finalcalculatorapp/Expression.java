package com.example.finalcalculatorapp;

import static com.example.finalcalculatorapp.Utility.isDub;
import static com.example.finalcalculatorapp.Utility.isInt;
import static com.example.finalcalculatorapp.Utility.isNumeric;
import static com.example.finalcalculatorapp.Utility.recFact;

import android.util.Log;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Expression {//todo - add functionality to have e and pi multiply when put next to eachother
    private String expString;
    public Expression(String expression){
        expString=expression;
    }
    public Queue<String> construct_post() {
        //Stack<Double> nums = new Stack<Double>();
        Stack<String> brackOpStack = new Stack<>();//used for any parenthesis, absolute value brackets, and operations
        //Stack<String> operations = new Stack<String>();
        Queue<String> eval_queue = new LinkedList<>(); //the final queue which contains the postfix notation
        String pattern = "*-+/%!^"; // pattern for common operations
        for (int i = 0; i < expString.length(); i++) {//checks every character in the expression
            char c = expString.charAt(i);

            // checks if the current character is a digit
            if (Character.isDigit(c)) {
                String num = "";
                if (i > 0 && expString.charAt(i - 1) == '-') {
                    if (i == 1)
                        num += '-';
                    else if (expString.charAt(i - 2) == '(')
                        num += '-';
                    else {
                        num += '-';
                        while (!brackOpStack.isEmpty() && !Utility.higher_precedence("+", brackOpStack.peek())) {
                            eval_queue.add(brackOpStack.pop());
                        }
                        brackOpStack.push("+");
                    }
                }
                num += c;

                // captures the entire number by looping until a non-digit or decimal point is encountered
                while ((i + 1 < expString.length()) && (Character.isDigit(expString.charAt(i + 1)) || expString.charAt(i + 1) == '.')) {

                    num += expString.charAt(i + 1);
                    i++;
                }
                if (i < expString.length() - 1 && expString.charAt(i + 1) == '(') {
                    while (!brackOpStack.isEmpty() && !Utility.higher_precedence("*", brackOpStack.peek())) {
                        eval_queue.add(brackOpStack.pop());
                    }
                    brackOpStack.push("*");
                }
                if(i<expString.length()-1 && Character.isAlphabetic(expString.charAt(i+1))){
                    while (!brackOpStack.isEmpty() && !Utility.higher_precedence("*", brackOpStack.peek())) {
                        eval_queue.add(brackOpStack.pop());
                    }
                    brackOpStack.push("*");
                }

                // adds the number to a queue for later evaluation
                eval_queue.add(String.valueOf(num));
            }

            // checks if the current character is a left parenthesis
            else if (c == '(') {
                // adds the left parenthesis to a stack
                brackOpStack.push(String.valueOf(c));
            }

            // checks if the current character is a right parenthesis
            else if (c == ')') {
                // evaluates and adds all operators to the queue until a left parenthesis is encountered
                while (!brackOpStack.isEmpty() && !brackOpStack.peek().equals("(")) {
                    eval_queue.add(brackOpStack.pop());
                }
                // removes the left parenthesis from the stack
                brackOpStack.pop();
                if (i < expString.length() - 1 && Character.isDigit(expString.charAt(i + 1))) {
                    while (!brackOpStack.isEmpty() && !Utility.higher_precedence("*", brackOpStack.peek())) {
                        eval_queue.add(brackOpStack.pop());
                    }
                    brackOpStack.push("*");
                }
            }

            // checks if the current character is an operator (+,*, /, ^)
            else if (pattern.contains(String.valueOf(c))) {
                if (c == '-') {
                    if (Character.isDigit(expString.charAt(i + 1)))
                        continue;
                }
                // evaluates and adds operators to the queue based on operator precedence rules
                while (!brackOpStack.isEmpty() && !Utility.higher_precedence(String.valueOf(c), brackOpStack.peek())) {
                    eval_queue.add(brackOpStack.pop());
                }
                // adds the operator to the stack
                brackOpStack.push(String.valueOf(c));
                if (i < expString.length() - 1 && expString.charAt(i + 1) != '-' && pattern.contains(String.valueOf(expString.charAt(i + 1))))
                    if (c == '!')
                        continue;
                    else
                        throw new IllegalArgumentException("Multiple operators next to each other");
            }

            // checks if the current character is an alphabetic function (sin, cos, tan, log, etc.)
            else if (Character.isAlphabetic(c)) {
                if (c == 'e') {
                    eval_queue.add("" + Math.exp(1));
                    continue;
                } else if (c == 'π') {
                    eval_queue.add("" + 3.1415926535897);
                    continue;
                }
                String alphabetic_function = "";
                alphabetic_function += c;

                // captures the entire function name by looping until a non-alphabetic character is encountered
                while ((i + 1 < expString.length()) && (Character.isAlphabetic(expString.charAt(i + 1)))) {
                    alphabetic_function += expString.charAt(i + 1);
                    i++;
                }

                // checks if the function is valid and can be evaluated
                if (Utility.alphabetic_function_valid(alphabetic_function)) {
                    // evaluates and adds functions to the queue based on operator precedence rules
                    while (!brackOpStack.isEmpty() && !Utility.higher_precedence(alphabetic_function, brackOpStack.peek())) {
                        eval_queue.add(brackOpStack.pop());
                    }
                    // adds the function to the stack
                    brackOpStack.push(alphabetic_function);
                }
                if (!Utility.alphabetic_function_valid(alphabetic_function))
                    throw new IllegalArgumentException("Unknown alphabetic character");
            }

        }
        // evaluates and adds any remaining operators to the queue
        while (!brackOpStack.isEmpty()) {
            if(brackOpStack.peek().equals("("))
                throw new IllegalArgumentException("Open parenthesis found");
            eval_queue.add(brackOpStack.pop());
        }

        // returns the queue for evaluation
        return eval_queue;

    }

    /**
     * Evaluates a postfix notation expression
     * @param queue - A queue that contains the postfix notation
     * @return - returns the evaluation of expression as a double.
     * @throws Exception - Throws exception if error in postfix notation
     */
    @SuppressWarnings("ConstantConditions")
    public static double evaluate_post_fix(Queue<String> queue) throws Exception{
        //todo - large factorials showing negative
        Stack<Double> nums = new Stack<>();
        StringBuilder exp = new StringBuilder();
        if(MainActivity.pre_comp_ans.containsKey(queue+"")) {
            Log.d("Expression found", "Found");
            return MainActivity.pre_comp_ans.get(queue + "");
        }
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
}
