package validation;

import javax.xml.bind.ValidationException;

public interface IValidator {

//    void validateName(String name) throws ValidationException;
//
//    void validateAddress(String address) throws ValidationException;
//
//    void validateDate(String date) throws ValidationException;

    void validate(String data) throws ValidationException;
}
