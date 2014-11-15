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
		factors.clear();
		residue = N;
		if(residue<0){
			factors.add(-1l);
			residue = -residue;
		}
		for (int i = 1; i < primes.length; i++) {
			long divideResidue = residue % primes[i];
			
			if(primes[i]>residue){
				break;
			}
			
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
