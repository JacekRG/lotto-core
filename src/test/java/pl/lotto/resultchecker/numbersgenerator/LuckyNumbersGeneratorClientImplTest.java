package pl.lotto.resultchecker.numbersgenerator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.lotto.resultchecker.LuckyNumbersGeneratorClient;
import pl.lotto.resultchecker.dto.LuckyNumbersDto;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LuckyNumbersGeneratorClientImplTest {

    @Autowired
    private LuckyNumbersGeneratorClient luckyNumbersGeneratorClient;

    @Test
    public void testRetrieveLuckyNumbersForDate() {
        String exemplaryStringDate = "2022-12-24T12:00:00";
        LocalDateTime exemplaryDate = LocalDateTime.parse(exemplaryStringDate);

        LuckyNumbersDto luckyNumbers = luckyNumbersGeneratorClient.retrieveLuckyNumbersForDate(exemplaryDate);

//        assertEquals(exemplaryDate.format(DateTimeFormatter.ISO_DATE_TIME), luckyNumbers.localDateTime());
        assertEquals(exemplaryDate, luckyNumbers.localDateTime());
        assertEquals(luckyNumbers.winningNumbers().size(), 6);


    }
}
