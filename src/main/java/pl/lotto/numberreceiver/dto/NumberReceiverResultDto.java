package pl.lotto.numberreceiver.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record NumberReceiverResultDto(
        String message,
        UUID lotteryId,
        LocalDateTime drawDate
) {

    public static NumberReceiverResultDto failure(String message) {
        return new NumberReceiverResultDto(message, null, null);
    }

    public static NumberReceiverResultDto success(String message, UUID lotteryID, LocalDateTime drawDate) {
        return new NumberReceiverResultDto(message, lotteryID, drawDate);
    }

    public boolean isFailure() {
        return message.equals("failure");
    }
}
