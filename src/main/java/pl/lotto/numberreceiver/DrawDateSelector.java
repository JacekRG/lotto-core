package pl.lotto.numberreceiver;

import java.time.*;
import java.time.temporal.TemporalAdjusters;

public class DrawDateSelector {

    public static final LocalDateTime DATE_AND_TIME_OF_TICKET_PURCHASE = LocalDateTime.now();
    public static final LocalDate DATE_OF_TICKET_PURCHASE = LocalDate.now();
    public static final int DRAW_HOUR = 12;
    public static final DayOfWeek DRAW_DAY = DayOfWeek.SATURDAY;

    public static LocalDateTime specifyExactDateNextDraw() {

        if (DATE_OF_TICKET_PURCHASE.getDayOfWeek() == DRAW_DAY && DATE_AND_TIME_OF_TICKET_PURCHASE.getHour() >= DRAW_HOUR) {
            return (LocalDateTime.of(DATE_OF_TICKET_PURCHASE
                    .plusWeeks(1), LocalTime.of(DRAW_HOUR, 0)))
                    .with(TemporalAdjusters.nextOrSame(DRAW_DAY));

        } else {
            return (LocalDateTime.of(DATE_OF_TICKET_PURCHASE, LocalTime.of(DRAW_HOUR, 0)))
                    .with(TemporalAdjusters.nextOrSame(DRAW_DAY));
        }
    }
}
