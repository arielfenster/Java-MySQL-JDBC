package validation;

import javax.xml.bind.ValidationException;

public interface IValidator {

    enum Operation {
        INSERT,
        UPDATE
    }

    void validate(String data) throws ValidationException;
}
