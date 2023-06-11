package com.example.finalcalculatorapp;

public class ExpressionItem {
    private String expression;
    private String value;
    private int count;

    public ExpressionItem(String expression, String value) {
        this.expression = expression;
        this.value = value;
    }
    // Getter methods
    public String getExpression() {
        return expression;
    }

    public String getValue() {
        return value;
    }

}

