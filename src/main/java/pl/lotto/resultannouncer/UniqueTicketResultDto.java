package pl.lotto.resultannouncer;

import pl.lotto.resultchecker.dto.CheckedTicketDto;
import pl.lotto.resultchecker.dto.TicketStateDto;

public record UniqueTicketResultDto(
        CheckedTicketDto checkedTicketDto,
        TicketStateDto ticketStateDto) {
}
