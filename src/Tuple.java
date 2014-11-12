import java.math.BigInteger;
import java.util.ArrayList;

public class Tuple {

	private BigInteger number;
	private ArrayList<String> factors;

	public Tuple(BigInteger number, ArrayList<String> factors) {
		this.number = number;
		this.factors = factors;
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
		for (int i = 0; i < factors.size(); i++) {
			System.out.println(factors.get(i));
		}
	}

	public boolean isDone() {
		if (number.compareTo(BigInteger.ONE) == 0) {
			return true;
		} else {
			return false;
		}
	}

}
