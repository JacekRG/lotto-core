package pl.lotto.numberreceiver;

import java.util.UUID;

class LotteryIdGenerator {

    public static UUID generateLotteryId() {
        return UUID.randomUUID();
    }
}
