//  1. Imports – Jaruri Libraries

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

//  2. Class Declaration
public class Calculator {

    //  3. Variables for Frame and Design
    int boardWidth = 360;
    int boardHeight = 540;

    Color customLightGray = new Color(212, 212, 210);
    Color customDarkGray = new Color(80, 80, 80);
    Color customBlack = new Color(28, 28, 28);
    Color customOrange = new Color(255, 149, 0);

    //  4. Variables for Buttons and Display
    String[] buttonValues = {
        "AC", "+/-", "%", "÷",
        "7", "8", "9", "×",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "="
    };
    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%"};

    //  5. Components for Frame
    JFrame frame = new JFrame(" AK Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();
    private DefaultListModel<String> historyModel = new DefaultListModel<>();
    private JList<String> historyList = new JList<>(historyModel);

    //  6. Logic ke Variables (A, B, operator)
    String A = "0";
    String operator = null;
    String B = null;

    //  7. Constructor for Frame and Design
    Calculator() {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // 7. History Panel at the bottom
        // 7. History Panel at the bottom
        historyList.setFont(new Font("Monospaced", Font.PLAIN, 16));
        historyList.setBackground(customBlack); // background black
        historyList.setForeground(Color.WHITE); // text white
        JScrollPane scrollPane = new JScrollPane(historyList);
        scrollPane.getViewport().setBackground(customBlack); // full area black
        scrollPane.setPreferredSize(new Dimension(boardWidth, 100));
        frame.add(scrollPane, BorderLayout.SOUTH);

        // 8. Display Panel and Label
        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        // 9. Buttons Panel and Buttons
        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);

        // 10. Adding Buttons to the Panel
        for (String buttonValue : buttonValues) {
            JButton button = new JButton(buttonValue);
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));

            if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(customLightGray);
                button.setForeground(customBlack);
            } else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customOrange);
                button.setForeground(Color.white);
            } else {
                button.setBackground(customDarkGray);
                button.setForeground(Color.white);
            }

            buttonsPanel.add(button);

            // 11. Action Listener for Buttons
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String btnVal = button.getText();

                    if (Arrays.asList(rightSymbols).contains(btnVal)) {
                        if (btnVal.equals("=")) {
                            if (A != null && operator != null) {
                                B = displayLabel.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);
                                double result = 0;

                                if (operator.equals("+")) {
                                    result = numA + numB; 
                                }else if (operator.equals("-")) {
                                    result = numA - numB; 
                                }else if (operator.equals("×")) {
                                    result = numA * numB; 
                                }else if (operator.equals("÷")) {
                                    result = numB != 0 ? numA / numB : 0;
                                }

                                // ✅ History line add
                                String expression = A + " " + operator + " " + B + " = " + removeZeroDecimal(result);
                                historyModel.addElement(expression);

                                displayLabel.setText(removeZeroDecimal(result));
                                clearAll();
                            }
                        } else {
                            if (operator == null) {
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            }
                            operator = btnVal;
                        }

                    } else if (Arrays.asList(topSymbols).contains(btnVal)) {
                        if (btnVal.equals("AC")) {
                            clearAll();
                            displayLabel.setText("0");
                        } else if (btnVal.equals("+/-")) {
                            double val = Double.parseDouble(displayLabel.getText());
                            val *= -1;
                            displayLabel.setText(removeZeroDecimal(val));
                        } else if (btnVal.equals("%")) {
                            double val = Double.parseDouble(displayLabel.getText());
                            val /= 100;
                            displayLabel.setText(removeZeroDecimal(val));
                        }
                    } else { // Digits, dot, sqrt
                        if (btnVal.equals(".")) {
                            if (!displayLabel.getText().contains(".")) {
                                displayLabel.setText(displayLabel.getText() + ".");
                            }
                        } else if ("0123456789".contains(btnVal)) {
                            if (displayLabel.getText().equals("0")) {
                                displayLabel.setText(btnVal);
                            } else {
                                displayLabel.setText(displayLabel.getText() + btnVal);
                            }
                        } else if (btnVal.equals("√")) {
                            double val = Double.parseDouble(displayLabel.getText());
                            if (val >= 0) {
                                displayLabel.setText(removeZeroDecimal(Math.sqrt(val)));
                            }
                        }
                    }
                }
            });
        }

        frame.setVisible(true);
    }

    void clearAll() {
        A = "0";
        operator = null;
        B = null;
    }

    String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0) {
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }

    // 12. Main Method
    public static void main(String[] args) {
        new Calculator();
    }

    public Color getCustomOrange() {
        return customOrange;
    }
}
