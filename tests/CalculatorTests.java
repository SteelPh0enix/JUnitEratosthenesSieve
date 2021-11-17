import com.steelph0enix.Calculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CalculatorTests {
    private final Calculator calc = new Calculator();

    static Stream<Arguments> divisiblePairsProvider() {
        return Stream.of(
                arguments(1, 1),
                arguments(2, 1),
                arguments(2, 2),
                arguments(3, 1),
                arguments(4, 1),
                arguments(4, 2),
                arguments(5, 1),
                arguments(6, 3),
                arguments(10, 2),
                arguments(10, 5),
                arguments(100, 10),
                arguments(70, 7),
                arguments(36, 6)
        );
    }

    static Stream<Arguments> nonDivisiblePairsProvider() {
        return Stream.of(
                arguments(1, 20),
                arguments(22, 15),
                arguments(3, 2),
                arguments(5, 3),
                arguments(123, 456),
                arguments(199, 10),
                arguments(100, 33)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 10, 20, 50, 100, 1000, Integer.MAX_VALUE})
    void testFloorRootValue_returnsCorrectResult_correctParametersGiven(int number) {
        final int calculatorResult = calc.floorRootValue(number);
        final int expectedResult = (int) Math.sqrt(number);

        assertEquals(calculatorResult, expectedResult);
    }

    @ParameterizedTest
    @MethodSource("divisiblePairsProvider")
    void testIsDivisible_returnsCorrectResult_correctParametersGiven(int dividend, int divisor) {
        final boolean calculatorResult = calc.isDivisible(dividend, divisor);
        final boolean expectedResult = true;

        assertEquals(calculatorResult, expectedResult);
    }

    @ParameterizedTest
    @MethodSource("nonDivisiblePairsProvider")
    void testIsDivisible_returnsCorrectResult_incorrectParametersGiven(int dividend, int divisor) {
        final boolean calculatorResult = calc.isDivisible(dividend, divisor);
        final boolean expectedResult = false;

        assertEquals(calculatorResult, expectedResult);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5, -10, -20, 0, 1234, Integer.MIN_VALUE, Integer.MAX_VALUE})
    void testIsDivisible_throwsException_divisionByZero(int dividend) {
        assertThrows(IllegalArgumentException.class, () -> calc.isDivisible(dividend, 0));
    }

    @ParameterizedTest
    @ValueSource(ints = {-2, -3, -5, -7, -11, -13, 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97})
    void testIsPrime_returnsCorrectResult_correctParametersGiven(int number) {
        final boolean calculatorResult = calc.isPrime(number);
        final boolean expectedResult = true;

        assertEquals(calculatorResult, expectedResult);
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, -8, -6, -4, -1, 0, 1, 4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 22})
    void testIsPrime_returnsCorrectResult_incorrectParametersGiven(int number) {
        final boolean calculatorResult = calc.isPrime(number);
        final boolean expectedResult = false;

        assertEquals(calculatorResult, expectedResult);
    }
}
