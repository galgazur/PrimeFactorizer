import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainClass {

	private final int ZERO_AMOUNT = 60;
	private final int j = 0;
	public static final int TRIAL_DIVISION_PRIME_AMOUNT = 100000000;
	private final int POLLARD_MAX_MINUTES = 60*60;
	private final int THREAD_POOL_SIZE = 10;

	private static final String primesFile = "primes.txt";

	public static void main(String[] args) {
		String andreas = "9204123476";
		String niklas = "9103090198";

		new MainClass(andreas);

	}

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
		String num;

		ExecutorService pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		FactorThread t;
//
		ArrayList<Integer> ints = new ArrayList<Integer>();
		ints.add(18);
		ints.add(19);
		ints.add(23);
		ints.add(24);
		ints.add(25);
		ints.add(28);
		ints.add(36);
		ints.add(41);
		ints.add(43);
		ints.add(44);
		ints.add(47);
		ints.add(50);
		ints.add(53);
		ints.add(56);
		ints.add(63);
		ints.add(68);
		ints.add(69);
		ints.add(76);
		ints.add(85);
		ints.add(86);
		ints.add(87);
		ints.add(95);
		ints.add(96);
		ints.add(97);
		
//		ArrayList<Integer> ints = new ArrayList<Integer>();
//		ints.add(9);
//		ints.add(13);
//		ints.add(19);
//		ints.add(22);
//		ints.add(24);
//		ints.add(33);
//		ints.add(35);
//		ints.add(36);
//		ints.add(37);
//		ints.add(40);
//		ints.add(46);
//		ints.add(58);
//		ints.add(62);
//		ints.add(63);
//		ints.add(70);
//		ints.add(72);
//		ints.add(74);
//		ints.add(77);
//		ints.add(78);
//		ints.add(83);
//		ints.add(84);
//		ints.add(86);
//		ints.add(88);
//		ints.add(89);
//		ints.add(96);
//		ints.add(97);
		
		for (int i = 1; i < 100; i++) {
			temp = temp.add(BigInteger.ONE);
			
			if (ints.contains(i)) {
				continue;
			}

			t = new FactorThread(temp, primeFinder, logger, i);

			pool.submit(t);

		}

	}

	private class FactorThread implements Runnable {
		BigInteger number;
		PrimeFinder primeFinder;
		int i;
		Logger logger;

		FactorThread(BigInteger number, PrimeFinder primeFinder, Logger logger, int i) {
			this.number = number;
			this.primeFinder = primeFinder;
			this.i = i;
			this.logger = logger;
		}

		@Override
		public void run() {
			String num = number.toString();
			Tuple tuple = factor(number, primeFinder);

			if (tuple == null) {
				logger.logPerAddedNumber(false, tuple, i);
			} else {
				logger.logPerAddedNumber(true, tuple, i);

				System.out.println("Answer for " + num + ":");
				ArrayList<String> als = tuple.getFactors();
				for (int q = 0; q < als.size(); q++) {
					System.out.print(" * " + als.get(q));
				}
			}

		}

	}

	private Tuple factor(BigInteger N, PrimeFinder primeFinder) {
		Tuple tuple = new Tuple(N, new ArrayList<String>(), primeFinder);

		if (tuple.isDone()) {
			return tuple;
		}

		// first try trial division long start = System.currentTimeMillis();
		System.out.println("Initiating trial division");
		long start = System.currentTimeMillis();
		SmallFactorizer smallFactors = new SmallFactorizer(primeFinder);
		smallFactors.trialDivision(tuple);
		long end = System.currentTimeMillis() - start;
		long minutes = end / (1000 * 60);
		System.out.println("Trial division was performed in " + minutes + " minutes");

		// run pollard for POLLARD_MAX_MINUTES. If factor is found, run again.
		// Repeat until no more factor is found
		System.out.println("Initiating pollard-rho");
		Pollard pollard = new Pollard();
		boolean foundFactor = true;
		while (!tuple.isDone() && foundFactor) {
			start = System.currentTimeMillis();
			foundFactor = pollard.pollardRho(tuple, POLLARD_MAX_MINUTES);
			end = System.currentTimeMillis() - start;
			minutes = end / (1000 * 60);

			if (foundFactor) {
				System.out.println("Factor " + tuple.getFactors().get(tuple.getFactors().size() - 1)
						+ " found with Pollard-Rho in " + minutes + " minutes");
			}

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

}
