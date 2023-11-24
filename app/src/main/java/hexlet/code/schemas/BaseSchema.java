package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema {
    protected Map<String, Predicate<Object>> checks = new LinkedHashMap<>();
    protected boolean requiredFlag = false;

    public final boolean isValid(Object input) {
        if (!checks.get("required").test(input) && !requiredFlag) {
            return true;
        }

        return checks.values().stream().allMatch(check -> check.test(input));
    }
}
