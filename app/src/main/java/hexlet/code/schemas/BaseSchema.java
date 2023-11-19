package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class BaseSchema {
    protected List<Predicate<Object>> checks;
    protected boolean requiredFlag;

    protected BaseSchema() {
        this.checks = new ArrayList<>();
        this.requiredFlag = false;
    }

    protected abstract boolean isValidType(Object input);

    public final boolean isValid(Object input) {
        if (input == null || (input instanceof String && ((String) input).isEmpty())) {
            return !requiredFlag;
        }

        if (!isValidType(input)) {
            return false;
        }

        for (Predicate<Object> check : checks) {
            boolean isCurrentCheckPass = check.test(input);
            if (!isCurrentCheckPass) {
                return false;
            }
        }

        return true;
    }
}
