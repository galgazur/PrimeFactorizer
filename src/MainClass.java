import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;

public class MainClass {

	private final int ZERO_AMOUNT = 60;

	private final int j = 0;

	private static final int TRIAL_DIVISION_PRIME_AMOUNT = 10000000;

	private final int POLLARD_MAX_MINUTES = 30;

	private static final String primesFile = "primes.txt";

	public static void makePrimeFile() {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(primesFile, true)));

			PrimeFinder primeFinder = new PrimeFinder();

			primeFinder.findPrimes2(out, TRIAL_DIVISION_PRIME_AMOUNT);

			out.close();

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public MainClass(String personNumber) {

		// construct bigInteger with trailing zeros
		String bigNum = personNumber;
		for (int i = 0; i < ZERO_AMOUNT + j; i++) {
			bigNum += "0";
		}
		BigInteger number = new BigInteger(bigNum);

		// read primes from file
		PrimeFinder primeFinder = new PrimeFinder();
		primeFinder.getPrimesFromFile(primesFile, TRIAL_DIVISION_PRIME_AMOUNT);

		Logger logger = new Logger();

		Tuple tuple;
		BigInteger temp = number;
		for (int i = 1; i < 100; i++) {

			temp = temp.add(BigInteger.ONE);
			tuple = factor(temp, primeFinder);

			if (tuple == null) {
				continue;
			}

			logger.logPerAddedNumber(tuple, 1);

			System.out.println("ANSWER:");
			ArrayList<String> als = tuple.getFactors();
			for (int q = 0; q < als.size(); q++) {
				System.out.print(" * " + als.get(q));
			}

		}

	}

	private Tuple factor(BigInteger N, PrimeFinder primeFinder) {
		Tuple tuple = new Tuple(N, new ArrayList<String>(), primeFinder);

		if (tuple.isDone()) {
			return tuple;
		}

		// first try trial division
		System.out.println("Initiating trial division");
		SmallFactorizer smallFactors = new SmallFactorizer(primeFinder);
		smallFactors.trialDivision(tuple);

		// run pollard for POLLARD_MAX_MINUTES. If factor is found, run again.
		// Repeat until no more factor is found
		System.out.println("Initiating pollard-rho");
		Pollard pollard = new Pollard();
		boolean foundFactor = true;
		while (!tuple.isDone() && foundFactor) {

			foundFactor = pollard.pollardRho(tuple, POLLARD_MAX_MINUTES);

		}

		// return if done or run QS otherwise
		if (tuple.isDone()) {
			return tuple;
		} else {
			System.out.println("Initiating quadratic sieve");
			QSHelper qsHelper = new QSHelper(primeFinder);
			Tuple qsTuple = qsHelper.factorize(tuple.getNumber());

			tuple.mergeCohesiveTuples(qsTuple);
		}

		return tuple;

	}

	public static void main(String[] args) {
		String andreas = "9204123476";
		String niklas = "9103090198";

		new MainClass(andreas);

	}

}
