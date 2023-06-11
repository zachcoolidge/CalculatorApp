package com.example.finalcalculatorapp;

import android.util.Log;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.matheclipse.core.form.output.OutputFormFactory;
import org.matheclipse.core.form.tex.TeXFormFactory;
import java.util.function.Function;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;

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
        Function<Double, Double> function = x -> symbolic_exp(exp, x);

        // Define the number of intervals (higher value gives better accuracy)
        int numIntervals = 1000000;

        // Evaluate the integral
        double result = simp_comp_eval(function, a, b, numIntervals);

        Log.d("Evaluated","The integral of f(x) = " + exp + " from " + a + " to " + b + " is: " + result);
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
    public static String symbolic_indefinite(String expression){
        IExpr integral = new ExprEvaluator().eval("integrate("+expression+",x)");

        return integral + "";
    }
    public static String symbolic_definite(String expression, double a, double b){
            IExpr integral = new ExprEvaluator().eval("integrate(" + expression + ",x)");
            String integ = "" + integral;
            IExpr a_bound = new ExprEvaluator().eval(integ.replaceAll("x", Double.toString(a)));
            IExpr b_bound = new ExprEvaluator().eval(integ.replaceAll("x", Double.toString(b)));
            String result = Double.toString(Double.parseDouble("" + b_bound) - Double.parseDouble("" + a_bound));
            String rtn = integral + "";
            rtn = rtn.replaceAll("Log","ln");
            return result;
    }
}
