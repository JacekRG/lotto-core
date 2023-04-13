package pl.lotto.resultannouncer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResultAnnouncerConfiguration {

    @Bean
    public ResultAnnouncerSummarizer resultAnnouncerSummarizer() {
        return new ResultAnnouncerSummarizer();
    }
}
