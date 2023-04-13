package pl.lotto.infrastructre.scheduler.resultsannouncer;

import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.lotto.resultchecker.ResultCheckerFacade;

@Component
@Log4j2
public class SchedulerResultChecker {
    private final ResultCheckerFacade resultCheckerFacade;

    public SchedulerResultChecker(ResultCheckerFacade resultCheckerFacade) {
        this.resultCheckerFacade = resultCheckerFacade;
    }

    @Scheduled(cron = "${lotto.checker.lotteryResultsAnnouncement}")
    public void checkLotteryResultsScheduledTask() {
        log.info("scheduler started");
        resultCheckerFacade.generateResults();
    }
}
