package com.example.finalcalculatorapp;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import android.util.Log;
public class EquationEvaluate {
    public static String solve(String equation) {
        IExpr eq = new ExprEvaluator().eval("Solve(" + equation + ",x)");
        return eq + "";
    }
}
