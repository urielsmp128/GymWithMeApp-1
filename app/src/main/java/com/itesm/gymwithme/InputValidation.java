package com.itesm.gymwithme;


import com.google.android.material.textfield.TextInputLayout;

public class InputValidation {


    public static boolean isValidAge(TextInputLayout til, String age) {

        if (age.isEmpty()) {
            til.setError("Enter an age");
            return false;
        }

        int ageInt = Integer.parseInt(age);

        if (ageInt < 0) {
            til.setError("Enter a valid age");
            return false;
        }

        if (ageInt < 12) {
            til.setError("error");

            return false;
        }

        til.setError(null);
        return true;
    }

    public static boolean isValidGender(TextInputLayout til, String inputGender) {

        if (inputGender.isEmpty()) {
            til.setError("Please choose a gender");
            return false;
        }

        if (!inputGender.matches("Male|Female")) {
            til.setError("Please choose a gender");
            return false;
        }

        til.setError(null);
        return true;
    }

    public static boolean isValid(TextInputLayout til, String input, String inputName) {
        return isValid(til, input, inputName, 100, true);
    }

    public static boolean isValid(TextInputLayout til, String input, String inputName, int maxLength,
                                  boolean canContainSpaces) {

        String errorEmpty = inputName + " field cannot be empty";
        String errorLength = inputName + " limit exceeded";
        String errorSpaces = inputName + " cannot contain spaces";

        if (input.isEmpty()) {
            til.setError(errorEmpty);
            return false;
        } else if (input.length() > maxLength) {
            til.setError(errorLength);
            return false;
        } else if (!canContainSpaces && input.contains(" ")) {
            til.setError(errorSpaces);
            return false;
        } else {
            til.setError(null);
            return true;
        }
    }



}
