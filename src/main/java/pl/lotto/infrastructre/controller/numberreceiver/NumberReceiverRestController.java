package pl.lotto.infrastructre.controller.numberreceiver;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.lotto.numberreceiver.NumberReceiverFacade;
import pl.lotto.numberreceiver.dto.DrawDateDto;
import pl.lotto.numberreceiver.dto.NumberReceiverResultDto;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
public class NumberReceiverRestController {

    private final NumberReceiverFacade numberReceiverFacade;

    public NumberReceiverRestController(NumberReceiverFacade numberReceiverFacade) {
        this.numberReceiverFacade = numberReceiverFacade;
    }

    @GetMapping("/drawDate")
    public ResponseEntity<DrawDateDto> getDrawDate() {
        DrawDateDto drawDateDto = numberReceiverFacade.specifyDrawDate();
        return ResponseEntity.ok().body(drawDateDto);
    }

    @PostMapping("/inputNumbers")
    public ResponseEntity<NumberReceiverResultDto> inputNumbers(@RequestBody InputNumbersRequest request) {
        List<Integer> numbers = request.numbers();
        NumberReceiverResultDto numberReceiverResultDto = numberReceiverFacade.inputNumbers(numbers);
        if (numberReceiverResultDto.isFailure()) {
            return ResponseEntity.status(BAD_REQUEST).body(numberReceiverResultDto);
        }
        return ResponseEntity.ok().body(numberReceiverResultDto);
    }
}
