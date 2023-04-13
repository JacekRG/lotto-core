package pl.lotto.resultchecker;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.dto.AllNumbersFromUsersDto;
import pl.lotto.numberreceiver.dto.DrawDateDto;
import pl.lotto.numberreceiver.dto.LotteryTicketDto;
import pl.lotto.resultannouncer.UniqueTicketResultDto;
import pl.lotto.resultchecker.dto.CheckedTicketDto;
import pl.lotto.resultchecker.dto.CheckedTicketMapper;
import pl.lotto.resultchecker.dto.TicketStateDto;
import pl.lotto.resultchecker.dto.LuckyNumbersDto;


@Component
public class ResultCheckerFacade {
    private final NumberReceiverFacade receiverFacade;
    private final LuckyNumbersGeneratorClient generatorClient;
    private final TicketChecker ticketChecker;
    private final ResultCheckerRepository repository;
    private CheckedTicketMapper checkedTicketMapper;

    @Autowired
    public ResultCheckerFacade(NumberReceiverFacade receiverFacade,
                               LuckyNumbersGeneratorClient generatorClient,
                               TicketChecker ticketChecker,
                               ResultCheckerRepository repository) {
        this.receiverFacade = receiverFacade;
        this.generatorClient = generatorClient;
        this.ticketChecker = ticketChecker;
        this.repository = repository;
    }

    public boolean wasCheckedForNextDrawDate() {
        DrawDateDto drawDateDto = receiverFacade.specifyDrawDate();
        return repository.existsByDrawDate(drawDateDto.drawDate());
    }

    public List<CheckedTicketDto> generateResults() {
        AllNumbersFromUsersDto allNumbersFromUsersDto = receiverFacade.userNumbersForNextDrawDate();
        if (!allNumbersFromUsersDto.tickets().isEmpty()) {
            LocalDateTime drawDate = retrieveDrawDate(allNumbersFromUsersDto);
            LuckyNumbersDto luckyNumbersDto = generatorClient.retrieveLuckyNumbersForDate(drawDate);
            List<CheckedTicket> checkedTickets = ticketChecker.checkAllTickets(luckyNumbersDto.winningNumbers(),
                    allNumbersFromUsersDto.tickets());
            repository.saveAll(checkedTickets);
            return checkedTicketMapper.mapCheckedTicketListOfDocumentToCheckedTicketDtoList(checkedTickets);
        }
        return Collections.emptyList();
    }

    private LocalDateTime retrieveDrawDate(AllNumbersFromUsersDto allNumbersFromUsersDto) {
        return allNumbersFromUsersDto.tickets()
                .stream()
                .findFirst()
                .map(LotteryTicketDto::drawDate)
                .orElseThrow(DrawDateNotSpecifiedForTicketException::new);
    }

    public UniqueTicketResultDto checkIfTicketIsChecked(UUID id) {
        CheckedTicket checkedTicket = repository.findById(id).orElseThrow(() -> {
            throw new MongoException("The ticket has not been checked yet");
        });
        boolean isBeforeDraw = checkedTicket.getDrawDate().isAfter(LocalDateTime.now());
        if (isBeforeDraw) {
            return new UniqueTicketResultDto(null, TicketStateDto.TOO_EARLY);
        }
        CheckedTicketDto checkedTicketDto = checkedTicketMapper.mapCheckedTicketDocumentToCheckedTicketDto(checkedTicket);
        return new UniqueTicketResultDto(checkedTicketDto, TicketStateDto.CHECKED);
    }
}
