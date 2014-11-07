
public class PrimeFinder {
	
	private static final int PRIME_GUESS_THRESHOLD = 1000;
	private long[] primes;
	private long previousbound;
	
	/**
	 * Find primes smaller than this bound
	 * @param bound
	 * @return
	 */
	public void findPrimes(long bound){
		if(bound>previousbound){
			long[] results = new long[Integer.MAX_VALUE-5];
			int resultPointer = 0;
			
			for (long i = 2; i < bound; i++) {
				if(isPrime(i)){
					results[resultPointer] = i;
					resultPointer++;
					break;
				}
			}
			
			primes = new long[resultPointer];
			for (int i = 0; i < resultPointer; i++) {
				primes[i] = results[i];
			}
			previousbound = bound;
		}
	}
	
	/**
	 * return stored primes list
	 * @return
	 */
	public long[] getPrimes(){
		return primes;
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
	
}
