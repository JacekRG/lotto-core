package pl.lotto.resultannouncer;

import java.util.Set;
import java.util.UUID;

class ResultAnnouncerSummarizer {
    public AnnouncedTicket summarizeUniqueTicket(UUID lotteryId, Set<Integer> numbersOfHits) {
        return new AnnouncedTicket(lotteryId, specifyPrize(numbersOfHits.size()));
    }

    private AnnouncerMessages specifyPrize(int numberOfHit) {
        return switch (numberOfHit) {
            case 6 -> AnnouncerMessages.MAIN_PRIZE;
            case 5 -> AnnouncerMessages.SECOND_PRIZE;
            case 4 -> AnnouncerMessages.THIRD_PRIZE;
            case 3 -> AnnouncerMessages.FOURTH_PRIZE;
            default -> AnnouncerMessages.NO_PRIZE;
        };
    }
}
