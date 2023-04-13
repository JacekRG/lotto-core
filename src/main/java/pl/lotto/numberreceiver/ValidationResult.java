package pl.lotto.numberreceiver;

record ValidationResult(String message) {
    ValidationResult() {
        this("You entered the correct numbers");
    }

    public boolean isNotValid() {
        return this.message.equals("failure");
    }
}
