package pl.lotto.resultchecker;

import pl.lotto.numberreceiver.dto.LotteryTicketDto;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TicketChecker {
    List<CheckedTicket> checkAllTickets(Collection<Integer> luckyNumbers, List<LotteryTicketDto> ticketsFromUsers) {
        return ticketsFromUsers
                .stream()
                .map(lotteryTicketDto -> fromDto(luckyNumbers, lotteryTicketDto))
                .collect(Collectors.toList());
    }

    private CheckedTicket fromDto(Collection<Integer> luckyNumbers, LotteryTicketDto ticket) {
        Set<Integer> numbersOfHits1 = calculateNumbersOfHit(ticket.numbersFromUser(), luckyNumbers);
        return new CheckedTicket(ticket.lotteryId(), ticket.drawDate(), ticket.numbersFromUser(), numbersOfHits1);
    }

    private Set<Integer> calculateNumbersOfHit(List<Integer> numbersFromUser, Collection<Integer> luckyNumbers) {
        return numbersFromUser.stream()
                .distinct()
                .filter(luckyNumbers::contains)
                .collect(Collectors.toSet());
    }
}
