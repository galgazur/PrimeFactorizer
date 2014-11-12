import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

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
}
