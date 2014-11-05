
public class PrimeFinder {
	
	private static final int PRIME_GUESS_THRESHOLD = 1000;
	
	/**
	 * Find primes smaller than this number
	 * @param bound
	 * @return
	 */
	public long[] findPrimes(long bound){
		long[] results = new long[Integer.MAX_VALUE-5];
		int resultPointer = 0;
		
		for (long i = 2; i < bound; i++) {
			if(isPrime(i)){
				results[resultPointer] = i;
				resultPointer++;
				break;
			}
		}
		
		return results;
	}
	
	public boolean isPrime(long primeCandidate){
		if(primeCandidate>PRIME_GUESS_THRESHOLD){
			return isProbablePrime(primeCandidate);	
		}else{
			return isDefinitePrime(primeCandidate);
		}
	}
	
	private boolean isProbablePrime(long primeCandidate){
		return true;
	}
	
	private boolean isDefinitePrime(long primeCandidate){
		return true;
	}
	
	public boolean isProbablePrime(BigInteger primeCandidate){
		
	}
}
