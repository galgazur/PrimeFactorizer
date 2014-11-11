import java.math.BigInteger;

public class SmallFactorizer {
	StringBuilder smallFactors = null;
	BigInteger residue = null;
	long maxNumber = 0;

	/**
	 * Trial Division
	 * 
	 * @param N
	 * @param maxNumber
	 */
	public void trialDivision(Tuple tuple, int maxNumber) {
		residue = tuple.getNumber();
		this.maxNumber = maxNumber;

		for (int i = 2; i < maxNumber; i++) {
			BigInteger[] divideResidue = residue
					.divideAndRemainder(new BigInteger("" + i));
			if (divideResidue[1] == BigInteger.ZERO) {
				residue = divideResidue[0];
				tuple.addFactor(i);
				i--;
			}
		}
		tuple.setNumber(residue);
	}

	public String getSmallFactors(String factorString) {
		return smallFactors.toString();
	}

}
