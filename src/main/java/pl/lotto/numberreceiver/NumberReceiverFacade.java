package pl.lotto.numberreceiver;

import pl.lotto.numberreceiver.dto.AllNumbersFromUsersDto;
import pl.lotto.numberreceiver.dto.DrawDateDto;
import pl.lotto.numberreceiver.dto.LotteryTicketDto;
import pl.lotto.numberreceiver.dto.NumberReceiverResultDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static pl.lotto.numberreceiver.dto.NumberReceiverResultDto.failure;
import static pl.lotto.numberreceiver.dto.NumberReceiverResultDto.success;

public class NumberReceiverFacade {

    private final NumberInputValidator validator;
    private final DrawDateSelector drawDateSelector;
    private final NumberReceiverRepository repository;

    NumberReceiverFacade(NumberInputValidator validator, DrawDateSelector drawDateSelector,
                         NumberReceiverRepository repository) {
        this.validator = validator;
        this.drawDateSelector = drawDateSelector;
        this.repository = repository;
    }

    public NumberReceiverResultDto inputNumbers(List<Integer> numbersFromUser) {
        ValidationResult validate = validator.validate(numbersFromUser);
        if (validate.isNotValid()) {
            return failure(validate.message());
        }
        UUID lotteryId = generateLotteryUniqueId();
        LocalDateTime drawDate = drawDateSelector.calculateNearestDrawDate();
        LotteryTicket lotteryTicket = new LotteryTicket(lotteryId, drawDate, numbersFromUser);
        repository.save(lotteryTicket);
        return success(validate.message(), lotteryId, drawDate);
    }

    public UUID generateLotteryUniqueId() {
        return LotteryIdGenerator.generateLotteryId();
    }

    public AllNumbersFromUsersDto userNumbersForNextDrawDate() {
        LocalDateTime localDateTime = drawDateSelector.calculateNearestDrawDate();
        List<LotteryTicket> allByDate = repository.findAllByDrawDate(localDateTime);
        List<LotteryTicketDto> lotteryTicketDtos = allByDate.stream()
                .map(lotteryTicket -> new LotteryTicketDto(
                        lotteryTicket.getLotteryId(),
                        lotteryTicket.getDrawDate(),
                        lotteryTicket.getNumbersFromUser()))
                .toList();
        return new AllNumbersFromUsersDto(lotteryTicketDtos);
    }

    public DrawDateDto specifyDrawDate() {
        LocalDateTime drawDate = drawDateSelector.calculateNearestDrawDate();
        return new DrawDateDto(drawDate);
    }
}
