import java.math.BigInteger;


public class SmallFactorizer {
	StringBuilder smallFactors = null;
	BigInteger residue = null;
	long maxNumber = 0;

	/**
	 * Trial Division
	 * @param N
	 * @param maxNumber
	 */
	public void factorizeSmallNumbers(BigInteger N, long maxNumber){
		residue = N;
		this.maxNumber = maxNumber;
		
		for (long i = 2; i < maxNumber; i++) {
			BigInteger[] divideResidue = residue.divideAndRemainder(new BigInteger(""+i));
			if(divideResidue[1]==BigInteger.ZERO){
				residue = divideResidue[0];
				addSmallFactor(i);
				i--;
			}
		}
	}
	
	public BigInteger getUnfactored(String factorString){
		return residue;
	}
	
	public String getSmallFactors(String factorString){
		return smallFactors.toString();
	}
	
	private void addSmallFactor(long smallFactor){
		if(smallFactors == null){
			smallFactors = new StringBuilder();
			smallFactors.append(smallFactor);
		}else{
			smallFactors.append(" " + smallFactor);
		}
		
	}
}
