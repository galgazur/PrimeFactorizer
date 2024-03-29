import java.math.BigInteger;

public class Pollard {

	private static BigInteger ONE = new BigInteger("1");

	public Pollard() {

		// BigInteger N = new BigInteger("24");

		// BigInteger divisor = pollardRho();
		// System.out.println("" + divisor);// gcd.toString();
		// System.out.println(new String(gcd.toByteArray()));
	}

	// public static void main(String[] args) {
	// new Pollard();
	// }

	public boolean pollardRho(Tuple tuple, int minutes) {
		BigInteger N = tuple.getNumber();
		BigInteger ret = null;

		BigInteger X = Calc.generateRandomBigInteger(N);

		BigInteger fx = null;
		BigInteger gcd = null;

		long start = System.currentTimeMillis();
		long end = start + minutes*60*1000;
		long currentTime;
		while (true) {
			// if took too long
			if ((currentTime = System.currentTimeMillis()) >= end) {
				System.out.println("Pollard-Rho timed out");
				return false;
			}
			fx = Calc.square(X).add(ONE).mod(N);

			gcd = Calc.gcd(fx.subtract(X), N);

			if (gcd.compareTo(ONE) > 0) {
				// gcd is a factor of N
				ret = gcd;
				break;
			} else if (gcd.compareTo(N) == 0) {
				// re-initiate random X
				X = Calc.generateRandomBigInteger(N);
			} else {
				X = fx;
			}

		}

		BigInteger[] dividerAndResidue = N.divideAndRemainder(ret);

		tuple.setNumber(dividerAndResidue[0]);

		tuple.addFactor(ret);

		return true;
	}

}
