package com.example.finalcalculatorapp;

import android.util.Log;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.function.Function;

public class IntegralEvaluate {
    private static double simp_comp_eval(Function<Double, Double> function, double a, double b, int numIntervals) {
        double h = (b - a) / numIntervals;
        double sum = function.apply(a) + function.apply(b);

        for (int i = 1; i < numIntervals; i++) {
            double x = a + i * h;
            if (i % 2 == 0) {
                sum += 2 * function.apply(x);
            } else {
                sum += 4 * function.apply(x);
            }
        }

        return (h / 3) * sum;
    }

    public static Double eval(String exp, Double a, Double b) {
        // Define the function to integrate
        String func = "x^2";
        Function<Double, Double> function = x -> symbolic_exp(exp, x);

        // Define the integration limits

        // Define the number of intervals (higher value gives better accuracy)
        int numIntervals = 1000000;

        // Evaluate the integral
        double result = simp_comp_eval(function, a, b, numIntervals);

        Log.d("Evaluated","The integral of f(x) = " + func + " from " + a + " to " + b + " is: " + result);
        return result;
    }

    public static double symbolic_exp(String expression, double x) {
        Expression exp = new ExpressionBuilder(expression)
                .variable("x")
                .build();

        exp.setVariable("x", x);

        try {
            return exp.evaluate();
        } catch (ArithmeticException e) {
            System.out.println("Error in evaluating expression: " + e.getMessage());
            return 0.0; // Default value if an error occurs
        }
    }
}
