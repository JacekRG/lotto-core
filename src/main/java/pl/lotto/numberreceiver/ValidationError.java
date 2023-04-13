package pl.lotto.numberreceiver;

enum ValidationError {


    DUPLICATED_NUMBERS("User gave duplicated numbers"),
    LESS_THAN_SIX_NUMBER("User gave less than six numbers"),

    MORE_THAN_SIX_NUMBER("User gave more than six numbers"),

    NUMBERS_IN_RANGE("User gave numbers in range");


   final String message;

    ValidationError(String message) {
        this.message = message;
    }
}
