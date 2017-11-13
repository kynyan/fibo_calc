package model;

import org.junit.Test;
import utils.Utils;

import java.math.BigInteger;

import static io.qala.datagen.RandomShortApi.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.Assert.assertTrue;

public class CalculatorTest {
    @Test public void computesFibonacciNumber() {
        BigInteger result = new Calculator(integer(0, Calculator.FIBO_INDEX_UPPER_BOUNDARY)).getFiboNumber();
        assertTrue(isFibonacci(result));
    }

    @Test public void throwsError_WhenIndexNegative() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Calculator((-1) * positiveInteger()))
                .withMessage(Calculator.FIBO_INDEX_NEGATIVE_NOTE);
    }

    @Test public void throwsError_WhenIndexExceedsUpperBoundary() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Calculator(Calculator.FIBO_INDEX_UPPER_BOUNDARY + 1))
                .withMessage(Calculator.FIBO_INDEX_EXCEEDED_NOTE);
    }

    private boolean isFibonacci(BigInteger number) {
        BigInteger square =  number.multiply(number);
        BigInteger five = BigInteger.valueOf(5);
        BigInteger four = BigInteger.valueOf(4);
        return Utils.isPerfectSquare(five.multiply(square).subtract(four)) ||
                Utils.isPerfectSquare(five.multiply(square).add(four));
    }
}
