import java.math.BigInteger;

public class Pollard {

	private static BigInteger ONE = new BigInteger("1");

	private Pollard() {
		BigInteger N = new BigInteger("24");

		BigInteger divisor = pollardRho(N);
		System.out.println("" + divisor);// gcd.toString();
		// System.out.println(new String(gcd.toByteArray()));
	}

	public static void main(String[] args) {
		new Pollard();
	}

	public BigInteger pollardRho(BigInteger N) {
		BigInteger ret = null;

		BigInteger X = Calc.generateRandomBigInteger(N);

		BigInteger fx = null;
		BigInteger gcd = null;

		while (true) {

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

		return ret;
	}

}
