import java.util.ArrayList;

public class SmoothnessChecker {
	long residue = 0l;
	long maxNumber = 0;
	ArrayList<Long> factors = new ArrayList<Long>();
	/**
	 * Trial Division
	 * @param N
	 * @param maxNumber
	 */
	public boolean factorizeSmallNumbers(long N, long[] primes){
		residue = N;
		
		for (int i = 2; i < primes.length; i++) {
			long divideResidue = residue % primes[i];
			if(divideResidue==0){
				residue = residue / primes[i];
				factors.add(primes[i]);
				i--;
			}
		}
		return (residue==1);
	}
	
	public ArrayList<Long> getFactors(){
		return factors;
	}
}
