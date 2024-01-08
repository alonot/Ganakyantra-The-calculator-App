package com.example.ganakyantra_thecalculator;


import android.annotation.SuppressLint;

public class util {

    public static boolean degOn;

    private static String factorial(int n) {
        if (n < 0)
            throw new RuntimeException("Number must be positive");
        else {
            String fact = "1";
            for (int i = 2; i <= n; i++) {
                fact = String.valueOf(Double.parseDouble(fact) * i);
            }
            return fact;
        }
    }

    public static String deci_pos;

    private static Object[] get1st2nd(String input, int operation, String opname) {

        int i, y;
        i = y = operation;   //operation variable will just store the location of operation which we will do on these two numbers
        String firstnumber = "";
        String secondnumber = "";
        y += opname.length() - 1;  //To counter  operations like sin,ln,log,etc.

        if (y + 1 < input.length())
            if (input.charAt(y + 1) == '+' || input.charAt(y + 1) == '-')
                secondnumber += input.charAt(++y);

        boolean signreached = false;
        while (true) {
            char ch, ch2;
            ch = '\0';
            ch2 = '\0';
            if (i != 0 && !signreached) {
                i--;
                ch2 = input.charAt(i);
                if (Character.isDigit(ch2) || ch2 == '.')
                    firstnumber = ch2 + firstnumber;
                else if (ch2 == '+' || ch2 == '-') {
                    firstnumber = ch2 + firstnumber;
                    signreached = true;
                } else
                    i++; //if we reached our first charater other than a digit , we will increase i which will be countered by above i-- hence at the end i will be on starting digit of firstnumber
            }
            if (y < input.length() - 1) {
                y++;
                ch = input.charAt(y);
                if (Character.isDigit(ch) || ch == '.')
                    secondnumber += ch;
                else
                    y--;//similar to i , At the end y will be on last digit of second number.
            }
            if ((i == 0 && y == input.length()) || (!Character.isDigit(ch) && (!Character.isDigit(ch2)) && ch != '.' && ch2 != '.'))
                break;
        }
        return new Object[]{firstnumber, secondnumber, i, y};

    }

