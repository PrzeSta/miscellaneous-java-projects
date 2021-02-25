package calculator_app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.math.BigInteger;

public class Controller {

    private Calculator calculator = new Calculator();
    private BigInteger currentValue = BigInteger.ZERO;
    private BigInteger previousValue = BigInteger.ZERO;
    private int radix = 10;

    @FXML
    private Label currentLabel, previousLabel, currentOperationLabel, radixLabel;

    @FXML
    private Button button2, button3, button4, button5, button6, button7, button8, button9, buttonA, buttonB, buttonC, buttonD, buttonE, buttonF;

    private void updateInputLabel(){
        currentLabel.setText(currentValue.toString(radix));
    }

    private void updatePreviousLabel(){
        previousLabel.setText(previousValue.toString(radix));
    }

    private void updateOperationLabel(){
        switch(calculator.operation) {
            case ADD -> currentOperationLabel.setText("ADD");
            case SUB -> currentOperationLabel.setText("SUB");
            case MUL -> currentOperationLabel.setText("MUL");
            case DIV -> currentOperationLabel.setText("DIV");
            case MOD -> currentOperationLabel.setText("MOD");
            case FAC -> currentOperationLabel.setText("FAC");
            case NEWT -> currentOperationLabel.setText("NEWT");
            case POW -> currentOperationLabel.setText("POW");
            case NONE -> currentOperationLabel.setText("NONE");
        }
    }

    private void updateRadixLabel(){
        switch(radix){
            case 2 -> radixLabel.setText("BIN");
            case 10 -> radixLabel.setText("DEC");
            case 16 -> radixLabel.setText("HEX");
        }
    }

    private void updateLabels(){
        updateInputLabel();
        updatePreviousLabel();
        updateOperationLabel();
        updateRadixLabel();
    }

    private void changeButtonStatus(Boolean status){
        button2.setDisable(status);
        button3.setDisable(status);
        button4.setDisable(status);
        button5.setDisable(status);
        button6.setDisable(status);
        button7.setDisable(status);
        button8.setDisable(status);
        button9.setDisable(status);
        buttonA.setDisable(status);
        buttonB.setDisable(status);
        buttonC.setDisable(status);
        buttonD.setDisable(status);
        buttonE.setDisable(status);
        buttonF.setDisable(status);
    }

    private void changeRadix(int newRadix){
        radix = newRadix;
        currentValue = new BigInteger(currentValue.toString(radix), radix);
        previousValue = new BigInteger(previousValue.toString(radix), radix);
        if(calculator.operation != Calculator.Operation.NONE)
            calculator.changeRadix(radix);

        updateLabels();
    }

    @FXML
    protected void handleButtonBinAction(ActionEvent actionEvent) {
        if(radix != 2) {
            changeRadix(2);
            changeButtonStatus(true);
        }
    }

    @FXML
    protected void handleButtonDecAction(ActionEvent actionEvent) {
        if(radix != 10) {
            changeRadix(10);
            changeButtonStatus(false);
            buttonA.setDisable(true);
            buttonB.setDisable(true);
            buttonC.setDisable(true);
            buttonD.setDisable(true);
            buttonE.setDisable(true);
            buttonF.setDisable(true);
        }
    }

    @FXML
    protected void handleButtonHexAction(ActionEvent actionEvent) {
        if(radix != 16) {
            changeRadix(16);
            changeButtonStatus(false);
        }
    }

    @FXML
    protected void handleButtonDELAction(ActionEvent actionEvent) {
        if(!currentValue.equals(BigInteger.ZERO)) {
            currentValue = currentValue.divide(BigInteger.valueOf(radix));
            updateInputLabel();
        }
    }

    @FXML
    protected void handleButtonACAction(ActionEvent actionEvent) {
        currentValue = BigInteger.ZERO;
        previousValue = BigInteger.ZERO;
        calculator = new Calculator();
        radix = 10;
        changeRadix(10);
    }

    @FXML
    protected void handleButtonChSiAction(ActionEvent actionEvent) {
        currentValue = currentValue.negate();
        updateInputLabel();
    }

    private void handleNumberButton(String value){
        currentValue = currentValue.multiply(BigInteger.valueOf(radix)).add(new BigInteger(value, radix));
        updateInputLabel();
    }

    @FXML
    protected void handleButton1Action(ActionEvent actionEvent) {
        handleNumberButton("1");
    }

    @FXML
    protected void handleButton2Action(ActionEvent actionEvent) {
        handleNumberButton("2");
    }

