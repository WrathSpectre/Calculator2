package wrathspectre.com.calculator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;

public class BasicCalculatorFragment extends Fragment { //recent all history

    private String expression = "";
    private boolean hasComma = false;
    private final Character[] operators = {'+', '-', '*', '/'};

    private TextView expressionViewer, resultViewer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.basic_calculator_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        expressionViewer = view.findViewById(R.id.expressionViewer);
        expressionViewer.setMovementMethod(new ScrollingMovementMethod());
        expressionViewer.setSelected(true);

        resultViewer = view.findViewById(R.id.resultViewer);

        view.findViewById(R.id.zero).setOnClickListener(new digitsOnClickListener("0"));
        view.findViewById(R.id.one).setOnClickListener(new digitsOnClickListener("1"));
        view.findViewById(R.id.two).setOnClickListener(new digitsOnClickListener("2"));
        view.findViewById(R.id.three).setOnClickListener(new digitsOnClickListener("3"));
        view.findViewById(R.id.four).setOnClickListener(new digitsOnClickListener("4"));
        view.findViewById(R.id.five).setOnClickListener(new digitsOnClickListener("5"));
        view.findViewById(R.id.six).setOnClickListener(new digitsOnClickListener("6"));
        view.findViewById(R.id.seven).setOnClickListener(new digitsOnClickListener("7"));
        view.findViewById(R.id.eight).setOnClickListener(new digitsOnClickListener("8"));
        view.findViewById(R.id.nine).setOnClickListener(new digitsOnClickListener("9"));
        view.findViewById(R.id.add).setOnClickListener(new operatorsOnClickListener("+"));
        view.findViewById(R.id.substract).setOnClickListener(new operatorsOnClickListener("-"));
        view.findViewById(R.id.multiply).setOnClickListener(new operatorsOnClickListener("*"));
        view.findViewById(R.id.divide).setOnClickListener(new operatorsOnClickListener("/"));

        view.findViewById(R.id.comma).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expression.length() == 0 || Arrays.asList(operators).contains(expression.charAt(expression.length() - 1))) {
                    expression += "0.";
                    hasComma = true;
                }

                else if(expression.length() > 0 && !expression.endsWith(".") && !hasComma) {
                    expression += ".";
                    hasComma = true;
                }

                expressionViewer.setText(expression);
            }
        });


        view.findViewById(R.id.backspace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expression.length() > 0) {
                    if(expression.charAt(expression.length() - 1) == '.')
                        hasComma = false;

                    else if(Arrays.asList(operators).contains(expression.charAt(expression.length() - 1))) // ???
                        hasComma = true;

                    expression = expression.substring(0, expression.length() - 1);
                }

                expressionViewer.setText(expression);
            }
        });

        view.findViewById(R.id.backspace).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                expression = "";
                expressionViewer.setText("0");
                resultViewer.setText("0.0");

                return false;
            }
        });

        view.findViewById(R.id.result).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EquationSolver equationSolver = new EquationSolver(expression);
                equationSolver.infixToPostfix();
                equationSolver.evaluatePostfix();

                resultViewer.setText(equationSolver.getResult());
            }
        });
    }

    private class digitsOnClickListener implements View.OnClickListener {
        private String digit;

        private digitsOnClickListener(final String digit) {
            this.digit = digit;
        }

        @Override
        public void onClick(View v) {
            if(expression.length() == 0)
                expression = digit;

            else if((expression.length() < 2 && !expression.endsWith("0")) ||
                    (expression.length() >= 2 && !(expression.endsWith("0") && Arrays.asList(operators).contains(expression.charAt(expression.length() - 2)))))
                expression += digit;

            expressionViewer.setText(expression);
        }
    }

    private class operatorsOnClickListener implements View.OnClickListener {
        private String operator;

        private operatorsOnClickListener(final String operator) {
            this.operator = operator;
        }

        @Override
        public void onClick(View v) {
            if(hasComma)
                hasComma = false;

            if(expression.length() != 0) {
                if(expression.length() > 1 && Arrays.asList(operators).contains(expression.charAt(expression.length() - 1)))
                    expression = expression.substring(0, expression.length() - 1) + operator;

                else if(!expression.endsWith("."))
                    expression += operator;

                expressionViewer.setText(expression);
            }
        }
    }
}