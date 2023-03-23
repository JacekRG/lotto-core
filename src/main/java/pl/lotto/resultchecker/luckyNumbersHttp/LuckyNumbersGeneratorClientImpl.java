package pl.lotto.resultchecker.luckyNumbersHttp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class LuckyNumbersGeneratorClientImpl implements LuckyNumbersGeneratorClient {

    private final RestTemplate restTemplate;

    @Value("${luckyNumbersGeneratorFacade.url:http://localhost}")
    private String luckyNumbersGeneratorFacadeUrl;

    @Value("${luckyNumbersGeneratorFacade.port:8087}")
    private String luckyNumbersGeneratorFacadePort;

    public LuckyNumbersGeneratorClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public LuckyNumbersDto retrieveLuckyNumbersForDate(LocalDateTime date) {
//        String url = luckyNumbersGeneratorFacadeUrl + ":" + luckyNumbersGeneratorFacadePort + "/?" + "date="
//                + date.format(DateTimeFormatter.ISO_DATE_TIME);

        String strip = date.format(DateTimeFormatter.ISO_DATE_TIME).strip();
        String url = UriComponentsBuilder
                .fromUriString(luckyNumbersGeneratorFacadeUrl)
                .port(luckyNumbersGeneratorFacadePort)
                .queryParam("date", strip)
                .build().toUriString();

        ResponseEntity<LuckyNumbersDto> response = restTemplate.getForEntity(url, LuckyNumbersDto.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new IllegalStateException("Unexpected status code: " + response.getStatusCodeValue());
        }
    }
}