    @SuppressLint("DefaultLocale")
    public static String calculate(String input) throws SyntaxError {
        System.out.println(input);
//        Logic is that starting from the next element after bracket, increase no_openBrac by 1 at each open bracket and close them at next
//        ")" .When no open brackets i.e. no_openBrac is 0. We get our expression.
        if (input.contains("(")) {
            int bracLoc = input.indexOf("(");
            int brac2Loc = -1;
            int no_openBrac = 0;
            for (int i = bracLoc + 1; i < input.length(); i++) {
                char ch = input.charAt(i);
                if (ch == '(')
                    no_openBrac++;
                if (ch == ')') {
                    System.out.println(input + "R");
                    if (no_openBrac == 0)
                        return calculate(input.substring(0, bracLoc) + (calculate(input.substring(bracLoc + 1, i))) + input.substring(i + 1));
                    else
                        no_openBrac--;
                }
            }
        }
        if (input.contains("+-") || input.contains("-+")) {
            int Loc = input.indexOf("+-");
            if (Loc == -1)
                Loc = input.indexOf("-+");
            return calculate(input.substring(0, Loc) + "-" + input.substring(Loc + 2));
        }

        if (input.contains("--") || input.contains("++")) {
            int Loc = input.indexOf("++");
            if (Loc == -1)
                Loc = input.indexOf("--");
            return calculate(input.substring(0, Loc) + "+" + input.substring(Loc + 2));
        }

        if (input.contains("!")) {
            int multiplyLoc = input.indexOf('!');
            String firstnumber = "";

            int i, y;
            i = y = multiplyLoc;  //operation variable will just store the location of operation we will do on these two numbers

            //get the required info...
            Object[] result = get1st2nd(input, multiplyLoc, "!");
            firstnumber = String.valueOf(result[0]);
            i = (int) result[2];
            y = (int) result[3];

            if (firstnumber.length() == 0)
                throw new SyntaxError("Syntax Error");

            String ans = "";
            try {
                int n = Integer.parseInt(firstnumber);
                if (n < 0)
                    throw new SyntaxError("Numbers must be positive.");
                ans = String.valueOf(factorial(n));
            } catch (NumberFormatException e) {
                throw new SyntaxError("Number must be whole number.");
            }
            if (!ans.contains("-"))
                ans = "+" + ans;

            return calculate(input.substring(0, i) + ans + input.substring(y + 1));
        }

        if (input.contains("P")) {
            int PLoc = input.indexOf("P");
            String firstnumber = "";
            String secondnumber = "";

            int i, y;
            i = y = PLoc;  //operation variable will just store the location of operation we will do on these two numbers

            //get the required info...
            Object[] result = get1st2nd(input, PLoc, "P");
            firstnumber = String.valueOf(result[0]);
            secondnumber = String.valueOf(result[1]);
            i = (int) result[2];
            y = (int) result[3];

            if (secondnumber.length() != 0) {

                String ans = "";
                try {
                    int n = Integer.parseInt(firstnumber);
                    int r = Integer.parseInt(secondnumber);
                    if (n < 0 || r < 0)
                        throw new SyntaxError("Numbers must be positive.");

                    ans = String.valueOf(Double.parseDouble(factorial(n)) / Double.parseDouble(factorial(n - r)));
                } catch (NumberFormatException e) {
                    throw new SyntaxError("Number must be whole number.");
                }

                if (!ans.contains("-"))
                    ans = "+" + ans;


                return calculate(input.substring(0, i) + ans + input.substring(y + 1));
            }
        }

        if (input.contains("C")) {
            int CLoc = input.indexOf("C");
            String firstnumber = "";
            String secondnumber = "";

            int i, y;
            i = y = CLoc;  //operation variable will just store the location of operation we will do on these two numbers

            //get the required info...
            Object[] result = get1st2nd(input, CLoc, "C");
            firstnumber = String.valueOf(result[0]);
            secondnumber = String.valueOf(result[1]);
            i = (int) result[2];
            y = (int) result[3];

            if (secondnumber.length() != 0) {

                String ans = "";
                try {
                    int n = Integer.parseInt(firstnumber);
                    int r = Integer.parseInt(secondnumber);
                    if (n < 0 || r < 0)
                        throw new SyntaxError("Numbers must be positive.");
                    if (n < r)
                        throw new SyntaxError("Syntax Error");

                    ans = String.valueOf(Double.parseDouble(factorial(n)) / (Double.parseDouble(factorial(n - r)) * Double.parseDouble(factorial(r))));
                } catch (NumberFormatException e) {
                    throw new SyntaxError("Number must be whole number.");
                }

                if (!ans.contains("-"))
                    ans = "+" + ans;


                return calculate(input.substring(0, i) + ans + input.substring(y + 1));
            }
        }

        if (input.contains("ˣ√")) {
            int xpowLoc = input.indexOf("ˣ√");
            String firstnumber = "";
            String secondnumber = "";

            int i, y;
            i = y = xpowLoc;  //operation variable will just store the location of operation we will do on these two numbers

            //get the required info...
            Object[] result = get1st2nd(input, xpowLoc, "ˣ√");
            firstnumber = String.valueOf(result[0]);
            secondnumber = String.valueOf(result[1]);
            i = (int) result[2];
            y = (int) result[3];

            String ans = "";
            try {
                ans = String.valueOf(Math.pow(Double.parseDouble(secondnumber), 1 / Double.parseDouble(firstnumber)));
            } catch (NumberFormatException e1) {
                System.out.println(e1);
                throw new SyntaxError("SyntaxError");
            }


            return calculate(input.substring(0, i) + ans + input.substring(y + 1));
        }

        if (input.contains("alog")) {
            int alogLoc = input.indexOf("alog");
            String secondnumber = "";

            int i, y;
            i = y = alogLoc;  //operation variable will just store the location of operation we will do on these two numbers

            //get the required info...
            Object[] result = get1st2nd(input, alogLoc, "alog");
            secondnumber = String.valueOf(result[1]);
            i = (int) result[2];
            y = (int) result[3];


            String ans = "";
            try {
                ans = String.valueOf(Math.pow(10, Double.parseDouble(secondnumber)));
            } catch (NumberFormatException e) {
                throw new SyntaxError("SyntaxError");
            }
            if (!ans.contains("-"))
                ans = "+" + ans;

            if (!deci_pos.equals("Auto")) {
                System.out.println("//" + String.format("%.2f", Double.parseDouble(ans)));
                ans = String.format("%." + deci_pos + "f", Double.parseDouble(ans));
            }

            return calculate(input.substring(0, i) + ans + input.substring(y + 1));
        }

        if (input.contains("atan")) {
            int atanLoc = input.indexOf("atan");
            String secondnumber = "";

            int i, y;
            i = y = atanLoc;  //operation variable will just store the location of operation we will do on these two numbers

            //get the required info...
            Object[] result = get1st2nd(input, atanLoc, "atan");
            secondnumber = String.valueOf(result[1]);
            i = (int) result[2];
            y = (int) result[3];

            System.out.println(i + "," + y);
            String ans = "";
            try {
                if (degOn)
                    ans = String.valueOf(Math.toDegrees(Math.atan(Double.parseDouble(secondnumber))));
                else
                    ans = String.valueOf(Math.atan(Double.parseDouble(secondnumber)));
                if (ans == "NaN")
                    throw new SyntaxError("Math Error");
            } catch (NumberFormatException e) {
                System.out.println(e);
                throw new SyntaxError("SyntaxError");
            }

            if (!ans.contains("-"))
                ans = "+" + ans;

            if (!deci_pos.equals("Auto")) {
                System.out.println("//" + String.format("%.2f", Double.parseDouble(ans)));
                ans = String.format("%." + deci_pos + "f", Double.parseDouble(ans));
            }
            return calculate(input.substring(0, i) + ans + input.substring(y + 1));
        }

        if (input.contains("acos")) {
            int acosLoc = input.indexOf("acos");
            String secondnumber = "";

            int i, y;
            i = y = acosLoc;  //operation variable will just store the location of operation we will do on these two numbers

            //get the required info...
            Object[] result = get1st2nd(input, acosLoc, "acos");
            secondnumber = String.valueOf(result[1]);
            i = (int) result[2];
            y = (int) result[3];


            String ans = "";
            try {
                if (degOn)
                    ans = String.valueOf(Math.toDegrees(Math.acos(Double.parseDouble(secondnumber))));
                else
                    ans = String.valueOf(Math.acos(Double.parseDouble(secondnumber)));
                if (ans == "NaN")
                    throw new SyntaxError("Math Error");
            } catch (NumberFormatException e) {
                System.out.println(e);
                throw new SyntaxError("SyntaxError");
            }
            if (!ans.contains("-"))
                ans = "+" + ans.substring(0);

            if (!deci_pos.equals("Auto")) {
                System.out.println("//" + String.format("%.2f", Double.parseDouble(ans)));
                ans = String.format("%." + deci_pos + "f", Double.parseDouble(ans));
            }

            return calculate(input.substring(0, i) + ans + input.substring(y + 1));
        }

        if (input.contains("asin")) {
            int asinLoc = input.indexOf("asin");
            String secondnumber = "";

            int i, y;
            i = y = asinLoc;  //operation variable will just store the location of operation we will do on these two numbers

            //get the required info...
            Object[] result = get1st2nd(input, asinLoc, "asin");
            secondnumber = String.valueOf(result[1]);
            i = (int) result[2];
            y = (int) result[3];


            String ans = "";
            try {
                if (degOn)
                    ans = String.valueOf(Math.toDegrees(Math.asin(Double.parseDouble(secondnumber))));
                else
                    ans = String.valueOf(Math.asin(Double.parseDouble(secondnumber)));
                if (ans == "NaN")
                    throw new SyntaxError("Math Error");
            } catch (NumberFormatException e) {
                System.out.println(e);
                throw new SyntaxError("SyntaxError");
            }
            if (!ans.contains("-"))
                ans = "+" + ans.substring(0);

            if (!deci_pos.equals("Auto")) {
                System.out.println("//" + String.format("%.2f", Double.parseDouble(ans)));
                ans = String.format("%." + deci_pos + "f", Double.parseDouble(ans));
            }

            return calculate(input.substring(0, i) + ans + input.substring(y + 1));
        }

        if (input.contains("ln")) {
            int lnLoc = input.indexOf("ln");
            String secondnumber = "";

            int i, y;
            i = y = lnLoc;  //operation variable will just store the location of operation we will do on these two numbers

            //get the required info...
            Object[] result = get1st2nd(input, lnLoc, "ln");
            secondnumber = String.valueOf(result[1]);
            i = (int) result[2];
            y = (int) result[3];


            String ans = "";
            try {
                ans = String.valueOf(Math.log(Double.parseDouble(secondnumber)));
                if (ans == "NaN")
                    throw new SyntaxError("Math Error");
            } catch (NumberFormatException e) {
                System.out.println(e);
                throw new SyntaxError("SyntaxError");
            }
            if (!ans.contains("-"))
                ans = "+" + ans.substring(0);

            if (!deci_pos.equals("Auto")) {
                System.out.println("//" + String.format("%.2f", Double.parseDouble(ans)));
                ans = String.format("%." + deci_pos + "f", Double.parseDouble(ans));
            }

            return calculate(input.substring(0, i) + ans + input.substring(y + 1));
        }

        if (input.contains("log")) {
            int logLoc = input.indexOf("log");
            String secondnumber = "";

            int i, y;
            i = y = logLoc;  //operation variable will just store the location of operation we will do on these two numbers

            //get the required info...
            Object[] result = get1st2nd(input, logLoc, "log");
            secondnumber = String.valueOf(result[1]);
            i = (int) result[2];
            y = (int) result[3];


            String ans = "";
            try {
                ans = String.valueOf(Math.log10(Double.parseDouble(secondnumber)));
                if (ans == "NaN")
                    throw new SyntaxError("Math Error");
            } catch (NumberFormatException e) {
                System.out.println(e);
                throw new SyntaxError("SyntaxError");
            }

            if (!ans.contains("-"))
                ans = "+" + ans.substring(0);

            if (!deci_pos.equals("Auto")) {
                System.out.println("//" + String.format("%.2f", Double.parseDouble(ans)));
                ans = String.format("%." + deci_pos + "f", Double.parseDouble(ans));
            }

            return calculate(input.substring(0, i) + ans + input.substring(y + 1));
        }

        if (input.contains("tan")) {
            int tanLoc = input.indexOf("tan");
            String secondnumber = "";

            int i, y;
            i = y = tanLoc;  //operation variable will just store the location of operation we will do on these two numbers

            //get the required info...
            Object[] result = get1st2nd(input, tanLoc, "tan");
            secondnumber = String.valueOf(result[1]);
            i = (int) result[2];
            y = (int) result[3];


            String ans = "";
            try {
                if (degOn)
                    ans = String.valueOf(Math.tan(Math.toRadians(Double.parseDouble(secondnumber))));
                else
                    ans = String.valueOf(Math.tan(Double.parseDouble(secondnumber)));
                if (ans == "NaN")
                    throw new SyntaxError("Math Error");
            } catch (NumberFormatException e) {
                System.out.println(e);
                throw new SyntaxError("SyntaxError tan");
            }


            if (!ans.contains("-"))
                ans = "+" + ans.substring(0);

            if (!deci_pos.equals("Auto")) {
                System.out.println("//" + String.format("%.2f", Double.parseDouble(ans)));
                ans = String.format("%." + deci_pos + "f", Double.parseDouble(ans));
            }

            return calculate(input.substring(0, i) + ans + input.substring(y + 1));
        }

        if (input.contains("cos")) {
            int cosLoc = input.indexOf("cos");
            String secondnumber = "";

            int i, y;
            i = y = cosLoc;  //operation variable will just store the location of operation we will do on these two numbers

            //get the required info...
            Object[] result = get1st2nd(input, cosLoc, "cos");
            secondnumber = String.valueOf(result[1]);
            i = (int) result[2];
            y = (int) result[3];


            String ans = "";
            try {
                if (degOn)
                    ans = String.valueOf(Math.cos(Math.toRadians(Double.parseDouble(secondnumber))));
                else
                    ans = String.valueOf(Math.cos(Double.parseDouble(secondnumber)));
                if (ans == "NaN")
                    throw new SyntaxError("Math Error");
            } catch (NumberFormatException e) {
                System.out.println(e);
                throw new SyntaxError("SyntaxError");
            }


            if (!ans.contains("-"))
                ans = "+" + ans.substring(0);

            if (!deci_pos.equals("Auto")) {
                System.out.println("//" + String.format("%.2f", Double.parseDouble(ans)));
                ans = String.format("%." + deci_pos + "f", Double.parseDouble(ans));
            }

            return calculate(input.substring(0, i) + ans + input.substring(y + 1));
        }

        if (input.contains("sin")) {
            int sinLoc = input.indexOf("sin");
            String secondnumber = "";

            int i, y;
            i = y = sinLoc;  //operation variable will just store the location of operation we will do on these two numbers

            //get the required info...
            Object[] result = get1st2nd(input, sinLoc, "sin");
            secondnumber = String.valueOf(result[1]);
            i = (int) result[2];
            y = (int) result[3];

            System.out.println("sin:" + secondnumber);


            String ans = "";
            try {
                if (degOn)
                    ans = String.valueOf(Math.sin(Math.toRadians(Double.parseDouble(secondnumber))));
                else
                    ans = String.valueOf(Math.sin(Double.parseDouble(secondnumber)));
                if (ans == "NaN")
                    throw new SyntaxError("Math Error");
            } catch (NumberFormatException e) {
                throw new SyntaxError("SyntaxError");
            }


            if (!ans.contains("-"))
                ans = "+" + ans.substring(0);

            if (!deci_pos.equals("Auto")) {
                System.out.println("//" + String.format("%.2f", Double.parseDouble(ans)));
                ans = String.format("%." + deci_pos + "f", Double.parseDouble(ans));
            }

            return calculate(input.substring(0, i) + ans + input.substring(y + 1));
        }

        if (input.contains("√")) {
            int rootLoc = input.indexOf('√');
            String secondnumber = "";

            int i, y;
            i = y = rootLoc;  //operation variable will just store the location of operation we will do on these two numbers

            //get the required info...
            Object[] result = get1st2nd(input, rootLoc, "√");
            secondnumber = String.valueOf(result[1]);
            i = (int) result[2];
            y = (int) result[3];


            String ans = "";
            try {
                ans = String.valueOf(Math.sqrt(Integer.parseInt(secondnumber)));
                if (ans == "NaN")
                    throw new SyntaxError("Math Error");
            } catch (Exception e) {
                try {
                    ans = String.valueOf(Math.sqrt(Double.parseDouble(secondnumber)));
                } catch (Exception e1) {
                    throw new SyntaxError("Syntax Error");
                }
                if (ans == "NaN")
                    throw new SyntaxError("Math Error");
            }

            if (!ans.contains("-"))
                ans = "+" + ans.substring(0);

            if (!deci_pos.equals("Auto")) {
                System.out.println("//" + String.format("%.2f", Double.parseDouble(ans)));
                ans = String.format("%." + deci_pos + "f", Double.parseDouble(ans));
            }

            return calculate(input.substring(0, i) + ans + input.substring(y + 1));
        }

        if (input.contains("^")) {
            int powLoc = input.indexOf('^');
            String firstnumber = "";
            String secondnumber = "";

            int i, y;
            i = y = powLoc;  //operation variable will just store the location of operation we will do on these two numbers

            //get the required info...
            Object[] result = get1st2nd(input, powLoc, "^");
            firstnumber = String.valueOf(result[0]);
            secondnumber = String.valueOf(result[1]);
            i = (int) result[2];
            y = (int) result[3];


            String ans = "";
            try {
                ans = String.valueOf((int) Math.pow(Integer.parseInt(firstnumber), Integer.parseInt(secondnumber)));
            } catch (Exception e) {
                try {
                    ans = String.valueOf(Math.pow(Double.parseDouble(firstnumber), Double.parseDouble(secondnumber)));
                } catch (Exception e1) {
                    throw new SyntaxError("Syntax Error");
                }

            }
            if (ans == "NaN")
                throw new SyntaxError("Syntax Error");

            if (!ans.contains("-"))
                ans = "+" + ans.substring(0);

            if (!deci_pos.equals("Auto")) {
                System.out.println("//" + String.format("%.2f", Double.parseDouble(ans)));
                ans = String.format("%." + deci_pos + "f", Double.parseDouble(ans));
            }

            return calculate(input.substring(0, i) + ans + input.substring(y + 1));
        }

        if (input.contains("/")) {
            int divideLoc = input.indexOf('/');
            String firstnumber = "";
            String secondnumber = "";

            int i, y;
            i = y = divideLoc;  //operation variable will just store the location of operation we will do on these two numbers

            //get the required info...
            Object[] result = get1st2nd(input, divideLoc, "/");
            firstnumber = String.valueOf(result[0]);
            secondnumber = String.valueOf(result[1]);
            i = (int) result[2];
            y = (int) result[3];


            String ans = "";
            if (Double.parseDouble(secondnumber) == 0.0)
                throw new SyntaxError("Division by Zero");
            try {
                ans = String.valueOf(Double.parseDouble(firstnumber) / Double.parseDouble(secondnumber));
            } catch (Exception e1) {
                throw new SyntaxError("Syntax Error");
            }
            if (ans == "NaN")
                throw new SyntaxError("Syntax Error");


            if (!ans.contains("-"))
                ans = "+" + ans;

            if (!deci_pos.equals("Auto")) {
                System.out.println("//" + String.format("%.2f", Double.parseDouble(ans)));
                ans = String.format("%." + deci_pos + "f", Double.parseDouble(ans));
            }

            return calculate(input.substring(0, i) + ans + input.substring(y + 1));
        }

        if (input.contains("x")) {
            int multiplyLoc = input.indexOf('x');
            String firstnumber = "";
            String secondnumber = "";

            int i, y;
            i = y = multiplyLoc;  //operation variable will just store the location of operation we will do on these two numbers

            //get the required info...
            Object[] result = get1st2nd(input, multiplyLoc, "x");
            firstnumber = String.valueOf(result[0]);
            secondnumber = String.valueOf(result[1]);
            i = (int) result[2];
            y = (int) result[3];
            System.out.println(firstnumber + "," + secondnumber);


            String ans = "";
            try {
                ans = String.valueOf(Integer.parseInt(firstnumber) * Integer.parseInt(secondnumber));
            } catch (Exception e) {
                try {
                    ans = String.valueOf(Double.parseDouble(firstnumber) * Double.parseDouble(secondnumber));
                } catch (Exception e1) {
                    throw new SyntaxError("Syntax Error");
                }
            }
            if (ans == "NaN")
                throw new SyntaxError("Syntax Error");

            if (!ans.contains("-"))
                ans = "+" + ans;


            if (!deci_pos.equals("Auto")) {
                System.out.println("//" + String.format("%.2f", Double.parseDouble(ans)));
                ans = String.format("%." + deci_pos + "f", Double.parseDouble(ans));
            }
            return calculate(input.substring(0, i) + ans + input.substring(y + 1));
        }

        if (input.contains("+")) {
            int currentplusLoc = 0;
            String firstnumber = "";
            String secondnumber = "";
            Object[] result;
            boolean gotoperator = false;

            int i = 0, y = 0;

            while (input.substring(currentplusLoc + 1).contains("+")) {
                int plusLoc = input.indexOf('+', currentplusLoc);
                currentplusLoc = plusLoc;

                i = y = plusLoc;

                //get the required info...
                result = get1st2nd(input, plusLoc, "+");
                firstnumber = String.valueOf(result[0]);

                if (firstnumber.length() == 0) {
                    currentplusLoc++;
                    System.out.println(input + ":" + plusLoc);
                } else {
                    secondnumber = String.valueOf(result[1]);
                    i = (int) result[2];
                    y = (int) result[3];
                    gotoperator = true;
                    break;
                }
            }

            if (gotoperator) {
                System.out.println(firstnumber + "," + secondnumber);

                String ans = "";
                try {
                    ans = String.valueOf(Integer.parseInt(firstnumber) + Integer.parseInt(secondnumber));
                } catch (Exception e) {
                    try {
                        ans = String.valueOf(Double.parseDouble(firstnumber) + Double.parseDouble(secondnumber));
                    } catch (Exception e1) {
                        throw new SyntaxError("Syntax Error");
                    }
                }
                if (!ans.contains("-"))
                    ans = "+" + ans.substring(0);

                if (!deci_pos.equals("Auto")) {
                    System.out.println("//" + String.format("%.2f", Double.parseDouble(ans)));
                    ans = String.format("%." + deci_pos + "f", Double.parseDouble(ans));
                }

                return calculate(input.substring(0, i) + ans + input.substring(y + 1));
            }
        }

        //            Takes out the first and second number and add them as integer if possible otherwise as double.
        if (input.contains("-")) {

            int currentminusLoc = 0;
            String firstnumber = "";
            String secondnumber = "";
            Object[] result;
            boolean gotoperator = false;

            int i = 0, y = 0;

            while (input.substring(currentminusLoc + 1).contains("-")) {
                int minusLoc = input.indexOf('-', currentminusLoc);
                currentminusLoc = minusLoc;

                i = y = minusLoc;

                //get the required info...
                result = get1st2nd(input, minusLoc, "-");
                firstnumber = String.valueOf(result[0]);

                System.out.println(minusLoc);

                if (firstnumber.length() == 0) {
                    currentminusLoc++;
                } else {
                    secondnumber = String.valueOf(result[1]);
                    i = (int) result[2];
                    y = (int) result[3];
                    gotoperator = true;
                    break;
                }
            }

            if (gotoperator) {
                String ans = "";
                try {
                    ans = String.valueOf(Integer.parseInt(firstnumber) - Integer.parseInt(secondnumber));
                } catch (Exception e) {
                    try {
                        ans = String.valueOf(Double.parseDouble(firstnumber) - Double.parseDouble(secondnumber));
                    } catch (Exception e1) {
                        throw new SyntaxError("Syntax Error");
                    }

                }

                if (!ans.contains("-"))
                    ans = "+" + ans;

                if (!deci_pos.equals("Auto")) {
                    System.out.println("//" + String.format("%.2f", Double.parseDouble(ans)));
                    ans = String.format("%." + deci_pos + "f", Double.parseDouble(ans));
                }

                return calculate(input.substring(0, i) + ans + input.substring(y + 1));
            }
        }

        return input;
    }


    public static void main(String[] args) {
        try {
            System.out.println("Ans: " + calculate("+8-6"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}