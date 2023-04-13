package pl.lotto.resultannouncer;

import org.springframework.stereotype.Component;
import pl.lotto.resultchecker.ResultCheckerFacade;

import java.util.Set;
import java.util.UUID;

@Component
public class ResultAnnouncerFacade {

    private final ResultCheckerFacade resultCheckerFacade;
    private final ResultAnnouncerSummarizer resultAnnouncerSummarizer;
    private final ResultAnnouncerRepository resultAnnouncerRepository;

    public ResultAnnouncerFacade(ResultCheckerFacade resultCheckerFacade, ResultAnnouncerSummarizer resultAnnouncerSummarizer,
                                 ResultAnnouncerRepository resultAnnouncerRepository) {
        this.resultCheckerFacade = resultCheckerFacade;
        this.resultAnnouncerSummarizer = resultAnnouncerSummarizer;
        this.resultAnnouncerRepository = resultAnnouncerRepository;
    }

    public AnnouncedTicket announceResult(UUID id) {
        UniqueTicketResultDto uniqueTicketResultDto = resultCheckerFacade.checkIfTicketIsChecked(id);
        if (uniqueTicketResultDto.ticketStateDto().isNotFound()) {
            return new AnnouncedTicket(id, AnnouncerMessages.NOT_FOUND);
        }
        if (uniqueTicketResultDto.ticketStateDto().isTooEarly()) {
            return new AnnouncedTicket(id, AnnouncerMessages.TOO_EARLY);
        }
        Set<Integer> numbersOfHits = uniqueTicketResultDto.checkedTicketDto().numbersOfHits();
        AnnouncedTicket result = resultAnnouncerSummarizer.summarizeUniqueTicket(id, numbersOfHits);
        return resultAnnouncerRepository.save(result);
    }
}
