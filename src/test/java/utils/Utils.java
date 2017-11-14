package utils;

import java.math.BigInteger;

public class Utils {
    public static boolean isPerfectSquare(BigInteger n) {
        if (n.compareTo(BigInteger.ZERO) < 0) return false;

        BigInteger tst = sqrt(n);
        return tst.multiply(tst).equals(n);
    }

    public static BigInteger sqrt(BigInteger x) {
        BigInteger div = BigInteger.ZERO.setBit(x.bitLength()/2);
        BigInteger div2 = div;
        // Loop until we hit the same value twice in a row, or wind
        // up alternating.
        for(;;) {
            BigInteger y = div.add(x.divide(div)).shiftRight(1);
            if (y.equals(div) || y.equals(div2))
                return y;
            div2 = div;
            div = y;
        }
    }
}
