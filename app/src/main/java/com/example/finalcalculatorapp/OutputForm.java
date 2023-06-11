package com.example.finalcalculatorapp;

import org.matheclipse.core.form.output.OutputFormFactory;
import org.matheclipse.core.form.tex.TeXFormFactory;
import org.matheclipse.core.interfaces.IExpr;

public class OutputForm {
    public static String toString(IExpr result) {
        if (result == null) {
            return "";
        }
        try {
            OutputFormFactory outputFormFactory = OutputFormFactory.get(true, false);
            return outputFormFactory.toString(result);
        } catch (Exception e) {
            return "Error";
        }
    }

    public static String toLatex(IExpr expr) {
        if (expr == null) {
            return "";
        }
        try {
            TeXFormFactory teXFormFactory = new TeXFormFactory();
            StringBuilder buffer = new StringBuilder();
            teXFormFactory.convert(buffer, expr,1);
                return buffer.toString();
        } catch (Exception e) {
            return "Error";
        }
    }
}

