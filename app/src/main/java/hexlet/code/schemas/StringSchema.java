package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;

public class StringSchema extends BaseSchema {
    // Section to populate checkMethods (map located in superclass)
    {
        checkMethods.put("required", this::checkRequired);
        checkMethods.put("minLength", this::checkMinLength);
        checkMethods.put("substring", this::checkSubstring);
    }

    // Section with methods to populate checking requirements (map located in superclass)
    public StringSchema required() {
        requirements.computeIfAbsent("required", value -> new ArrayList<>()).add(true);
        return this;
    }

    public StringSchema minLength(int minLength) {
        requirements.computeIfAbsent("minLength", value -> new ArrayList<>()).add(minLength);
        return this;
    }

    public StringSchema contains(String substring) {
        requirements.computeIfAbsent("substring", value -> new ArrayList<>()).add(substring);
        return this;
    }

    // Section with methods to check each condition from requirements
    protected boolean isValidInput(Object input) {
        return input == null || input instanceof String;
    }

    private boolean checkRequired(Object parameters, Object input) {
        return !(input == null || input.equals(""));
    }

    private boolean checkMinLength(Object listOfLengths, Object input) {
        if (input == null) {
            return true;
        }

        List<?> lengths = (List<?>) listOfLengths;
        for (Object length : lengths) {
            if (input.toString().length() < (int) length) {
                return  false;
            }
        }
        return true;
    }

    private boolean checkSubstring(Object listOfSubstrings, Object input) {
        if (input == null) {
            return true;
        }

        List<?> substrings = (List<?>) listOfSubstrings;
        for (Object substring : substrings) {
            if (!input.toString().contains((String) substring)) {
                return false;
            }
        }
        return true;
    }
}
