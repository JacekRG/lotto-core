package pl.lotto.numberreceiver;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NumberReceiverConfiguration {

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    public NumberReceiverFacade numberReceiverFacade(NumberReceiverRepository repository, Clock clock) {
        DrawDateSelector drawDateSelector = new DrawDateSelector(clock);
        NumberInputValidator validator = new NumberInputValidator();
        return new NumberReceiverFacade(validator, drawDateSelector, repository);
    }
}
