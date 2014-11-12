import java.math.BigInteger;
import java.util.ArrayList;

public class Tuple {

	private BigInteger number;
	private ArrayList<String> factors;
	private PrimeFinder primeFinder;
	private BigInteger startNumber;
	
	private ArrayList<BigInteger> primes;				// TODO

	public Tuple(BigInteger number, ArrayList<String> factors, PrimeFinder primeFinder) {
		this.number = number;
		this.factors = factors;
		this.primeFinder = primeFinder;
		this.startNumber = number;

	}

	public void addFactor(long N) {
		factors.add("" + N);
	}

	public void addFactor(BigInteger N) {
		factors.add(N.toString());
	}

	public void setNumber(BigInteger N) {
		this.number = N;
	}

	public BigInteger getNumber() {
		return number;
	}

	public ArrayList<String> getFactors() {
		return factors;
	}

	public void printFactors() {
		System.out.println(startNumber.toString());
		System.out.println();
		
		for (int i = 0; i < factors.size(); i++) {
			System.out.println(factors.get(i));
		}
	}

	public boolean isDone() {
		if (number.compareTo(BigInteger.ONE) == 0) {
			return true;
		} else if (primeFinder.isPrime(number)) {
			addFactor(number);
			setNumber(new BigInteger("1"));
			return true;
		} else {

			return false;
		}
	}
}
