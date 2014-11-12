import java.math.BigInteger;
import java.util.ArrayList;

public class PrimeFinder {

	private static final long PRIME_GUESS_THRESHOLD = 100000;
	private long[] primes;
	private long previousbound = 2;

	private final int CERTAINTY = 30;

	/**
	 * Find primes smaller than this bound
	 * 
	 * @param bound
	 * @return
	 */
	public void findPrimes(long bound) {
		if (bound > previousbound) {
			ArrayList<Long> arrayList = new ArrayList<Long>();

			for (long i = previousbound; i <= bound; i++) {
				if (isPrime(i)) {
					arrayList.add(i);
				}
			}

			primes = new long[arrayList.size()];

			// primes = arrayList.toArray(new Long[arrayList.size()]);

			for (int i = 0; i < primes.length; i++) {
				primes[i] = arrayList.get(i);
			}

			// primes = arrayList.toArray();

			previousbound = bound;
		}

	}

	/**
	 * return stored primes list
	 * 
	 * @return
	 */
	public long[] getPrimes() {
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

	public boolean isPrime(long primeCandidate) {
		BigInteger bigInteger = new BigInteger("" + primeCandidate);

		return bigInteger.isProbablePrime(CERTAINTY);
	}

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
