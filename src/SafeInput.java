import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class SafeInput {
    /**
     * @param pipe   a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return a String response that is not zero length
     */
    public static String getNonZeroLenString(Scanner pipe, String prompt) {
        String retString = ""; // Set this to zero length. Loop runs until it isn't
        do {
            System.out.print("\n" + prompt + ": "); // show prompt add space
            retString = pipe.nextLine();
        } while (retString.length() == 0);

        return retString;

    }

    public static int getInt(Scanner pipe, String prompt) {
        String trash = "";
        int retInt = 0;
        boolean valid = false;

        while (!valid) {
            System.out.println(prompt);
            if (pipe.hasNextInt()) {
                retInt = pipe.nextInt();
                valid = true;

            } else {
                trash = pipe.next();
                System.out.println("The input " + trash + " is not an int, please try again");
                pipe.nextLine();

            }
        }

        return retInt;
    }

    public static double getDouble(Scanner pipe, String prompt) {
        String trash = "";
        double retDouble = 0;
        boolean valid = false;

        while (!valid) {
            System.out.println(prompt);
            if (pipe.hasNextDouble()) {
                retDouble = pipe.nextDouble();
                valid = true;

            } else {
                trash = pipe.next();
                System.out.println("The input " + trash + " is not an double, please try again");
                pipe.nextLine();


            }
        }

        return retDouble;
    }

    public static int getRangedInt(Scanner pipe, String prompt, int low, int high) {
        String trash = "";
        int retInt = 0;
        boolean valid = false;
        System.out.println(prompt);
        while (!valid) {


            if (pipe.hasNextInt()) {
                retInt = pipe.nextInt();
                if (retInt >= low && retInt <= high) {
                    valid = true;
                } else {
                    System.out.println("number out of range");
                    pipe.nextLine();
                }


            } else {
                trash = pipe.next();
                System.out.println("The input " + trash + " is not an int, please try again");
                pipe.nextLine();


            }
        }

        return retInt;
    }

    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high) {
        String trash = "";
        double retDouble = 0;
        boolean valid = false;
        System.out.println(prompt);
        while (!valid) {


            if (pipe.hasNextDouble()) {
                retDouble = pipe.nextDouble();
                if (retDouble >= low && retDouble <= high) {
                    valid = true;
                } else {
                    System.out.println("double out of range");
                    pipe.nextLine();
                }


            } else {
                trash = pipe.next();
                System.out.println("The input " + trash + " is not an double, please try again");
                pipe.nextLine();


            }
        }

        return retDouble;
    }

    public static boolean getYNConfirm(Scanner pipe, String prompt) {
        String trash = "";
        String userInput;
        boolean retBool = false;
        boolean valid = false;
        System.out.println(prompt);
        while (!valid) {


            if (pipe.hasNext()) {
                userInput = pipe.next();
                if (userInput.equalsIgnoreCase("y")) {
                    retBool = true;
                    valid = true;

                } else if (userInput.equalsIgnoreCase("n")) {
                    retBool = false;
                    valid = true;
                } else {
                    System.out.println("invalid input");
                    pipe.nextLine();
                }


            } else {
                trash = pipe.next();
                System.out.println("The input " + trash + " is not a valid string, please try again");
                pipe.nextLine();


            }
        }

        return retBool;

    }

    public static String getRegExString(Scanner pipe, String prompt, String regEx) {
        String userInput = " ";

        String retRegExString = "";
        boolean valid = false;
        Pattern pattern = Pattern.compile(regEx);


        System.out.println(prompt);
        while (!valid) {
            if (pipe.hasNext()) {
                userInput = pipe.next();
                Matcher matcher = pattern.matcher(userInput);
                if (matcher.matches()) {
                    valid = true;
                } else {
                    System.out.println("Your input does not match your regex pattern, try again: ");
                    pipe.nextLine();
                }

            } else {
                System.out.println("Please enter a valid input");
                pipe.nextLine();
            }
        }


        return userInput;
    }


    public static void prettyHeader(String msg) {


        int msgStart = 60 / 2 - msg.length() / 2;


        int i = 0;
        for (i = 1; i <= 60; i++) {
            System.out.print("*");  // This works, but is not optimal for larger loops
        }
        System.out.print("\n");
        i = 0;
        for (i = 1; i <= 60; i++) {
            if (i <= 3) {
                System.out.print("*");


            } else if (i == msgStart) {
                System.out.print(msg);
                i += msg.length() - 1;


            } else if (i >= 58 && i <= 60) {
                System.out.print("*");
            } else {
                System.out.print(" ");
            }
        }


        System.out.print("\n");
        for (i = 1; i <= 60; i++) {
            System.out.print("*");  // This works, but is not optimal for larger loops
        }

    }
}