package pl.lotto.feature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.lotto.BaseIntegrationTest;
import pl.lotto.numberreceiver.dto.NumberReceiverResultDto;
import pl.lotto.resultchecker.ResultCheckerFacade;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LottoWinnerSpec extends BaseIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ResultCheckerFacade resultCheckerFacade;

    @Test
    public void should_user_play_and_check_win_after_some_days() throws Exception {
        // Step 0
        wireMockServer.stubFor(WireMock.get(urlEqualTo("/?date=2022-12-24T12:00:00"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {"winningNumbers":[1,2,3,4,5,6],"localDateTime":"2022-12-24T12:00:00"}
                                          """.trim())));


        // STEP 1: when user input 6 correct numbers (1,2,3,4,5,6) (in range 1-99) to POST /inputNumbers
        // STEP 2 system returns unique random userLotteryId and returned draw 24-12-2022 to user
        // given

        // when
        ResultActions perform = mockMvc.perform(post("/inputNumbers")
                .content("{\"numbers\":[1,2,3,4,5,6]}")
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        MvcResult mvcResult = perform
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(
                                "{\"message\":\"success\"," +
                                        "\"drawDate\":\"2022-12-24T12:00:00\"}"))
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        NumberReceiverResultDto result = objectMapper.readValue(json, NumberReceiverResultDto.class);
        // STEP 3 system generates random winning numbers (1,2,3,4,5,6) using “lotto” logic for 24-12-2022, winning numbers are generated every saturday at 12:00
        // when
//        await().atMost(30, SECONDS)
//                .pollInterval(Duration.ofSeconds(1))
//                .until(() -> !luckyNumbersGeneratorFacade.retrieve(result.drawDate()).winningNumbers().isEmpty());


        // STEP 00000 system checkedWinner results
        // given
        // when
        await().atMost(10, SECONDS)
                .pollInterval(Duration.ofSeconds(1))
                .until(() -> resultCheckerFacade.wasCheckedForNextDrawDate());


        // STEP 4 user wants to know if won using GET /winners/{userLotteryId} but before draw
        // given
        // when
        // then
        String dateBeforeDrawingAsText = "2022-12-23T12:00:00";

        LocalDateTime dateBeforeDrawing = LocalDateTime.parse(dateBeforeDrawingAsText);
        adjustableClock.setClockToLocalDateTime(dateBeforeDrawing);

        ResultActions perform2 = mockMvc.perform(get("/winners/" + result.lotteryId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        {"ticketAnnounce":null,"message":"TOO_EARLY"}
                        """))
                .andExpect(status().isNoContent());


        // STEP 02034i234 after 2 days
        adjustableClock.plusDays(2);


        // STEP 5 system checkedWinner
        // given
        // when
        await().atMost(20, SECONDS)
                .pollInterval(Duration.ofSeconds(1))
                .until(() -> resultCheckerFacade.checkIfTicketIsChecked(result.lotteryId())
                        .ticketStateDto()
                        .isChecked());
        // then
        // STEP 7 user wants to know if won using GET /winners/{userLotteryId} after draw
        // when

        ResultActions perform3 = mockMvc.perform(get("/winners/" + result.lotteryId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json("""
                        {"message":"MAIN_PRIZE"}
                        """))
                .andExpect(status().isOk());
        // then

    }
}
