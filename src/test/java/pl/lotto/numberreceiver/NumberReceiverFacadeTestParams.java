//package pl.lotto.numberreceiver;
//
//
//import junitparams.JUnitParamsRunner;
//import junitparams.Parameters;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.time.Clock;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.mock;
//
//@RunWith(JUnitParamsRunner.class)
//public class NumberReceiverFacadeTestParams {
//    private static Object[] testValues() {
//        return new Object[]{
//                new Object[]{Arrays.asList(1, 2, 3, 4, 5, 6), "success"},
//                new Object[]{Arrays.asList(1, 2, 3, 4, 5, 6, 7), "failure"},
//                new Object[]{Arrays.asList(1, 2, 3, 4, 5, 100), "failure"},
//                new Object[]{Arrays.asList(1, 2, 3, 4, 5), "failure"},
//                new Object[]{Arrays.asList(1, 2, 3, 4, 5, 5), "failure"},
//                new Object[]{Arrays.asList(1, 2, 3, 4, 5, -1), "failure"}
//        };
//    }
//
//
//
//    @Test
//    @Parameters(method = "testValues")
//    public void inputNumbers(List<Integer> numbersFromUser, String expectedResult) {
//        NumberReceiverRepository numberReceiverRepository = mock(NumberReceiverRepository.class);
//        NumberReceiverFacade numberReceiverFacade = new NumberReceiverConfiguration().createForTests(numberReceiverRepository, Clock.systemUTC());
//        numberReceiverFacade.validateEnteredNumbers(numbersFromUser);
//        assertThat(numberReceiverFacade.validateEnteredNumbers(numbersFromUser).message()).isEqualTo(expectedResult);
//
//    }
//
//}
