package pl.lotto.resultchecker.dto;

import org.mapstruct.Mapper;
import pl.lotto.resultchecker.CheckedTicket;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CheckedTicketMapper {

    CheckedTicketDto mapCheckedTicketDocumentToCheckedTicketDto(CheckedTicket checkedTicket);

    List<CheckedTicketDto> mapCheckedTicketListOfDocumentToCheckedTicketDtoList(List<CheckedTicket> checkedTickets);

}
