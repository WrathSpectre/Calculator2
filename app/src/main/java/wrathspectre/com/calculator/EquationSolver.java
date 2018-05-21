package wrathspectre.com.calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class EquationSolver {

    private String infixExpression, postfixExpression;

    final private Map<Character, Integer> precedence;

    double result;

    EquationSolver(final String infixExpression) {
        this.infixExpression = infixExpression;

        precedence = new HashMap<>();
        precedence.put('+', 1);
        precedence.put('-', 1);
        precedence.put('*', 2);
        precedence.put('/', 2);
        precedence.put('^', 3);
    }

    public void infixToPostfix() {
        StringBuilder stringBuilder = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for(Character ch : infixExpression.toCharArray()) {
            if(Character.isDigit(ch) || ch.equals('.')) {
                stringBuilder.append(ch);
            }

            else if(precedence.containsKey(ch)) {
                stringBuilder.append(' ');

                while(!stack.empty() && !stack.peek().equals('(') && hasHigherPrecedence(stack.peek(), ch)) {
                    stringBuilder.append(stack.peek()).append(' ');
                    stack.pop();
                }

                stack.push(ch);
            }

            else if(ch == '(') {
                stack.push('(');
            }

            else if(ch == ')') {
                while(stack.empty() || !stack.peek().equals('(')) {
                    stringBuilder.append(' ').append(stack.peek());
                    stack.pop();
                }

                stack.pop();
            }
        }

        while(!stack.empty()) {
            stringBuilder.append(' ').append(stack.peek());
            stack.pop();
        }

        postfixExpression = stringBuilder.toString();
    }

    private boolean hasHigherPrecedence(Character sign_1, Character sign_2) {
        return precedence.get(sign_1) >= precedence.get(sign_2);
    }

    public void evaluatePostfix() {
        Stack<Double> stack = new Stack<>();
        double buffor = 0.0;

        String[] parse = postfixExpression.split(" ");

        for(String str : parse) {
            if(precedence.containsKey(str.charAt(0))) {
                Double firstNumber = stack.peek();
                stack.pop();

                Double secondNumber = stack.peek();
                stack.pop();

                switch(str.charAt(0)) {
                    case '+':
                        buffor = secondNumber + firstNumber;
                        break;

                    case '-':
                        buffor = secondNumber - firstNumber;
                        break;

                    case '*':
                        buffor = secondNumber * firstNumber;
                        break;

                    case '/':
                        buffor = secondNumber / firstNumber;
                        break;
                }

                stack.push(buffor);
            }

            else {
                Double number = Double.valueOf(str);
                stack.push(number);
            }
        }

        result = stack.peek();
    }

    public String getResult() {
        return Double.toString(result);
    }
}
