package pl.lotto.resultannouncer;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
public class AnnouncedTicket {

    private final UUID ticketId;
    private final AnnouncerMessages announcerMessage;

    public AnnouncedTicket(UUID ticketId, AnnouncerMessages announcerMessage) {
        this.ticketId = ticketId;
        this.announcerMessage = announcerMessage;
    }
}
