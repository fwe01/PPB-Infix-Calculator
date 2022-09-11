package com.example.infixcalculator.algorithm;

import java.util.Stack;

public class InfixCalculator {
    //karena merupakan infix operation maka dibutuhkan stack
    private Stack<Double> Value = new Stack<Double>();
    private Stack<Character> Operator = new Stack<Character>();
    private int error_stats;
    private double last_result;

    public InfixCalculator() {
        error_stats = 0;
        last_result = 0;
    }

    public boolean isNumber(String input, int at) {
        //cek apakah value negatif atau operator pengurang
        if (input.charAt(at) == '-') {
            return (input.charAt(at + 1) != ' ');
        }
        return ((input.charAt(at) >= '0' && input.charAt(at) <= '9'));
    }

    public boolean isOperator(char chr) {
        return (chr == '+' || chr == '-' || chr == 'X' || chr == '/' || chr == '^' || chr == '%');
    }

    public boolean isComma(char chr) {
        return (chr == '.');
    }

    static int precedence(char chr) {
        switch (chr) {
            case '+':
            case '-':
                return 1;
            case 'X':
            case '/':
            case '%':
                return 2;
            case '^':
                return 3;
        }
        return -1;
    }

    public double calculate() throws Exception {
        double operandB = Value.pop(),
                operandA = Value.pop();
        char operation = Operator.pop();

        switch (operation) {
            case '+':
                return (operandA + operandB);
            case '-':
                return (operandA - operandB);
            case 'X':
                return (operandA * operandB);
            case '/':
                if (operandB == 0.0) {
                    error_stats = 1;
                    throw new Exception("ERROR : Cannot divide by zero.");
                } else {
                    return (operandA / operandB);
                }
            case '%':
                return (operandA % operandB);
            case '^':
                return ((long) Math.pow(operandA, operandB));
        }
        return 0;
    }

    public String evaluate(String expressions) throws Exception {
        error_stats = 0;
        Value.clear();
        Operator.clear();
        for (int itr = 0; itr < expressions.length() && error_stats == 0; itr++) {
            char temp_char = expressions.charAt(itr);

            if (isNumber(expressions, itr) || isComma(temp_char)) {
                double comma = 1;
                int negative = 1;
                double temp_value = 0.0;
                boolean after_comma = false;

                if (temp_char == '-') {
                    negative = -1;
                    itr++;
                    temp_char = expressions.charAt(itr);
                }

                //karena diketahui merupakan angka mak di cek apakah ada digit" selanjutnya
                while (isNumber(expressions, itr) || isComma(temp_char)) {
                    if (after_comma) {
                        comma *= 10;
                    } else if (isComma(temp_char)) {
                        after_comma = true;
                        itr++;
                        temp_char = expressions.charAt(itr);
                        continue;
                    }


                    temp_value = temp_value * 10 + (temp_char - '0');

                    if (itr + 1 < expressions.length()) {
                        itr++;
                        temp_char = expressions.charAt(itr);
                    } else {
                        break;
                    }
                }
                //setelah didapat value yang diinginkan maka dapat dimasukan kedalam stack
                Value.push(temp_value * negative / comma);
            } else if (isOperator(temp_char)) {
                while (!Operator.isEmpty() && precedence(temp_char) <= precedence(Operator.peek())) {
                    double temp_result = calculate();
                    Value.push(temp_result);
                }
                Operator.push(temp_char);
            } else if (temp_char == '(') {
                //jika ditemukan buka kurung maka dimasukan ke dalam operator
                Operator.push(temp_char);
            } else if (temp_char == ')') {
                //lakukan seluruh perhitungan yang ada di dalam kurung
                while (Operator.peek() != '(') {
                    double temp_result = calculate();
                    Value.push(temp_result);
                }
                //karena ditemukan opertaor '(' maka dikeluarkan
                Operator.pop();
            } else if (Character.isAlphabetic(temp_char)) {
                if (expressions.length() < 3) {
                    error_stats = 1;
                    throw new Exception("ERROR : Non operand or operator input detected.");
                } else if (expressions.substring(itr, itr + 3).toUpperCase().equals("ANS")) {
                    Value.push(last_result);
                    itr += 2;
                } else {
                    error_stats = 1;
                    throw new Exception("ERROR : Non operand or operator input detected.");
                }
            }

        }

        while (!Operator.isEmpty() && error_stats == 0) {
            double temp_result = calculate();
            Value.push(temp_result);
        }

        if (error_stats == 1) {
            error_stats = 0;
            return "SYNTAX ERROR";
        } else {
            last_result = Value.pop();
            return String.valueOf(last_result);
        }
    }
}
