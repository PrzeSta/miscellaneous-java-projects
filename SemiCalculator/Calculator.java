package calculator_app;

import java.math.BigInteger;

class Calculator {

    protected enum State{
        WaitingFor1stArg, WaitingFor2ndArg, ReadyForResult
    }

    protected enum Operation{
        ADD, SUB, DIV, MUL, MOD, FAC, NEWT, POW, NONE
    }

    private BigInteger arg1;
    private BigInteger arg2;
    protected Operation operation;
    protected State state;

    protected Calculator(){
        arg1 = null;
        arg2 = null;
        operation = Operation.NONE;
        state = State.WaitingFor1stArg;
    }

    protected void take1st(BigInteger arg, Operation op){
        arg1 = arg;
        operation = op;
        if(operation == Operation.FAC)
            state = State.ReadyForResult;
        else
            state = State.WaitingFor2ndArg;
    }

    protected void take2nd(BigInteger arg){
        arg2 = arg;
        state = State.ReadyForResult;
    }

    protected void changeRadix(int radix){
        arg1 = new BigInteger(arg1.toString(radix), radix);
    }

    private BigInteger factorial(BigInteger arg) {
        BigInteger result = BigInteger.ONE;
        for (int i = arg.intValue(); i > 0; i--)
            result = result.multiply(BigInteger.valueOf(i));
        return result;
    }

    protected BigInteger calculate(){

        BigInteger result = null;
        switch(operation) {
            case ADD -> result = arg1.add(arg2);
            case SUB -> result = arg1.subtract(arg2);
            case MUL -> result = arg1.multiply(arg2);
            case DIV -> result = arg1.divide(arg2);
            case MOD -> result = arg1.mod(arg2);
            case FAC -> result = factorial(arg1);
            case NEWT -> result = factorial(arg1).divide(factorial(arg2).multiply(factorial(arg1.subtract(arg2))));
            case POW -> result = arg1.pow(arg2.intValue());
            case NONE -> result = BigInteger.ZERO;
        }

        arg1 = null;
        arg2 = null;
        operation = Operation.NONE;
        state = State.WaitingFor1stArg;

        return result;
    }
}