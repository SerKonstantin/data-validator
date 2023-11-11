package hexlet.code.schemas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

public abstract class BaseSchema {
    // Requirements and checkMethods are populated from subclass
    protected Map<String, List<Object>> requirements = new HashMap<>();
    protected Map<String, BiPredicate<Object, Object>> checkMethods = new HashMap<>();

    protected abstract boolean isValidInput(Object input);

    public boolean isValid(Object input) {
        if (!isValidInput(input)) {
            return false;
        }

        for (Map.Entry<String, List<Object>> entry : requirements.entrySet()) {
            String typeOfCheck = entry.getKey();
            List<Object> parameters = entry.getValue();
            boolean isCurrentCheckPass = checkMethods.get(typeOfCheck).test(parameters, input);
            if (!isCurrentCheckPass) {
                return false;
            }
        }

        return true;
    }
}
