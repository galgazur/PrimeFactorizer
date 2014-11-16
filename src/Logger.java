import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Logger {

	private class PrimePair {
		public int amount;
		public BigInteger prime;

		private PrimePair(int amount, BigInteger prime) {
			this.amount = amount;
			this.prime = prime;
		}
	}

	public void log(Tuple tuple, PrintWriter out) {
		// String[] rows = str.split("\n");

		BigInteger N = tuple.getNumber();
		ArrayList<String> factors = tuple.getFactors();

		ArrayList<BigInteger> primes = new ArrayList<BigInteger>();

		for (int i = 0; i < factors.size(); i++) {
			primes.add(new BigInteger(factors.get(i).toString()));
		}

		Collections.sort(primes);

		ArrayList<PrimePair> primePairs = new ArrayList<PrimePair>();

		BigInteger comparePrime = primes.get(0);
		BigInteger nextPrime = comparePrime;
		int counter = 1;
		for (int i = 1; i < primes.size(); i++) {
			nextPrime = primes.get(i);
			if (comparePrime.compareTo(nextPrime) == 0) {
				counter++;
			} else {
				primePairs.add(new PrimePair(counter, comparePrime));
				comparePrime = nextPrime;
				counter = 1;
			}
		}

		primePairs.add(new PrimePair(counter, nextPrime));

		PrimePair primePair = primePairs.get(0);
		String str = primePair.prime.toString() + " " + primePair.amount;

		for (int i = 1; i < primePairs.size(); i++) {
			primePair = primePairs.get(i);

			str += " " + primePair.prime.toString() + " " + primePair.amount;

		}

		out.println(str);

	}

	public void logPerAddedNumber(boolean done, Tuple tuple, int added) {
		// String[] rows = str.split("\n");

		
		
		if (tuple.getFactors().size() == 0) {
			return;
		}

		String prefix = "";
		if (!done) {
			prefix = "NOT_DONE-";
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
		Date date = new Date();
		String time = dateFormat.format(date);

		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(prefix + time
					+ "factorization-" + added + ".txt", true)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayList<String> factors = tuple.getFactors();

		ArrayList<BigInteger> primes = new ArrayList<BigInteger>();

		for (int i = 0; i < factors.size(); i++) {
			primes.add(new BigInteger(factors.get(i).toString()));
		}

		Collections.sort(primes);

		ArrayList<PrimePair> primePairs = new ArrayList<PrimePair>();

		BigInteger comparePrime = primes.get(0);
		BigInteger nextPrime = comparePrime;
		int counter = 1;
		for (int i = 1; i < primes.size(); i++) {
			nextPrime = primes.get(i);
			if (comparePrime.compareTo(nextPrime) == 0) {
				counter++;
			} else {
				primePairs.add(new PrimePair(counter, comparePrime));
				comparePrime = nextPrime;
				counter = 1;
			}
		}

		primePairs.add(new PrimePair(counter, nextPrime));

		PrimePair primePair = primePairs.get(0);
		String str = primePair.prime.toString() + " " + primePair.amount;

		for (int i = 1; i < primePairs.size(); i++) {
			primePair = primePairs.get(i);

			str += " " + primePair.prime.toString() + " " + primePair.amount;

		}

		out.println(str);
		out.close();

	}
}
