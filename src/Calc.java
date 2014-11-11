import java.math.BigInteger;
import java.util.Random;

public class Calc {

	/**
	 * NOT WRITTEN BY US!!! THIS WAS WRITTEN BY Faruk Akgul, August 22, 2009.
	 * http://faruk.akgul.org/blog/javas-missing-algorithm-biginteger-sqrt/
	 * 
	 * @param n
	 * @return
	 */
	public static BigInteger sqrt(BigInteger n) {
		BigInteger a = BigInteger.ONE;
		BigInteger b = new BigInteger(n.shiftRight(5).add(new BigInteger("8"))
				.toString());
		while (b.compareTo(a) >= 0) {
			BigInteger mid = new BigInteger(a.add(b).shiftRight(1).toString());
			if (mid.multiply(mid).compareTo(n) > 0) {
				b = mid.subtract(BigInteger.ONE);
			} else {
				a = mid.add(BigInteger.ONE);
			}
		}
		return a.subtract(BigInteger.ONE);
	}

	public static BigInteger gcd(BigInteger A, BigInteger B) {
		if (A.compareTo(B) == -1) {
			BigInteger tmp = A;
			A = B;
			B = tmp;
		}

		// BigInteger[] divided = null;
		BigInteger tmp = null;
		while (B.signum() != 0) {
			tmp = A.divideAndRemainder(B)[1];
			A = B;
			B = tmp;
		}

		return A;
	}

	public static BigInteger square(BigInteger N) {
		return N.multiply(N);
	}

	public static BigInteger generateRandomBigInteger(BigInteger N) {
		BigInteger X = null;
		// runs less than two iterations on average
		do {
			X = new BigInteger(N.bitLength(), new Random());
		} while (X.compareTo(N) >= 0);

		return X;
	}

}
