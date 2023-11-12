package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapSchema extends BaseSchema {
    // Section to populate checkMethods (map located in superclass)
    {
        checkMethods.put("required", this::checkRequired);
        checkMethods.put("sizeOf", this::checkSizeOf);
    }

    // Section with methods to populate checking requirements (map located in superclass)
    public MapSchema required() {
        requirements.computeIfAbsent("required", value -> new ArrayList<>()).add(true);
        return this;
    }

    public MapSchema sizeOf(int size) {
        requirements.computeIfAbsent("sizeOf", value -> new ArrayList<>()).add(size);
        return this;
    }

    // Section with methods to check each condition from requirements
    protected boolean isValidInput(Object input) {
        return input == null || input instanceof Map<?, ?>;
    }

    private boolean checkRequired(Object parameters, Object input) {
        return !(input == null);
    }

    private boolean checkSizeOf(Object listOfSizes, Object input) {
        if (input == null) {
            return true;
        }
        if (!(input instanceof Map<?, ?>) || !(listOfSizes instanceof List<?> sizes)) {
            return false;
        }

        int inputSize = ((Map<?, ?>) input).size();
        for (Object size : sizes) {
            if (!(size instanceof Integer)) {
                return false;
            }
            if (inputSize != (int) size) {
                return false;
            }
        }

        return true;
    }
}
