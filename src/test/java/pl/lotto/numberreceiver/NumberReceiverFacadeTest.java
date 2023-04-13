package pl.lotto.numberreceiver;

import java.time.*;
import java.util.List;

import org.junit.jupiter.api.Test;
import pl.lotto.numberreceiver.dto.NumberReceiverResultDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class NumberReceiverFacadeTest {

    NumberReceiverRepository repository = new InMemoryNumberReceiverRepositoryTestImpl();

    @Test
    public void should_return_success_when_user_gave_six_numbers_in_range() {
        // given
        LocalDateTime thursday = LocalDateTime.of(2022, 11, 17, 17, 5, 0);
        Instant clockInstant = ZonedDateTime.of(thursday, ZoneId.systemDefault()).toInstant();
        Clock clock = Clock.fixed(clockInstant, ZoneId.systemDefault());
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().numberReceiverFacade(repository, clock);
        LocalDateTime friday = LocalDateTime.of(2022, 11, 19, 12, 0, 0);
        DrawDateSelector drawDateSelector = mock(DrawDateSelector.class);
        given(drawDateSelector.calculateNearestDrawDate()).willReturn(friday.plusDays(1));
        NumberInputValidator numberInputValidator = new NumberInputValidator();
        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade(numberInputValidator, drawDateSelector, repository);
        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);
        // when
        NumberReceiverResultDto result = numberReceiverFacade.inputNumbers(numbersFromUser);
        // then
        assertThat(result.message()).isEqualTo("You entered the correct numbers");
    }
}
//
//    @Test
//    public void should_return_failure_when_user_gave_less_than_six_numbers() {
//        // given
////        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createForTests(repository, clock);
//        NumberInputValidator numberInputValidator = new NumberInputValidator();
//        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade(numberInputValidator, drawDateSelector, repository);
//        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5);
//        // when
//        NumberReceiverResultDto result = numberReceiverFacade.validateEnteredNumbers(numbersFromUser);
//        // then
//        assertThat(result.message()).isEqualTo("failure");
//    }
//
//    @Test
//    public void should_return_success_with_lottery_unique_id_when_user_gave_correct_input() {
//        // given
////        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createForTests(repository, clock);
//        NumberInputValidator numberInputValidator = new NumberInputValidator();
//        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade(numberInputValidator, drawDateSelector, repository);
//        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);
//        // when
//        NumberReceiverResultDto result = numberReceiverFacade.validateEnteredNumbers(numbersFromUser);
//        // then
//        assertThat(result.lotteryId().toString()).matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
//    }
//
//    @Test
//    public void should_return_nulls_when_user_not_passed_validation() {
//        // given
////        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createForTests(repository, clock);
//        NumberInputValidator numberInputValidator = new NumberInputValidator();
//        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade(numberInputValidator, drawDateSelector, repository);
//        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6, 7);
//        // when
//        NumberReceiverResultDto result = numberReceiverFacade.validateEnteredNumbers(numbersFromUser);
//        // then
//        assertThat(result.lotteryId()).isNull();
//        assertThat(result.drawDate()).isNull();
//    }
//
//    @Test
//    public void should_return_success_with_correct_draw_date_id_when_user_played_on_saturday_11AM() {
//        // given
//        LocalDateTime friday = LocalDateTime.of(2022, 11, 19, 11, 0, 0);
//        Instant clockInstant = ZonedDateTime.of(friday, ZoneId.systemDefault()).toInstant();
//        Clock clock = Clock.fixed(clockInstant, ZoneId.systemDefault());
////        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createForTests(repository, clock);
//        NumberInputValidator numberInputValidator = new NumberInputValidator();
//        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade(numberInputValidator, drawDateSelector, repository);
//        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);
//        // when
//        NumberReceiverResultDto result = numberReceiverFacade.validateEnteredNumbers(numbersFromUser);
//        // then
//        assertThat(result.drawDate()).isEqualTo(LocalDateTime.of(2022, 11, 19, 12, 0));
//    }
//
//    @Test
//    public void should_return_success_with_correct_draw_date_id_when_user_played_on_saturday_1PM() {
//        // given
//        LocalDateTime friday = LocalDateTime.of(2022, 11, 19, 13, 0, 0);
//        Instant clockInstant = ZonedDateTime.of(friday, ZoneId.systemDefault()).toInstant();
//        Clock clock = Clock.fixed(clockInstant, ZoneId.systemDefault());
////        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createForTests(repository, clock);
//        NumberInputValidator numberInputValidator = new NumberInputValidator();
//        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade(numberInputValidator, drawDateSelector, repository);
//        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);
//        // when
//        NumberReceiverResultDto result = numberReceiverFacade.validateEnteredNumbers(numbersFromUser);
//        // then
//        assertThat(result.drawDate()).isEqualTo(LocalDateTime.of(2022, 11, 26, 12, 0));
//    }
//
//    @Test
//    public void should_return_success_with_correct_draw_date_id_when_user_played_on_saturday_12() {
//        // given
//        LocalDateTime friday = LocalDateTime.of(2022, 11, 19, 12, 0, 0);
//        Instant clockInstant = ZonedDateTime.of(friday, ZoneId.systemDefault()).toInstant();
////        Clock clock = Clock.fixed(clockInstant, ZoneId.systemDefault());
//        DrawDateSelector drawDateSelector  = mock(DrawDateSelector.class);
//        given(drawDateSelector.calculateNearestDrawDate()).willReturn(friday.plusDays(1));
//
////        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createForTests(repository, clock);
//        NumberInputValidator numberInputValidator = new NumberInputValidator();
//        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade(numberInputValidator, drawDateSelector, repository);
//        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);
//        // when
//        NumberReceiverResultDto result = numberReceiverFacade.validateEnteredNumbers(numbersFromUser);
//        // then
//        assertThat(result.drawDate()).isEqualTo(LocalDateTime.of(2022, 11, 26, 12, 0));
//    }
//
//    @Test
//    public void should_return_failure_when_user_gave_more_than_six_numbers() {
//        // given
////        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createForTests(repository, clock);
//        NumberInputValidator numberInputValidator = new NumberInputValidator();
//        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade(numberInputValidator, drawDateSelector, repository);
//        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6, 7);
//        // when
//        NumberReceiverResultDto result = numberReceiverFacade.validateEnteredNumbers(numbersFromUser);
//        // then
//        assertThat(result.message()).isEqualTo("failure");
//    }
//
//    @Test
//    public void should_return_failure_when_user_gave_duplicated_numbers() {
//        // given
////        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createForTests(repository, clock);
//        NumberInputValidator numberInputValidator = new NumberInputValidator();
//        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade(numberInputValidator, drawDateSelector, repository);
//        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6, 7);
//        // when
//        NumberReceiverResultDto result = numberReceiverFacade.validateEnteredNumbers(numbersFromUser);
//        // then
//        assertThat(result.message()).isEqualTo("failure");
//    }
//
//    @Test
//    public void should_return_failure_when_user_gave_numbers_out_of_range() {
//        // given
////        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createForTests(repository, clock);
//        NumberInputValidator numberInputValidator = new NumberInputValidator();
//        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade(numberInputValidator, drawDateSelector, repository);
//        List<Integer> numbersFromUser = List.of(0, -2, 3, 4, 5, 100);
//        // when
//        NumberReceiverResultDto result = numberReceiverFacade.validateEnteredNumbers(numbersFromUser);
//        // then
//        assertThat(result.message()).isEqualTo("failure");
//    }
//
//    @Test
//    public void should_return_one_lottery_ticket_with_saturday_draw_date_when_only_one_user_played_on_thursday() {
//        // given
//        LocalDateTime thursday = LocalDateTime.of(2022, 11, 17, 17, 5, 0);
//        Instant clockInstant = ZonedDateTime.of(thursday, ZoneId.systemDefault()).toInstant();
//        Clock clock = Clock.fixed(clockInstant, ZoneId.systemDefault());
////        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createForTests(repository, clock);
//        NumberInputValidator numberInputValidator = new NumberInputValidator();
//        NumberReceiverFacade numberReceiverFacade = new NumberReceiverFacade(numberInputValidator, drawDateSelector, repository);
//        List<Integer> numbersFromUser = List.of(1, 2, 3, 4, 5, 6);
//        NumberReceiverResultDto numberReceiverResultDto = numberReceiverFacade.validateEnteredNumbers(numbersFromUser);
//        LocalDateTime drawDate = LocalDateTime.of(2022, 11, 19, 12, 0, 0);
//        // when
//        AllNumbersFromUsersDto lotteryTickets = numberReceiverFacade.userNumbersForNextDrawDate();
//        // then
//        LocalDateTime expectedDrawDate = LocalDateTime.of(2022, 11, 19, 12, 0, 0);
//        UUID expectedId = numberReceiverResultDto.lotteryId();
//        List<Integer> expectedNumbers = List.of(1, 2, 3, 4, 5, 6);
//        LotteryTicketDto expectedLotteryTicket = new LotteryTicketDto(expectedId, expectedDrawDate, expectedNumbers);
//        assertThat(lotteryTickets.tickets()).contains(expectedLotteryTicket);
//    }
//
//    Clock clock = Clock.systemUTC();
//}
