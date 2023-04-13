package pl.lotto.infrastructre.controller.resultannouncer;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.resultannouncer.AnnouncedTicket;
import pl.lotto.resultannouncer.ResultAnnouncerFacade;
import pl.lotto.resultannouncer.dto.ResultAnnouncerDto;

@RestController
public class ResultAnnouncerRestController {

    private final ResultAnnouncerFacade resultAnnouncerFacade;

    public ResultAnnouncerRestController(ResultAnnouncerFacade resultAnnouncerFacade) {
        this.resultAnnouncerFacade = resultAnnouncerFacade;
    }

    @GetMapping("/winners/{lotteryId}")
    public ResponseEntity<AnnouncedTicket> checkWinnerById(@PathVariable String lotteryId) {
        AnnouncedTicket announcedTicket = resultAnnouncerFacade.announceResult(UUID.fromString(lotteryId));
        return ResponseEntity.ok(announcedTicket);
    }
}
