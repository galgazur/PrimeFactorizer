import java.math.BigInteger;
import java.util.ArrayList;

public class PrimeFinder {

	private static final long PRIME_GUESS_THRESHOLD = 1000;
	private BigInteger[] primes;
	private BigInteger previousbound;

	private final int CERTAINTY = 30;

	/**
	 * Find primes smaller than this bound
	 * 
	 * @param bound
	 * @return
	 */
	public void findPrimes(BigInteger bound) {
		if (bound.compareTo(previousbound) > 0) {
			ArrayList<BigInteger> tmpArr = new ArrayList<BigInteger>();
			// BigInteger[] results = new BigInteger[Integer.MAX_VALUE-5];
			// long[] results = new long[Integer.MAX_VALUE-5];
			// int resultPointer = 0;

			for (BigInteger i = new BigInteger("2"); i.compareTo(bound) <= 0; i = i
					.subtract(BigInteger.ONE)) {
				;
				if (isPrime(i)) {
					tmpArr.add(i);
					// results[resultPointer] = i;
					// resultPointer++;
					// break;
				}
			}

			primes = tmpArr.toArray(new BigInteger[tmpArr.size()]);

			// primes = new long[resultPointer];
			// for (int i = 0; i < resultPointer; i++) {
			// primes[i] = results[i];
			// }
			previousbound = bound;
		}
	}

	/**
	 * return stored primes list
	 * 
	 * @return
	 */
	public BigInteger[] getPrimes() {
		return primes;
	}

	public boolean isPrime(BigInteger primeCandidate) {
		if (primeCandidate
				.compareTo(new BigInteger("" + PRIME_GUESS_THRESHOLD)) > 0) {
			return isProbablePrime(primeCandidate);
		} else {
			return isDefinitePrime(primeCandidate);
		}
	}

	// // miller-rabin
	// private boolean isProbablePrime(BigInteger N) {
	//
	// // TODO
	//
	// if (N.mod(new BigInteger("2")).compareTo(BigInteger.ZERO) > 0) {
	// return false;
	// }
	//
	// BigInteger a;
	// for (long i = 2; i <= PRIME_GUESS_THRESHOLD; i++) {
	// a = Calc.generateRandomBigInteger(N);
	// }
	//
	// return N.isProbablePrime(CERTAINTY);
	//
	// }

	private boolean isProbablePrime(BigInteger N) {
		return N.isProbablePrime(CERTAINTY);
	}

	private boolean isDefinitePrime(BigInteger primeCandidate) {
		// trial division
		for (long i = 2; i <= PRIME_GUESS_THRESHOLD; i++) {
			BigInteger rest = primeCandidate.divideAndRemainder(new BigInteger(
					"" + i))[1];
			if (rest.compareTo(BigInteger.ZERO) == 0) {
				return false;
			}
		}
		return true;
	}

}
