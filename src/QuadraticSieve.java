import java.math.BigInteger;
import java.util.ArrayList;

public class QuadraticSieve {

	/**
	 * returns a string representation for all primes and the amount of
	 * occurrences
	 * 
	 * @return
	 */
	public String factorize(long SmoothnessBound, PrimeFinder primes,
			BigInteger N) {

		primes.findPrimes(SmoothnessBound);
		BigInteger[] primeArray = primes.getPrimes();
		long[] factorBase = getFactorBase(primes, N);

		BigInteger m = Calc.sqrt(N);

		long[][] qxEVectorsArray = new long[2 * factorBase.length][]; // contains
																		// e-vectors
																		// for
																		// each
																		// q(x)
		int qxPointer = 0;

		for (long x = 0; x < Long.MAX_VALUE; x++) {
			// try different x

			long[] factorization = isPrimeSmooth(x, factorBase);
			if (factorization != null) {
				qxFactorArray[qxPointer] = factorization;
				qxPointer++;
			}
		}

		BigInteger xAns = null;
		BigInteger yAns = null;
		ArrayList<boolean[]> Tcombinations = findTCombinations(qxFactorArray);
		for (boolean[] T : Tcombinations) {

			xAns = getX(T);
			yAns = getY(T);

			if (xAns.compareTo(yAns.mod(N)) != 0) {
				break;
			}

		}
		if (xAns == null) {
			return null;
		} else {
			// MAKE OUR OWN GCD!!!
			return "" + gcd(xAns.subtract(yAns), N);
		}
	}

	private long[] getFactorBase(long[] primes, BigInteger N) {
		ArrayList<Long> factorBase = new ArrayList<Long>();
		factorBase.add((long) -1);
		for (long a : primes) {
			if (isQuadraticResidue(a, N)) {
				factorBase.add(a);
			}
		}
		long[] result = null;
		return factorBase.toArray();
	}

	private boolean isQuadraticResidue(long a, BigInteger N) {
		return true;
	}

	private long[] isPrimeSmooth(long x, long[] factorBase) {
		long[] array = null;
		if (true) {
			return array;
		} else {
			return null;
		}
	}

	private ArrayList<boolean[]> findTCombinations(long[][] qxFactorArray) {
		return null;
	}

	private BigInteger getY(boolean[] T) {
		return null;
	}

	private BigInteger getX(boolean[] T) {
		return null;
	}

	/**
	 * NOT WRITTEN BY US!!! THIS IS THE NATIVE JAVA GCD FOR BIGINTEGER!
	 * 
	 * @param g
	 * @param N
	 * @return
	 */
	private BigInteger gcd(BigInteger g, BigInteger N) {
		return g.gcd(N);
	}

}
