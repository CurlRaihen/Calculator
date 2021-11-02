package com.example.calculator;

import java.math.BigDecimal;
import java.text.ParseException;

public class Model {

    private BigDecimal result = BigDecimal.ZERO;
    private BigDecimal inputNumber1;
    private BigDecimal inputNumber2;
    private String inputOperator;
    private boolean decimalIsOn = false;


    public void add() {
        result = inputNumber1.add(inputNumber2);
        inputNumber1 = result;
        inputNumber2 = null;
        inputOperator = null;
    }
    public void subtract() {
        result = inputNumber1.subtract(inputNumber2);
        inputNumber1 = result;
        inputNumber2 = null;
        inputOperator = null;
    }
    public void multiply() {
        result = inputNumber1.multiply(inputNumber2);
        inputNumber1 = result;
        inputNumber2 = null;
        inputOperator = null;
    }
    public void divide() {
        result = inputNumber1.divide(inputNumber2);
        inputNumber1 = result;
        inputNumber2 = null;
        inputOperator = null;
    }

    public BigDecimal negate(BigDecimal operand) {
        if(inputNumber2 == null) {
            inputNumber1 = operand.multiply(BigDecimal.valueOf(-1));
            return inputNumber1;
        } else {
            inputNumber2 = operand.multiply(BigDecimal.valueOf(-1));
            return inputNumber2;
        }
    }

    public BigDecimal percentageOf(BigDecimal operand) {
        if(inputNumber2 == null) {
            result = operand.divide(BigDecimal.valueOf(100));
            inputNumber1 = result;
            inputOperator = null;
            return inputNumber1;
        } else {
            inputNumber2 = operand.divide(BigDecimal.valueOf(100));
            return inputNumber2;
        }
    }

    public void resetAll() {
        this.result = BigDecimal.ZERO;
        this.inputNumber1 = null;
        this.inputNumber2 = null;
        this.inputOperator = null;
        this.decimalIsOn = false;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) throws ParseException {
        this.result = result;
    }

    public BigDecimal getInputNumber1() {
        return inputNumber1;
    }

    public void setInputNumber1(BigDecimal inputNumber) {
        this.inputNumber1 = inputNumber;
    }

    public BigDecimal getInputNumber2() {
        return inputNumber2;
    }

    public void setInputNumber2(BigDecimal inputNumber2) {
        this.inputNumber2 = inputNumber2;
    }

    public void setInputOperator(String inputOperator) {
        this.inputOperator = inputOperator;
    }

    public String getOperator() {
        return inputOperator;
    }

    public boolean isDecimalIsOn() {
        return decimalIsOn;
    }

    public void setDecimalIsOn(boolean decimalIsOn) {
        this.decimalIsOn = decimalIsOn;
    }
}
