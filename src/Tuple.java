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

}
