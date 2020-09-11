package validation;

import javax.xml.bind.ValidationException;

public class NameValidator implements IValidator {

    private Operation operation;

    public NameValidator(Operation operation) {
        this.operation = operation;
    }

    @Override
    public void validate(String name) throws ValidationException {
        if (operation == Operation.INSERT) {
            validateInsert(name);
        } else {
            validateUpdate(name);
        }
    }

    private void validateInsert(String name) throws ValidationException {
        if (name.length() < 3) {
            throw new ValidationException("Invalid name. Name should contain at least 3 characters");
        }
    }

    private void validateUpdate(String name) throws ValidationException {
        if (name.isEmpty()) {
            return;
        }
        validateInsert(name);
    }
}
