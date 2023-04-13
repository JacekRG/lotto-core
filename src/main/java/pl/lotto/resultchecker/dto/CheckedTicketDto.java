package pl.lotto.resultchecker.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record CheckedTicketDto(

        UUID lotteryId,
        LocalDateTime drawDate,
        List<Integer> numbersFromUser,
        Set<Integer> numbersOfHits) {
}
