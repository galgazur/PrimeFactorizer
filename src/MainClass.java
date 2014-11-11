import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainClass {

	private final int ZERO_AMOUNT = 1;

	private final int trialDivisionMax = 10000;

	public MainClass() {
		String andreas = "9103090198";
		String niklas = "9204123476";

		for (int i = 0; i < ZERO_AMOUNT; i++) {
			andreas += "0";
			niklas += "0";
		}

		BigInteger andreasBase = new BigInteger(andreas);
		BigInteger niklasBase = new BigInteger(niklas);

		QuadraticSieve qSieve = new QuadraticSieve();

		PrimeFinder p = new PrimeFinder();
		BigInteger temp = null;
		String result = "";

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
		Date date = new Date();
		System.out.println(); // 2014/08/06 15:59:48
		String time = dateFormat.format(date);

		for (int i = 1; i <= 100; i++) {
			for (int j = 0; j < 2; j++) {
				if (j == 0) {
					temp = andreasBase.add(new BigInteger("" + i));
				} else {
					temp = niklasBase.add(new BigInteger("" + i));
				}

				// result = qSieve.factorize(p, temp);

				Logger.log(temp.toString(), result, time);
			}
		}
	}

	private void factor(BigInteger N) {
		Tuple tuple = new Tuple(N, new ArrayList<String>());

		// ArrayList<String> factors = new ArrayList<String>();

		// first try trial division
		SmallFactorizer smallFactors = new SmallFactorizer();

		smallFactors.trialDivision(tuple, trialDivisionMax);

		// pollard-rho
		Pollard pollard = new Pollard();

		pollard.pollardRho(tuple);

	}

	public static void main(String[] args) {
		new MainClass();

	}

}
