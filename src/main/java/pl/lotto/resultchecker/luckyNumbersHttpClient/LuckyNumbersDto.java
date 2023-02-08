package pl.lotto.resultchecker.luckyNumbersHttpClient;

import java.time.LocalDateTime;
import java.util.Collection;

public record LuckyNumbersDto(
        Collection<Integer> winningNumbers,
        LocalDateTime localDateTime) {
}