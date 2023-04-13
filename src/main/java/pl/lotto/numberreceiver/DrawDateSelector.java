package pl.lotto.numberreceiver;

import org.springframework.beans.factory.annotation.Value;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class DrawDateSelector {
    public Clock clock;

    @Value("${drawDate.hour:12}")
    private int drawHour;
    @Value("${drawDate.day:DayOfWeek.SATURDAY}")
    private DayOfWeek drawDay;
    private LocalDateTime todayDraw;
    LocalDateTime nextDrawDate;

    public DrawDateSelector(Clock clock) {
        this.clock = clock;
        this.todayDraw = LocalDateTime.now(clock).truncatedTo(ChronoUnit.HOURS);
        this.nextDrawDate = todayDraw.with(TemporalAdjusters.next(drawDay)).withHour(drawHour);
    }

    public LocalDateTime calculateNearestDrawDate() {
        if (isAbleToDrawToday()) {
            return todayDraw.withHour(drawHour);
        } else {
            return nextDrawDate;
        }
    }

    private boolean isAbleToDrawToday() {
        if (todayDraw.getDayOfWeek() != drawDay) {
            return false;
        } else {
            return todayDraw.getHour() < drawHour;
        }
    }
}