    @FXML
    protected void handleButton3Action(ActionEvent actionEvent) {
        handleNumberButton("3");
    }

    @FXML
    protected void handleButton4Action(ActionEvent actionEvent) {
        handleNumberButton("4");
    }

    @FXML
    protected void handleButton5Action(ActionEvent actionEvent) {
        handleNumberButton("5");
    }

    @FXML
    protected void handleButton6Action(ActionEvent actionEvent) {
        handleNumberButton("6");
    }

    @FXML
    protected void handleButton7Action(ActionEvent actionEvent) {
        handleNumberButton("7");
    }

    @FXML
    protected void handleButton8Action(ActionEvent actionEvent) {
        handleNumberButton("8");
    }

    @FXML
    protected void handleButton9Action(ActionEvent actionEvent) {
        handleNumberButton("9");
    }

    @FXML
    protected void handleButton0Action(ActionEvent actionEvent) {
        handleNumberButton("0");
    }

    @FXML
    protected void handleButtonAAction(ActionEvent actionEvent) {
        handleNumberButton("A");
    }

    @FXML
    protected void handleButtonBAction(ActionEvent actionEvent) {
        handleNumberButton("B");
    }

    @FXML
    protected void handleButtonCAction(ActionEvent actionEvent) {
        handleNumberButton("C");
    }

    @FXML
    protected void handleButtonDAction(ActionEvent actionEvent) {
        handleNumberButton("D");
    }

    @FXML
    protected void handleButtonEAction(ActionEvent actionEvent) {
        handleNumberButton("E");
    }

    @FXML
    protected void handleButtonFAction(ActionEvent actionEvent) {
        handleNumberButton("F");
    }

    private void calculate(){
        if(calculator.operation != Calculator.Operation.FAC)
            calculator.take2nd(currentValue);

        boolean error = false;
        if(calculator.state != Calculator.State.ReadyForResult)
            error = true;
        else {
            switch (calculator.operation) {
                case DIV, MOD -> {if(currentValue.equals(BigInteger.ZERO)) error = true;}
                case NEWT -> {if(currentValue.compareTo(previousValue) != -1) error = true;}
                default -> error = false;
            }
        }
        if(error){
            calculator = new Calculator();
            previousValue = BigInteger.ZERO;
            currentValue = BigInteger.ZERO;
            updateLabels();
            currentLabel.setText("ERROR");
        }
        else {
            previousValue = calculator.calculate();
            currentValue = BigInteger.ZERO;
            calculator = new Calculator();
            updateLabels();
        }
    }

    private void handleOperatorButton(Calculator.Operation operation){
        calculator.take1st(currentValue, operation);
        previousValue = currentValue;
        currentValue = BigInteger.ZERO;
        updateLabels();
    }

    @FXML
    protected void handleButtonAddAction(ActionEvent actionEvent) {
        handleOperatorButton(Calculator.Operation.ADD);
    }

    @FXML
    protected void handleButtonSubAction(ActionEvent actionEvent) {
        handleOperatorButton(Calculator.Operation.SUB);
    }

    @FXML
    protected void handleButtonMulAction(ActionEvent actionEvent) {
        handleOperatorButton(Calculator.Operation.MUL);
    }

    @FXML
    protected void handleButtonDivAction(ActionEvent actionEvent) {
        handleOperatorButton(Calculator.Operation.DIV);
    }

    @FXML
    protected void handleButtonModAction(ActionEvent actionEvent) {
        handleOperatorButton(Calculator.Operation.MOD);
    }

    @FXML
    protected void handleButtonFacAction(ActionEvent actionEvent) {
        calculator.take1st(currentValue, Calculator.Operation.FAC);
        calculate();
        updateLabels();
    }

    @FXML
    protected void handleButtonPowAction(ActionEvent actionEvent) {
        handleOperatorButton(Calculator.Operation.POW);
    }

    @FXML
    protected void handleButtonNewtAction(ActionEvent actionEvent) {
        handleOperatorButton(Calculator.Operation.NEWT);
    }

    @FXML
    protected void handleButtonReuAction(ActionEvent actionEvent) {
        currentValue = previousValue;
        updateInputLabel();
        updateOperationLabel();
    }

    @FXML
    protected void handleButtonExeAction(ActionEvent actionEvent) {
        if(calculator.operation == Calculator.Operation.NONE){
            previousValue = currentValue;
            currentValue = BigInteger.ZERO;
            updateLabels();
        }
        else
            calculate();
    }
}
