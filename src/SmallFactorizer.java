import java.math.BigInteger;

public class SmallFactorizer {
	StringBuilder smallFactors = null;
	BigInteger residue = null;
	long maxNumber = 0;
	PrimeFinder primeFinder;
	
	public SmallFactorizer(PrimeFinder primeFinder) {
		this.primeFinder = primeFinder;
	}

	/**
	 * Trial Division
	 * 
	 * @param N
	 * @param maxNumber
	 */
	public void trialDivision(Tuple tuple) {
		residue = tuple.getNumber();
		this.maxNumber = maxNumber;
		
		long[] primes = primeFinder.getPrimes();
		
		long prime;
		
		BigInteger[] divideResidue;

		for (int i = 0; i < primes.length; i++) {
			prime = primes[i];
			divideResidue = residue
					.divideAndRemainder(new BigInteger("" + prime));
			if (divideResidue[1] == BigInteger.ZERO) {
				residue = divideResidue[0];
				tuple.addFactor(prime);
				i--;
			}
		}
		tuple.setNumber(residue);
	}

	public String getSmallFactors(String factorString) {
		return smallFactors.toString();
	}

}
