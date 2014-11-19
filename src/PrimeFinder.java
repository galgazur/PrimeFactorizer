import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class PrimeFinder {

	private static final long	PRIME_GUESS_THRESHOLD	= 1;
	private long[]				primes;
	private long				previousbound			= 2;

	private final int			CERTAINTY				= 30;

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

			for (int i = 0; i < primes.length; i++) {
				primes[i] = arrayList.get(i);
			}

			previousbound = bound;
		}

	}

	public void getPrimesFromFile(String file, int amount) {
		long[] primes = new long[amount];

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			long start = System.currentTimeMillis();
			System.out.println("primes.txt not found, generating file now with the "
					+ MainClass.TRIAL_DIVISION_PRIME_AMOUNT + " first primes.");
			MainClass.makePrimeFile();
			long end = System.currentTimeMillis() - start;
			long minutes = end / (1000 * 60);
			System.out.println("primes.txt was generated in " + minutes + " minutes");
			try {
				br = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		try {
			br = new BufferedReader(new FileReader(file));

			String line;
			int i = 0;
			while ((line = br.readLine()) != null && i < amount) {

				primes[i] = Long.parseLong(line);
				i++;
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.primes = primes;
	}

	/*
	 * Used to build a file with the Integer.MAX_VALUE first primes
	 */
	public void findPrimes2(PrintWriter out, int amount) {

		int primeAmount = 0;
		for (long i = 2; i <= Integer.MAX_VALUE; i++) {
			if (primeAmount == amount) {
				break;
			}
			if (isPrime(i)) {
				out.println(i);
				primeAmount++;

			}
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
		if (primeCandidate.compareTo(new BigInteger("" + PRIME_GUESS_THRESHOLD)) > 0) {
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
		return millerRabin(N);
	}

	private boolean millerRabin(BigInteger N) {
		if (N.mod(new BigInteger("2")).equals(BigInteger.ZERO)) {
			return false;
		}
		Random rnd = new Random();
		for (int i = 0; i < CERTAINTY; i++) {
			BigInteger a;
			BigInteger N_1 = N.subtract(BigInteger.ONE);
			do {
				a = new BigInteger(N.bitLength(), rnd);
			} while (a.equals(BigInteger.ZERO) || a.equals(BigInteger.ONE) || a.compareTo(N_1) >= 0);

			boolean pass = false;
			BigInteger d = N_1;
			int s = d.getLowestSetBit();
			d = d.shiftRight(s);
			BigInteger x = a.modPow(d, N);
			if (x.equals(BigInteger.ONE)) {
				pass = true;
			}
			for (int j = 0; j < s - 1; j++) {
				if (x.equals(N_1)) {
					pass = true;
				}
				x = x.multiply(x).mod(N);
			}
			if (x.equals(N_1)) {
				pass = true;
			}
			if (!pass) {
				return false;
			}
		}
		return true;
	}

	private boolean isDefinitePrime(BigInteger primeCandidate) {
		// trial division
		for (long i = 2; i <= PRIME_GUESS_THRESHOLD; i++) {
			BigInteger rest = primeCandidate.divideAndRemainder(new BigInteger("" + i))[1];
			if (rest.compareTo(BigInteger.ZERO) == 0) {
				return false;
			}
		}
		return true;
	}

}
