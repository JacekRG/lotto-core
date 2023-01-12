package pl.lotto.resultannouncer;

public enum AnnouncerMessages {

    MAIN_PRIZE("You won the main prize!"),
    SECOND_PRIZE("You have won the second prize!"),
    THIRD_PRIZE("You have won the third prize!"),
    FOURTH_PRIZE("You have won the fourth prize!"),
    NO_PRIZE("Keep playing"),
    NOT_FOUND("Result not found");

    String message;

    AnnouncerMessages(String message) {
        this.message = message;
    }
}
