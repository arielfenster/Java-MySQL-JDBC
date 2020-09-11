package validation;

import javax.xml.bind.ValidationException;

public class AddressValidator implements IValidator {

    private Operation operation;

    public AddressValidator(Operation operation) {
        this.operation = operation;
    }

    @Override
    public void validate(String address) throws ValidationException {
        if (operation == Operation.INSERT) {
            validateInsert(address);
        } else {
            validateUpdate(address);
        }
    }

    private void validateInsert(String address) throws ValidationException {
        if (address.split(" ").length < 2) {
            throw new ValidationException("Invalid address. Address should contain at least 2 words");
        }
    }

    private void validateUpdate(String address) throws ValidationException {
        if (address.isEmpty()) {
            return;
        }
        validateInsert(address);
    }
}
