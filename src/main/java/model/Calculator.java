package model;

import lombok.Getter;

import java.math.BigInteger;

@Getter
public class Calculator {
    public static final int FIBO_INDEX_UPPER_BOUNDARY = 1_000_000;
    public static final String FIBO_INDEX_EXCEEDED_NOTE = "Should be less than 1 000 000";
    public static final String FIBO_INDEX_NEGATIVE_NOTE = "Should be equal or greater than 0";

    private BigInteger fiboNumber;

    public Calculator(int index) {
        checkIfIndexIsValid(index);
        this.fiboNumber = getFibonacciNumber(index);
    }

    // Uses matrix multiplication to compute Fibonacci number
    private BigInteger getFibonacciNumber(int n) {
        BigInteger[] matrix = {BigInteger.ONE, BigInteger.ONE, BigInteger.ONE, BigInteger.ZERO};
        return matrixPow(matrix, n)[1];
    }

    private void checkIfIndexIsValid(int index) {
        if (index < 0) throw new IllegalArgumentException(FIBO_INDEX_NEGATIVE_NOTE);
        if (index > FIBO_INDEX_UPPER_BOUNDARY) throw new IllegalArgumentException(FIBO_INDEX_EXCEEDED_NOTE);
    }

    // Computes the power of a matrix. The matrix is packed in row-major order.
    private BigInteger[] matrixPow(BigInteger[] matrix, int n) {
        if (n < 0)
            throw new IllegalArgumentException();
        BigInteger[] result = {BigInteger.ONE, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ONE};
        while (n != 0) {  // Exponentiation by squaring
            if (n % 2 != 0)
                result = matrixMultiply(result, matrix);
            n /= 2;
            matrix = matrixMultiply(matrix, matrix);
        }
        return result;
    }

    // Multiplies two matrices.
    private static BigInteger[] matrixMultiply(BigInteger[] x, BigInteger[] y) {
        return new BigInteger[] {
                x[0].multiply(y[0]).add(x[1].multiply(y[2])),
                x[0].multiply(y[1]).add(x[1].multiply(y[3])),
                x[2].multiply(y[0]).add(x[3].multiply(y[2])),
                x[2].multiply(y[1]).add(x[3].multiply(y[3])),
        };
    }
}
