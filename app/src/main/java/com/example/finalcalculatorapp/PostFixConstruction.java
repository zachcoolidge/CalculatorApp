package com.example.finalcalculatorapp;
import java.util.Stack;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;


public class PostFixConstruction {
    /**
     * Constructs a postfix expression
     *
     *
     * @param expString - A string that contains in-fix notation mathematical expression
     * @return - returns a Queue of strings, which is a post-fix notation expression.
     */
    public static Queue<String> construct_post(String expString){
        //Stack<Double> nums = new Stack<Double>();
        HashMap<String,Integer> operatorMap = hashPrec();
        Stack<String> brackOpStack = new Stack<String>();//used for any parenthesis, absolute value brackets, and operations
        //Stack<String> operations = new Stack<String>();
        Queue<String> eval_queue = new LinkedList<String>(); //the final queue which contains the postfix notation
        String pattern = "*-+/%!^"; // pattern for common operations
        for(int i=0;i<expString.length();i++){//checks every character in the expression
            char c = expString.charAt(i);

            // checks if the current character is a digit
            if(Character.isDigit(c)){
                String num = "";
                if(i>0 && expString.charAt(i-1)=='-'){
                    if(i==1)
                        num+='-';
                    else if(expString.charAt(i-2)=='(')
                        num+='-';
                    else{
                        num+='-';
                        while (!brackOpStack.isEmpty() && !higher_precedence("+", brackOpStack.peek())){
                            eval_queue.add(brackOpStack.pop());
                        }
                        brackOpStack.push("+");
                    }
                }
                num+=c;
                // captures the entire number by looping until a non-digit or decimal point is encountered
                while((i+1<expString.length()) && (Character.isDigit(expString.charAt(i+1)) || expString.charAt(i+1)=='.')){

                    num+=expString.charAt(i+1);
                    i++;
                }
                if(i<expString.length() -1 && expString.charAt(i+1)=='('){
                    while (!brackOpStack.isEmpty() && !higher_precedence("*", brackOpStack.peek())){
                        eval_queue.add(brackOpStack.pop());
                    }
                    brackOpStack.push("*");
                }

                // adds the number to a queue for later evaluation
                eval_queue.add(String.valueOf(num));
            }

            // checks if the current character is a left parenthesis
            else if(c=='('){
                // adds the left parenthesis to a stack
                brackOpStack.push(String.valueOf(c));
            }

            // checks if the current character is a right parenthesis
            else if(c==')'){
                // evaluates and adds all operators to the queue until a left parenthesis is encountered
                while(!brackOpStack.isEmpty() && !brackOpStack.peek().equals("(")){
                    eval_queue.add(brackOpStack.pop());
                }
                // removes the left parenthesis from the stack
                brackOpStack.pop();
                if(i<expString.length()-1 && Character.isDigit(expString.charAt(i+1))){
                    while (!brackOpStack.isEmpty() && !higher_precedence("*", brackOpStack.peek())){
                        eval_queue.add(brackOpStack.pop());
                    }
                    brackOpStack.push("*");
                }
            }

            // checks if the current character is an operator (+,*, /, ^)
            else if(pattern.contains(String.valueOf(c))){
                if(c=='-'){
                    if(Character.isDigit(expString.charAt(i+1)))
                        continue;
                }
                // evaluates and adds operators to the queue based on operator precedence rules
                while (!brackOpStack.isEmpty() && !higher_precedence(String.valueOf(c), brackOpStack.peek())){
                    eval_queue.add(brackOpStack.pop());
                }
                // adds the operator to the stack
                brackOpStack.push(String.valueOf(c));
                if(i<expString.length()-1 && expString.charAt(i+1)!='-' && pattern.contains(String.valueOf(expString.charAt(i+1))))
                    if(c=='!')
                        continue;
                    else
                        throw new IllegalArgumentException("Multiple operators next to eachother");
            }

            // checks if the current character is an alphabetic function (sin, cos, tan, log, etc.)
            else if(Character.isAlphabetic(c)){
                if(c=='e'){
                    eval_queue.add(""+ Math.exp(1));
                    continue;
                }
                else if(c=='π'){
                    eval_queue.add(""+3.1415926535897);
                    continue;
                }
                String alphabetic_function = "";
                alphabetic_function+=c;

                // captures the entire function name by looping until a non-alphabetic character is encountered
                while((i+1<expString.length()) && (Character.isAlphabetic(expString.charAt(i+1)))){
                    alphabetic_function+=expString.charAt(i+1);
                    i++;
                }

                // checks if the function is valid and can be evaluated
                if(alphabetic_function_valid(alphabetic_function)){
                    // evaluates and adds functions to the queue based on operator precedence rules
                    while (!brackOpStack.isEmpty() && !higher_precedence(alphabetic_function, brackOpStack.peek())){
                        eval_queue.add(brackOpStack.pop());
                    }
                    // adds the function to the stack
                    brackOpStack.push(alphabetic_function);
                }
                if(!alphabetic_function_valid(alphabetic_function))
                    throw new IllegalArgumentException("Unknown alphabetic charater");
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

}
//https://www.geeksforgeeks.org/java-util-hashmap-in-java-with-examples/#
//https://chat.openai.com (for future android studio)

//(2+3)*3/4+|-100|
//queue - 23+3*4/-100|+
//stack -