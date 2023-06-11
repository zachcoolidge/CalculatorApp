package com.example.finalcalculatorapp;
import android.util.Log;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.function.Function;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
public class DerivativeEvaluate {//todo - replace ans button with next higher derivative button, it takes the last thing answered and will derivate it, and will provide f''(x), f'''(x)... etc
    public static String symbolic_der(String expression){
        IExpr derivative = new ExprEvaluator().eval("D(" + expression + ",x)");
        return "" + derivative;
    }
    public static String at_point_der(String expression, double a){
        IExpr derivative = new ExprEvaluator().eval("D(" + expression + ",x)");
        String der = "" +derivative;
        IExpr sol = new ExprEvaluator().eval(der.replaceAll("x", Double.toString(a)));
        return "" + sol;
    }
}
