package pl.lotto.resultchecker;

import pl.lotto.resultchecker.dto.LuckyNumbersDto;

import java.time.LocalDateTime;

public interface LuckyNumbersGeneratorClient {
    LuckyNumbersDto retrieveLuckyNumbersForDate(LocalDateTime date);
}
