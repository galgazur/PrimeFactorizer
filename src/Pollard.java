import java.math.BigInteger;

public class Pollard {

	public long Pollard(BigInteger N) {
		long ret = 0;

		// let x0 ∈U ZN
		// define the sequence {xi}i≥1 by xi+1 ≡ x2
		// i + 1 mod N
		// for i := 1, 2,... do
		// d := gcd(x2i − xi, N)
		// if d > 1 then
		// d is a non-trivial factor in N, stop

		return ret;
	}

	private Pollard(BigInteger A, BigInteger B) {
		BigInteger gcd = Euclides(A, B);

		System.out.println("" + gcd);// gcd.toString();
		// System.out.println(new String(gcd.toByteArray()));
	}

	public static void main(String[] args) {
		BigInteger A = new BigInteger("8");
		BigInteger B = new BigInteger("24");

		new Pollard(A, B);
	}

	private BigInteger Euclides(BigInteger A, BigInteger B) {
		if (A.compareTo(B) == -1) {
			BigInteger tmp = A;
			A = B;
			B = tmp;
		}

		// BigInteger[] divided = null;
		BigInteger tmp = null;
		while (B.signum() != 0) {
			tmp = A.divideAndRemainder(B)[1];
			A = B;
			B = tmp;
		}

		return A;
	}

}
