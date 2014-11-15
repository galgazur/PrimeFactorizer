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

//	private final int trialDivisionMax = 100000000;
	 private final int trialDivisionMax = 100000;

//	public MainClass() {
//		String andreas = "9204123476";
//		String niklas = "9103090198";
//		
//		String andreasNumber = andreas;
//		String niklasNumber = niklas;
//
//		for (int i = 0; i < ZERO_AMOUNT + j; i++) {
//			andreas += "0";
//			niklas += "0";
//		}
//
//		BigInteger andreasBase = new BigInteger(andreas);
//		BigInteger niklasBase = new BigInteger(niklas);
//
//		QuadraticSieve qSieve = new QuadraticSieve();
//
//		PrimeFinder p = new PrimeFinder();
//		BigInteger temp = null;
//		String result = "";
//
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
//		Date date = new Date();
//		// System.out.println(); // 2014/08/06 15:59:48
//		String time = dateFormat.format(date);
//
//		temp = andreasBase.add(new BigInteger("2"));
//		temp = new BigInteger("91030901982");
//
////		System.out.println(temp.toString());
//
//		PrimeFinder primeFinder = new PrimeFinder();
//		primeFinder.findPrimes(trialDivisionMax);
//
////		factor(temp, primeFinder);
//
//		Logger logger = new Logger();
//
//		PrintWriter out = null;
//		try {
//			out = new PrintWriter(new BufferedWriter(new FileWriter(
//					"factorization-" + time + ".txt", true)));
//
//			if (BASE == 0) {
//				out.println(andreasNumber + " " + j);
//			} else {
//				out.println(niklasNumber + " " + j);
//			}
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		// for (int i = 1; i <= 100; i++) {
//		// for (int j = 0; j < 2; j++) {
//		// if (j == 0) {
//		// temp = andreasBase.add(new BigInteger("" + i));
//		// } else {
//		// temp = niklasBase.add(new BigInteger("" + i));
//		// }
//
//		Tuple tuple = factor(temp, primeFinder);
//
//		// result = qSieve.factorize(p, temp);
//
//		logger.log(tuple, out);
//		// }
//		// }
//
//		out.close();
//	}
//
//	private Tuple factor(BigInteger N, PrimeFinder primeFinder) {
//		Tuple tuple = new Tuple(N, new ArrayList<String>(), primeFinder);
//
//		if (tuple.isDone()) {
////			tuple.printFactors();
//			return tuple;
//		}
//
//		// ArrayList<String> factors = new ArrayList<String>();
//
//		// first try trial division
//		SmallFactorizer smallFactors = new SmallFactorizer(primeFinder);
//
//		smallFactors.trialDivision(tuple);
//
//		// pollard-rho
//		Pollard pollard = new Pollard();
//
//		while (!tuple.isDone()) {
//
//			pollard.pollardRho(tuple);
//
//		}
//
////		if (tuple.isDone()) {
////			tuple.printFactors();
////		}
//
//		return tuple;
//
//	}
//
//	public static void main(String[] args) {
//		new MainClass();
//
//	}

		public MainClass() {
			for (int i = 0; i < (10*9*8); i++) {
				QuadraticSieve.makeValidCombo2(i, 3, 10);
			}
			
			
			String num = "9103090198000000000000000000000000000000000000000000000000000000000001";
			System.out.println("Start Factorizing: " + num);
			PrimeFinder p = new PrimeFinder();
			p.findPrimes(100000);
			QSHelper qs = new QSHelper(p);
			Tuple s = qs.factorize(new BigInteger(num));
			System.out.println("ANSWER:");
			ArrayList<String> als = s.getFactors();
			for (int i = 0; i < als.size(); i++) {
				System.out.print(" * " + als.get(i));
			}
		}

		public static void main(String[] args) {
			new MainClass();

		}
	 
}
