package pl.lotto.resultchecker.dto;

public enum TicketStateDto {
    NOT_FOUND("This ticket was not found in our database. Check if you entered the correct ID"),
    CHECKED("The ticket has been checked successfully"),
    TOO_EARLY("It's too early to check your score. Please try again after the draw");

    final String description;

    TicketStateDto(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isNotFound() {
        return this.equals(NOT_FOUND);
    }

    public boolean isChecked() {
        return this.equals(CHECKED);
    }

    public boolean isTooEarly() {
        return this.equals(TOO_EARLY);
    }
}
