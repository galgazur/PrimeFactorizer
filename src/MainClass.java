import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainClass {

	private final int ZERO_AMOUNT = 60;

	private final int j = 0;

	private int BASE = 0;

	// private final int trialDivisionMax = 100000000;
	private final int trialDivisionMax = Integer.MAX_VALUE;

	private static void makePrimeFile() {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(
					"primes.txt", true)));
			
			PrimeFinder primeFinder = new PrimeFinder();
			
			primeFinder.findPrimes2(out);

//			long[] primes = primeFinder.getPrimes();
			
			out.close();
			
//			for (int i = 0; i < primes.length; i++) {
//				out.println(primes[i]);
//				out.flush();
//			}

			

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public MainClass() {
		String andreas = "9204123476";
		String niklas = "9103090198";

		String andreasNumber = andreas;
		String niklasNumber = niklas;

		for (int i = 0; i < ZERO_AMOUNT + j; i++) {
			andreas += "0";
			niklas += "0";
		}

		BigInteger andreasBase = new BigInteger(andreas);
		BigInteger niklasBase = new BigInteger(niklas);

		// QuadraticSieve qSieve = new QuadraticSieve();

		BigInteger temp = null;

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
		Date date = new Date();
		// System.out.println(); // 2014/08/06 15:59:48
		String time = dateFormat.format(date);

		temp = andreasBase.add(new BigInteger("2"));
		temp = new BigInteger("9103090198011");

		// System.out.println(temp.toString());

		PrimeFinder primeFinder = new PrimeFinder();
		primeFinder.findPrimes(trialDivisionMax);

		// factor(temp, primeFinder);

		Logger logger = new Logger();

		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(
					"factorization-" + time + ".txt", true)));

			if (BASE == 0) {
				out.println(andreasNumber + " " + j);
			} else {
				out.println(niklasNumber + " " + j);
			}

			out.flush();

		} catch (IOException e) {

			e.printStackTrace();
		}

		// long start = System.currentTimeMillis();

		Tuple tuple;
		for (int i = 0; i < 10; i++) {
			temp = temp.add(BigInteger.ONE);
			tuple = factor(temp, primeFinder);
			logger.log(tuple, out);
			out.flush();
		}

		// long elapsed = System.currentTimeMillis() - start;

		// float elapsedMin = elapsed/(60 * 1000F);

		// System.out.println(elapsedMin);

		// long tmp = (long) elapsedMin;

		// long difference = elapsedMin - (float)tmp;

		out.close();
	}

	private Tuple factor(BigInteger N, PrimeFinder primeFinder) {
		Tuple tuple = new Tuple(N, new ArrayList<String>(), primeFinder);

		if (tuple.isDone()) {
			// tuple.printFactors();
			return tuple;
		}

		// ArrayList<String> factors = new ArrayList<String>();

		// first try trial division
		SmallFactorizer smallFactors = new SmallFactorizer(primeFinder);

		smallFactors.trialDivision(tuple);

		// pollard-rho
		Pollard pollard = new Pollard();

		while (!tuple.isDone()) {

			pollard.pollardRho(tuple);

		}

		// if (tuple.isDone()) {
		// tuple.printFactors();
		// }

		return tuple;

	}

	public static void main(String[] args) {
//		new MainClass();
		makePrimeFile();
		

	}

}
