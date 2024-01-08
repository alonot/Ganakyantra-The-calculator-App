package com.example.ganakyantra_thecalculator;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatToggleButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private AppCompatButton btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_minus, btn_plus, btn_int;
    private AppCompatButton btn_root, btn_AC, btn_dot, btn_pie, btn_multiply, btn_div, btn_log, btn_sin, btn_cos, btn_tan;
    private AppCompatButton btn_e, btn_nCr, btn_bracUp, btn_bracDown, btn_del, btn_fraction, btn_pow, btn_menu;
    private AppCompatToggleButton btn_2nd, btn_rad_deg;
    private TextView box_ans, box_input;
    private Spinner spinner_decimal;

    private HashMap<String, String> mapper;

    private String input;
    public boolean is1st;

    private String intmode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            initialize();

            btn_fraction.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        if (result.length() > 0) {

                            int numerator, denominator;
                            int whole;
                            if (result.contains(".")) {
                                int topow = result.length() - result.indexOf('.') - 1;
                                double hund;
                                int dotpos = result.indexOf('.');
                                if (topow > 9) {
                                    hund = Math.pow(10, 9);
                                    result = result.substring(0, dotpos + 10);
                                } else
                                    hund = Math.pow(10, topow);
                                numerator = (int) (Double.parseDouble(result.substring(0, dotpos) + result.substring(dotpos + 1)));
                                denominator = (int) hund;
                                int gcd = GCD(numerator, denominator);

                                System.out.println(hund + ",," + numerator + "<<," + gcd);
                                numerator /= gcd;
                                denominator /= gcd;
                                whole = numerator / denominator;
                                numerator %= denominator;
                                if (denominator == 1) {
                                    System.out.println(denominator);
                                    box_ans.setText(result);
                                } else if (whole > 0) {
                                    box_ans.setText(whole + getsuperS(numerator + "") + "/" + getsubS(denominator + ""));
                                } else {
                                    box_ans.setText(getsuperS(numerator + "") + "/" + getsubS(denominator + ""));
                                }

                            } else {
                                box_ans.setText(result);
                            }
                            box_input.setText(result);
                            tocompute = result;
                            input = result;

                        }
                    } catch (Exception e) {
                        System.out.println(e);
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btn_int.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (intmode == "floor")
                            result = String.valueOf((int) Math.floor(Double.parseDouble(result)));
                        else if (intmode == "ceil")
                            result = String.valueOf((int) Math.ceil(Double.parseDouble(result)));
                        else
                            result = String.valueOf(Math.round(Double.parseDouble(result)));
                        box_ans.setText(result);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btn_rad_deg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        util.degOn = true;
                        btn_rad_deg.setBackgroundColor(Color.parseColor("#7A6F7C"));
                    } else {
                        util.degOn = false;
                        btn_rad_deg.setBackgroundColor(Color.parseColor("#BBB0BD"));
                    }
                }
            });

            btn_0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOnScreen("0");
                }
            });

            btn_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOnScreen("1");
                }
            });

            btn_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOnScreen("2");
                }
            });

            btn_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOnScreen("3");
                }
            });

            btn_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOnScreen("4");
                }
            });

            btn_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOnScreen("5");
                }
            });

            btn_6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOnScreen("6");
                }
            });

            btn_7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOnScreen("7");
                }
            });

            btn_8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOnScreen("8");
                }
            });

            btn_9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOnScreen("9");
                }
            });

            btn_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOnScreen("-1");
                }
            });

            //For popup menu__> for About
            btn_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
                    popupMenu.setOnMenuItemClickListener(MainActivity.this);
                    popupMenu.inflate(R.menu.menu);
                    popupMenu.show();
                    view = v;

                }
            });

            btn_AC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOnScreen("-2");
                }
            });

            btn_bracUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOnScreen("(");
                }
            });

            btn_bracDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOnScreen(")");
                }
            });

            btn_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOnScreen("+");
                }
            });

            btn_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOnScreen("-");
                }
            });

            btn_multiply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOnScreen("x");
                }
            });

            btn_div.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOnScreen("÷");
                }
            });

            btn_pie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOnScreen("π");
                }
            });

            btn_dot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOnScreen(".");
                }
            });

            btn_sin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (is1st) {
                        showOnScreen("sin⁻¹(");
                    } else {
                        showOnScreen("sin(");
                    }
                }
            });

            btn_cos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (is1st) {
                        showOnScreen("cos⁻¹(");
                    } else {
                        showOnScreen("cos(");
                    }
                }
            });

            btn_tan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (is1st) {
                        showOnScreen("tan⁻¹(");
                    } else {
                        showOnScreen("tan(");
                    }
                }
            });

            btn_log.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (is1st) {
                        showOnScreen("10ˣ(");
                    } else {
                        showOnScreen("log(");
                    }
                }
            });

            btn_nCr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (is1st) {
                        showOnScreen("P");
                    } else {
                        showOnScreen("C");
                    }
                }
            });

            btn_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (is1st) {
                        showOnScreen("!");
                    } else {
                        showOnScreen("²√");
                    }
                }
            });

            btn_e.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (is1st) {
                        showOnScreen("ln");
                    } else {
                        showOnScreen("e");
                    }
                }
            });

            btn_pow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (is1st) {
                        showOnScreen("ˣ√");
                    } else {
                        showOnScreen("^");
                    }
                }
            });

            btn_2nd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        is1st = true;

                        btn_2nd.setBackgroundColor(Color.parseColor("#A67CAE"));

                        btn_cos.setBackgroundColor(Color.parseColor("#DCBBDF"));
                        btn_tan.setBackgroundColor(Color.parseColor("#DCBBDF"));
                        btn_root.setBackgroundColor(Color.parseColor("#DCBBDF"));
                        btn_nCr.setBackgroundColor(Color.parseColor("#DCBBDF"));
                        btn_e.setBackgroundColor(Color.parseColor("#DCBBDF"));
                        btn_log.setBackgroundColor(Color.parseColor("#DCBBDF"));
                        btn_pow.setBackgroundColor(Color.parseColor("#DCBBDF"));
                        btn_sin.setBackgroundColor(Color.parseColor("#DCBBDF"));

                        btn_sin.setText("sin⁻¹");
                        btn_cos.setText("cos⁻¹");
                        btn_tan.setText("tan⁻¹");
                        btn_log.setText("10ˣ");
                        btn_nCr.setText("nPr");
                        btn_e.setText("ln");
                        btn_pow.setText("ˣ√");
                        btn_root.setText("!");
                    } else {
                        is1st = false;
                        btn_2nd.setBackgroundColor(Color.parseColor("#BBB0BD"));

                        btn_cos.setBackgroundColor(Color.parseColor("#BBB0BD"));
                        btn_tan.setBackgroundColor(Color.parseColor("#BBB0BD"));
                        btn_root.setBackgroundColor(Color.parseColor("#BBB0BD"));
                        btn_nCr.setBackgroundColor(Color.parseColor("#BBB0BD"));
                        btn_e.setBackgroundColor(Color.parseColor("#BBB0BD"));
                        btn_log.setBackgroundColor(Color.parseColor("#BBB0BD"));
                        btn_pow.setBackgroundColor(Color.parseColor("#BBB0BD"));
                        btn_sin.setBackgroundColor(Color.parseColor("#BBB0BD"));

                        btn_sin.setText("sin");
                        btn_cos.setText("cos");
                        btn_tan.setText("tan");
                        btn_log.setText("log");
                        btn_nCr.setText("nCr");
                        btn_e.setText("e");
                        btn_pow.setText("^");
                        btn_root.setText("²√");
                    }
                }
            });

            setSpinner();

        } catch (Exception e) {
            result = "";
            input = "";
            tocompute = "";
            System.out.println(e);
        }
    }

    private View view;

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.about1) {
            Toast.makeText(MainActivity.this, "Jai Shri Ram!!", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.floor) {
            Toast.makeText(this, "IntMode set to floor", Toast.LENGTH_SHORT).show();
            intmode = "floor";
        } else if (item.getItemId() == R.id.ceil) {
            Toast.makeText(this, "IntMode set to ceil", Toast.LENGTH_SHORT).show();
            intmode = "ceil";
        } else if (item.getItemId() == R.id.round) {
            Toast.makeText(this, "IntMode set to round", Toast.LENGTH_SHORT).show();
            intmode = "round";
        }
        return false;
    }


    private void setSpinner() {
        ArrayList<String> pos = new ArrayList<>();
        Collections.addAll(pos, "Auto", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, pos);
        spinner_decimal.setAdapter(adapter);

        spinner_decimal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, pos.get(position), Toast.LENGTH_SHORT).show();
                util.deci_pos = pos.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private int GCD(int a, int b) {
        int gcd = a;
        while (b % a != 0) {
            int temporary = a;
            a = b % a;
            b = temporary;

            gcd = a;
        }

        return gcd;
    }

    private final HashMap<String, String> supOf = new HashMap<String, String>() {{
        put("0", "⁰");
        put("1", "¹");
        put("2", "²");
        put("3", "³");
        put("4", "⁴");
        put("5", "⁵");
        put("6", "⁶");
        put("7", "⁷");
        put("8", "⁸");
        put("9", "⁹");


    }};

    private String getsuperS(String s) {
        String newString = "";
        for (int i = 0; i < s.length(); i++) {
            newString += supOf.get(s.charAt(i) + "");
        }
        System.out.println(newString);
        return newString;
    }

    private final HashMap<String, String> subOf = new HashMap<String, String>() {{
        put("0", "₀");
        put("1", "₁");
        put("2", "₂");
        put("3", "₃");
        put("4", "₄");
        put("5", "₅");
        put("6", "₆");
        put("7", "₇");
        put("8", "₈");
        put("9", "₉");


    }};

    private String getsubS(String s) {
        String newString = "";
        for (int i = 0; i < s.length(); i++) {
            newString += subOf.get(s.charAt(i) + "");
        }
        System.out.println(newString);
        return newString;
    }

    private void managex() {
        if (input.length() > 0) {
            if (input.charAt(input.length() - 1) != 'x' && tocompute.charAt(tocompute.length() - 1) == 'x')
                tocompute = tocompute.substring(0, tocompute.length() - 1);
        }
    }

    private void showOnScreen(String next) {
        if (next == "-1") {
            if (input.length() != 0) {

                if (input.charAt(input.length() - 1) == '√') {
                    if (input.charAt(input.length() - 2) == '²')
                        tocompute = tocompute.substring(0, tocompute.length() - 1);
                    else
                        tocompute = tocompute.substring(0, tocompute.length() - 2);
                    input = input.substring(0, input.length() - 2);
                    managex();
                } else if (input.length() > 2) {
                    if (input.charAt(input.length() - 2) == '¹') {
                        input = input.substring(0, input.length() - 6);
                        tocompute = tocompute.substring(0, tocompute.length() - 5);
                        managex();
                    } else if (input.charAt(input.length() - 2) == 'ˣ') {
                        input = input.substring(0, input.length() - 4);
                        tocompute = tocompute.substring(0, tocompute.length() - 4);
                        managex();
                    } else if (input.charAt(input.length() - 3) == 'l') {
                        input = input.substring(0, input.length() - 3);
                        tocompute = tocompute.substring(0, tocompute.length() - 3);
                        managex();
                    } else if (Character.isAlphabetic(input.charAt(input.length() - 2)) && !"xPCe".contains("" + input.charAt(input.length() - 2))) {
                        input = input.substring(0, input.length() - 4);
                        tocompute = tocompute.substring(0, tocompute.length() - 4);
                        managex();
                    } else {
                        input = input.substring(0, input.length() - 1);
                        tocompute = tocompute.substring(0, tocompute.length() - 1);
                        managex();
                    }
                    System.out.println(tocompute + "," + input);

                } else {
                    input = input.substring(0, input.length() - 1);
                    tocompute = tocompute.substring(0, tocompute.length() - 1);
                }
            }

            compute(tocompute);
        } else if (next == "-2") {
            tocompute = "";

            input = "";
        } else {
            input += next;
            calculate(next);
        }

        box_input.setText(input);

    }

    private static String tocompute = "";

    private void calculate(String next) {
        System.out.println(":::" + input + "<" + tocompute);
        if (mapper.containsKey(next)) {
            if (tocompute.length() > 0) {
                if (tocompute.charAt(tocompute.length() - 1) == ')' && (!"/ˣ√".contains(mapper.get(next))))
                    tocompute += "x" + mapper.get(next);
                else if (Character.isDigit(tocompute.charAt(tocompute.length() - 1)) && (!"/ˣ√".contains(mapper.get(next))))
                    tocompute += "x" + mapper.get(next);
                else
                    tocompute += mapper.get(next);
            } else
                tocompute += mapper.get(next);
        } else if ((next == "-" || next == "+")) {
            if (tocompute.length() > 0) {
                if (!Character.isDigit(tocompute.charAt(tocompute.length() - 1))) {
                    tocompute += "(" + next;
                } else
                    tocompute += next;
            } else {
                tocompute += "(" + next;
            }
        } else if (tocompute.length() > 0) {
            if (tocompute.charAt(tocompute.length() - 1) == ')' && (!"PCx/!)".contains(next.charAt(0) + "")))
                tocompute += "x" + next;
            else if (Character.isDigit(tocompute.charAt(tocompute.length() - 1)) && (!Character.isDigit(next.charAt(0)) && (!incompact.contains(next.charAt(0) + ""))))
                tocompute += "x" + next;
            else
                tocompute += next;
        } else
            tocompute += next;

        compute(tocompute);

    }

    private String result;

    private void compute(String tocompute) {

        try {
            result = util.calculate(completeBrac(tocompute));
            System.out.println("No error: " + result);
        } catch (Exception e) {
            System.out.println(e);
            result = e.getMessage();
        }
        box_ans.setText(result);
    }

    private String incompact;

    private String completeBrac(String input) {
        int no_openBrac = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(') {
                no_openBrac++;
            } else if (input.charAt(i) == ')') {
                no_openBrac--;
            }
        }
        for (int i = 0; i < no_openBrac; i++) {
            input += ")";
        }
        System.out.println(":)" + input);
        return input;
    }

    private void initialize() {

        input = "";
        btn_0 = findViewById(R.id.btn_0);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);

        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        btn_multiply = findViewById(R.id.btn_x);
        btn_div = findViewById(R.id.btn_div);

        btn_int = findViewById(R.id.btn_int);
        btn_AC = findViewById(R.id.btn_AC);
        btn_dot = findViewById(R.id.btn_dot);
        btn_pie = findViewById(R.id.btn_pie);

        btn_bracDown = findViewById(R.id.btn_bracDown);
        btn_bracUp = findViewById(R.id.btn_bracUp);
        btn_sin = findViewById(R.id.btn_sin);
        btn_cos = findViewById(R.id.btn_cos);
        btn_tan = findViewById(R.id.btn_tan);
        btn_log = findViewById(R.id.btn_log);

        btn_nCr = findViewById(R.id.btn_nCr);
        btn_root = findViewById(R.id.btn_root);
        btn_e = findViewById(R.id.btn_e);
        btn_pow = findViewById(R.id.btn_pow);
        btn_2nd = findViewById(R.id.btn_2nd);
        btn_del = findViewById(R.id.btn_del);

        box_ans = findViewById(R.id.box_ans);
        box_input = findViewById(R.id.Input);
        spinner_decimal = findViewById(R.id.btn_decimals);
        btn_fraction = findViewById(R.id.btn_ans);
        btn_rad_deg = findViewById(R.id.btn_rad_deg);
        btn_menu = findViewById(R.id.menu);

        util.degOn = false;
        is1st = false;
        incompact = "PC+-x/)!^.";
        mapper = new HashMap<String, String>() {{
            put("e", "" + Math.E);
            put("π", "" + Math.PI);
            put("÷", "/");
            put("²√", "√");
            put("ˣ√", "ˣ√");
            put("10ˣ(", "alog(");
            put("tan⁻¹(", "atan(");
            put("cos⁻¹(", "acos(");
            put("sin⁻¹(", "asin(");
            put("sin", "sin(");
            put("cos", "cos(");
            put("tan", "tan(");
            put("log", "log(");
            put("ln", "ln(");
        }};

        util.deci_pos = "Auto";
    }
}