package pl.lotto.numberreceiver;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.lotto.numberreceiver.ValidationError.*;

@AllArgsConstructor
class NumberInputValidator {

    @Value("${numberInputValidator.maxNumbersFromUser:6}")
    private int maxNumbersFromUser;
    @Value("${numberInputValidator.minInputNumber:1}")
    private int minInputNumber;
    @Value("${numberInputValidator.maxInputNumber:99}")
    private int maxInputNumber;

    public NumberInputValidator() {
    }

    public ValidationResult validate(List<Integer> numbersFromUser) {
        List<ValidationError> errors = new LinkedList<>();
        if (doesUserGaveDuplicatedNumbers(numbersFromUser)) {
            errors.add(DUPLICATED_NUMBERS);
        }
        if (doesUserGaveLessThanSixNumbers(numbersFromUser)) {
            errors.add(LESS_THAN_SIX_NUMBER);
        }
        if (doesUserGaveMoreThanSixNumbers(numbersFromUser)) {
            errors.add(MORE_THAN_SIX_NUMBER);
        }
        if (!errors.isEmpty()) {
            String message = concatenateValidationMessage(errors);
            return new ValidationResult(message);
        }
        return new ValidationResult();
    }

    private String concatenateValidationMessage(List<ValidationError> errors) {
        return errors.stream()
                .map(error -> error.message)
                .collect(Collectors.joining(", "));
    }

    private boolean doesUserGaveLessThanSixNumbers(List<Integer> numbersFromUser) {
        return numbersFromUser.size() < maxNumbersFromUser;
    }

    private boolean doesUserGaveMoreThanSixNumbers(List<Integer> numbersFromUser) {
        return numbersFromUser.size() > maxNumbersFromUser;
    }

    private boolean doesUserGaveDuplicatedNumbers(List<Integer> numbersFromUser) {
        Set<Integer> numberFromUserWithoutDuplicates = new HashSet<>(numbersFromUser);
        return numbersFromUser.size() != numberFromUserWithoutDuplicates.size();
    }
}
