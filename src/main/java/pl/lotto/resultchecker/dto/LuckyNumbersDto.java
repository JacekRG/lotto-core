package pl.lotto.resultchecker.dto;

import java.time.LocalDateTime;
import java.util.Collection;

public record LuckyNumbersDto(
        Collection<Integer> winningNumbers,
        LocalDateTime localDateTime) {
}
