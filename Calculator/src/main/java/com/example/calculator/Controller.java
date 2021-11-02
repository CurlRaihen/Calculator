package com.example.calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Controller {
    Model model = new Model();
    DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.GERMANY);



    @FXML
    private Label lbl;
    public Button acButton;
    private String buttonBackground;



    @FXML
    protected void onMousePressed(MouseEvent mouseEvent) {

        Button button = (Button) mouseEvent.getSource();
        String buttonText = button.getText();
        buttonBackground = button.getStyle();

        if(buttonText.equals("AC") || button.equals("C")
                || buttonText.equals("+/-") || button.equals("%")) {
            button.setStyle("-fx-background-color: #605F5FFF;");
        } else if (buttonText.equals("+") || buttonText.equals("-")
                || buttonText.equals("÷") || buttonText.equals("×") || buttonText.equals("=")) {
            button.setStyle("-fx-background-color: #BD6F03FF;");
        } else {
            button.setStyle("-fx-background-color: #777777FF;");
        }
    }

    @FXML
    protected void onMouseReleased(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        button.setStyle(buttonBackground);
    }


    @FXML
    protected void onClickNumber(ActionEvent actionEvent) throws ParseException {
        String input = ((Button) actionEvent.getSource()).getText();
        df.setParseBigDecimal(true);
        BigDecimal operand = (BigDecimal) df.parse(input);
        this.setLabelNumbers(operand); //prints the clicked Numbers on the Label
    }


    @FXML
    protected void onClickOperation(ActionEvent actionEvent) throws ParseException {
        String input = ((Button) actionEvent.getSource()).getText();

        //Non-Arithmetic Operations
        switch (input) {
            case "C":
                model.resetAll();
                lbl.setFont(new Font("Helvetica Light", 46.0));
                setLabelText(model.getResult());
                acButton.setText("AC");
                break;
            case "AC":
                model.resetAll();
                lbl.setFont(new Font("Helvetica Light", 46.0));
                setLabelText(model.getResult());
            case "+/-":
                BigDecimal negatedOperand = model.negate(new BigDecimal(lbl.getText()));
                setLabelText(negatedOperand);
                break;
            case ".":
                if(model.isDecimalIsOn()){
                    return;
                }
                if(model.getInputNumber1() == null) {
                    acButton.setText("C");
                    model.setInputNumber1(new BigDecimal(lbl.getText()));
                }
                lbl.setText("" + lbl.getText() + ".");
                model.setDecimalIsOn(true);
                break;
            default:
                model.setDecimalIsOn(false);
                if(model.getInputNumber2() != null) {
                    this.onClickEqualSign(new ActionEvent());
                }
                model.setInputOperator(input);
                break;
        }
    }

    @FXML
    public void onPercentageBtn(ActionEvent actionEvent) {
        BigDecimal operand = model.percentageOf(new BigDecimal(lbl.getText()));
        setLabelText(operand);
    }

    @FXML
    public void onClickEqualSign(ActionEvent actionEvent) {
        //Preventing Exception Throw if number 2 isnt init
        if(model.getInputNumber1() == null) {
            return;
        }
        else if(model.getInputNumber2() == null) {
            setLabelText(model.getInputNumber1().stripTrailingZeros());
            //labelText.setText("" + model.getInputNumber1().stripTrailingZeros());
        } else if(model.getInputNumber2().equals(BigDecimal.ZERO)) {
            lbl.setFont(new Font("Helvetica Light", 33));
            lbl.setText("not defined");
            model.resetAll();
        }
        else {

            //Calls the Operational Logic of the Model
            this.callArithmeticModelOperations();

            //Decimal points are cutted from whole number results
            BigDecimal bd = model.getInputNumber1().stripTrailingZeros();
            setLabelText(model.getInputNumber1().stripTrailingZeros());
            //labelText.setText("" + model.getResult().stripTrailingZeros());
        }
    }

    private void setLabelNumbers(BigDecimal operand) {
        //Sets InputNumber1
        if(model.getOperator() == null) {
            //Inits InputNumber1 is init
            if(model.getInputNumber1() == null) {
                model.setInputNumber1(operand);
            } else {
                //Sets InputNumber1 to a Point Number
                if(model.isDecimalIsOn()) {
                    String decimal = "" + lbl.getText() + operand;
                    model.setInputNumber1(new BigDecimal(decimal));
                } else {
                    model.setInputNumber1(model.getInputNumber1().multiply(BigDecimal.TEN).add(operand));
                }
            }
            setLabelText(model.getInputNumber1());
        }

        //Sets InputNumber2
        else {
            //Inits InputNumber2
            if(model.getInputNumber2() == null) {
                model.setInputNumber2(operand);
            } else {
                //Sets InputNumber2 to a Point Number
                if(model.isDecimalIsOn()) {
                    String decimal = "" + lbl.getText() + operand;
                    model.setInputNumber2(new BigDecimal(decimal));
                } else {
                    model.setInputNumber2(model.getInputNumber2().multiply(BigDecimal.TEN).add(operand));
                }
            }
            setLabelText(model.getInputNumber2());
        }
    }

    private void callArithmeticModelOperations() {
        //Calls the Operational Logic of the Model
        switch (model.getOperator()) {
            case "+": model.add();
                break;
            case "-": model.subtract();
                break;
            case "÷":
                model.divide();
                break;
            case "×": model.multiply();
                break;
        }
        //sets Operator to default
        model.setInputOperator(null);
    }

    private void setLabelText(BigDecimal bigDecimal) {
        String number = bigDecimal.toPlainString();
        lbl.setText(number);
    }

}