package pl.lotto.resultchecker;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.dto.AllNumbersFromUsersDto;
import pl.lotto.numberreceiver.dto.LotteryTicketDto;
import pl.lotto.resultchecker.dto.CheckedTicketDto;
import pl.lotto.resultchecker.dto.LuckyNumbersDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ResultCheckerFacadeTest {

    @Test
    public void should_return_3_as_number_of_one_user_hits()  {
        // given
        NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
        given(numberReceiverFacade.userNumbersForNextDrawDate()).willReturn(examplaryAllNumbersFromUsersDto());

        LuckyNumbersGeneratorClient luckyNumbersGeneratorFacade = mock(LuckyNumbersGeneratorClient.class);
        given(luckyNumbersGeneratorFacade.retrieveLuckyNumbersForDate(examplaryDate)).willReturn(new LuckyNumbersDto(List.of(1, 13, 21), examplaryDate));

        ResultCheckerRepository repository = mock(ResultCheckerRepository.class);
        TicketChecker ticketChecker = new TicketChecker();
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerFacade(numberReceiverFacade, luckyNumbersGeneratorFacade, ticketChecker, repository);


        // when
        List<CheckedTicketDto> checkedTickets = resultCheckerFacade.generateResults();
        // then
        assertThat(checkedTickets.get(0).numbersOfHits().size()).isEqualTo(3);
        Assertions.assertEquals(new HashSet<>(checkedTickets.get(0).numbersOfHits()), Set.of(21, 13, 1));
    }

    @Test
    public void should_throw_exception_when_draw_date_is_not_specified_for_ticket() {
        // given
        NumberReceiverFacade numberReceiverFacade = mock(NumberReceiverFacade.class);
        LuckyNumbersGeneratorClient luckyNumbersGeneratorFacade = mock(LuckyNumbersGeneratorClient.class);
        ResultCheckerRepository repository = mock(ResultCheckerRepository.class);
        TicketChecker ticketChecker = new TicketChecker();
        ResultCheckerFacade resultCheckerFacade = new ResultCheckerFacade(numberReceiverFacade, luckyNumbersGeneratorFacade, ticketChecker, repository);
        given(numberReceiverFacade.userNumbersForNextDrawDate()).willReturn(examplaryAllNumbersFromUsersDtoWithEmptyDate());
        given(luckyNumbersGeneratorFacade.retrieveLuckyNumbersForDate(examplaryDate)).willReturn(generateExamplaryLuckyNumbers(examplaryDate));
        luckyNumbersGeneratorFacade.retrieveLuckyNumbersForDate(examplaryDate);
        // when
        Throwable throwable = catchThrowable(resultCheckerFacade::generateResults);
        // then
        assertThat(throwable).isInstanceOf(DrawDateNotSpecifiedForTicketException.class);
    }


    LocalDateTime examplaryDate = LocalDateTime.of(2022, 11, 11, 11, 11);
    LocalDateTime emptyDate = null;
    UUID examplaryId = UUID.fromString("123e4567-e89b-42d3-a456-556642440000");

    LuckyNumbersDto generateExamplaryLuckyNumbers(LocalDateTime localDateTime) {
        return new LuckyNumbersDto(Set.of(1, 13, 21, 44, 55, 66), localDateTime);
    }

    AllNumbersFromUsersDto examplaryAllNumbersFromUsersDto() {
        return new AllNumbersFromUsersDto(List.of(new LotteryTicketDto(examplaryId, examplaryDate, List.of(1, 13, 21, 4, 5, 6))));
    }

    AllNumbersFromUsersDto examplaryAllNumbersFromUsersDtoWithEmptyDate() {
        return new AllNumbersFromUsersDto(List.of(new LotteryTicketDto(examplaryId, emptyDate, List.of(1, 13, 21, 4, 5, 6))));
    }

}
