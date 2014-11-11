import java.math.BigInteger;
import java.math.*;
import java.util.ArrayList;
import java.util.Iterator;


public class QuadraticSieve {
	private SmoothnessChecker smoothness = null;
	long[] factorBase = null;
	public static int linearIndependenceLimit = 5; 
	long bitCombo = 0;
	
	/**
	 * returns a string representation for all primes and the amount of occurrences
	 * @return
	 */
	public String factorize(long SmoothnessBound, PrimeFinder firstXprimes, BigInteger N){
		smoothness = new SmoothnessChecker();
		
		firstXprimes.findPrimes(SmoothnessBound);
		long[] primeArray = firstXprimes.getPrimes();
		factorBase = getFactorBase(primeArray, N);
		
		BigInteger m = sqrt(N);
		
		
		long[][] qxEVectorsArray = new long[2*factorBase.length][]; //contains e-vectors for each q(x)
		int qxPointer = 0;
		
		for(long x = 0; x<Long.MAX_VALUE; x++){
			//try different x 
			
			long[] factorization = smoothnessAndExponentVectors(x, factorBase);
			if(factorization!=null){
				qxEVectorsArray[qxPointer] = factorization;
				qxPointer++;
			}
		}
		
		BigInteger xAns = null;
		BigInteger yAns = null;
		
		bitCombo = (long)Math.pow((double)2, (double)qxEVectorsArray.length);
		long tLong = findTCombination(qxEVectorsArray, 0);
		boolean[] T = makeValidCombo(tLong, qxEVectorsArray.length);

		
		while(tLong < bitCombo){
			
			xAns = getX(T);
			yAns = getY(T);
			
			if( xAns.compareTo(yAns.mod(N))!=0){
				break;
			}
				
		}
		if(tLong > bitCombo){
			return null;
		}
		
		if(xAns==null){
			return null;
		}else{
			//MAKE OUR OWN GCD!!!
			return "" + gcd(xAns.subtract(yAns), N);
		}
	}
	
	public static long[] convertLongs(ArrayList<Long> longs)
	{
	    long[] ret = new long[longs.size()];
	    Iterator<Long> iterator = longs.iterator();
	    for (int i = 0; i < ret.length; i++)
	    {
	        ret[i] = iterator.next().intValue();
	    }
	    return ret;
	}
	
	/**
	 * 
	 * @param primes
	 * @param N
	 * @return
	 */
	private long[] getFactorBase(long[] primes, BigInteger N){
		ArrayList<Long> factorBase = new ArrayList<Long>();
		long[] h = new long[40];
		
		factorBase.add((long)-1);
		for(long a : primes){
			if(isQuadraticResidue(a, N)){
				factorBase.add(a);
			}
		}
		long[] result = convertLongs(factorBase);
		return result;
	}
	
	/**
	 * Determine if a factor is a 'quadratic residue'
	 * @param a
	 * @param N
	 * @return
	 */
	private boolean isQuadraticResidue(long a, long p){		//BigInteger N){
		for (long i = 0; i < p; i++) {
			if((i^2)%p==a){
				return true;
			}
		};
		return false;
	}
	
	/**
	 * Factorize a given number x and determine if it is smooth with regards to the factorbase.
	 * return an array of factors if it is, and null if not
	 * @param x
	 * @param factorBase
	 * @return
	 */
	private long[] smoothnessAndExponentVectors(long x, long[] factorBase){
		long[] eVector = new long[factorBase.length];
		long factor = 0l;
		long factorBs = 0l;
		boolean isSmooth = smoothness.factorizeSmallNumbers(x, factorBase);;
		if(isSmooth){
			ArrayList<Long> factors = smoothness.getFactors();	// 7 11 13 19 19 19 23 37 47
			for (int i = 0; i < factorBase.length; i++) {		//5 7 11 13 17 19 23 29 31 47 53
				factor = factors.get(0);
				factorBs = factors.get(i);
				
				if(factor==factorBs){
					factors.remove(i);
					eVector[i]++;
					i--;
				}
				if(factors.size()==0){
					break;
				}
			}
			
			return eVector;
		}else{
			return null;
		}
	}
	
	/**
	 * Retrieve biggest number from factorBase
	 * @param factorBase
	 * @return
	 */
	private long getMax(long[] factorBase){
		long max = 0;
		for (int i = 0; i < factorBase.length; i++) {
			if(factorBase[i]>max)
				max = factorBase[i];
		}
		return max;
	}
	
	/**
	 * Given all exponentVectors, find a linearly dependant combination of vectors
	 * @param qxFactorArray
	 * @param start
	 * @return
	 */
	private long findTCombination(long[][] qxFactorArray, long start){
		boolean isValidCombo = true;
		long bitSum = 0;
		for (int j3 = 0; j3 < qxFactorArray.length; j3++) {
			for (long i = start; i < bitCombo; i++) {
				
				isValidCombo = true;
				int isOk = 0; //denotes if the following eVector should be included or if it includes too many eVectors, or includes j3
				for (int j = 0; j < factorBase.length; j++) {
					isOk += (i >> j)&1;
				}
				
				boolean x = ((i >> j3)&1)==0;
				if((isOk<=linearIndependenceLimit)&&(x)){
					for (int j = 0; j < factorBase.length; j++) {
						bitSum = qxFactorArray[j3][j];
						for (int j2 = 0; j2 < qxFactorArray.length; j2++) {
							if(qxFactorArray[j2][j]>0){
								bitSum--;
							}
						}
						if(bitSum!=0){
							j=factorBase.length;
							isValidCombo = false;
						}
					}
				}
				if(isValidCombo){
					return i;
				}
			}
		}
		return 0;
	}
	
	/**
	 * retrieve a boolean array with corresponding index set to true
	 * @param num
	 * @param bits
	 * @return
	 */
	private boolean[] makeValidCombo(long num, int bits){
		boolean[] iVector = new boolean[bits];
		for (int i = 0; i < bits; i++) {
			iVector[i] = ((num<<i)&1)==1;
		}
		return iVector;
	} 
	
	/**
	 * Get the Y value from the given combination of Q(x)
	 * @param T
	 * @return
	 */
	private BigInteger getY(boolean[] T){
		return null;
	}
	
	/**
	 * Get the X value from the given combination of Q(x)
	 * @param T
	 * @return
	 */
	private BigInteger getX(boolean[] T){
		return null;
	}
	
	
	
	/**
	 * NOT WRITTEN BY US!!!
	 * THIS IS THE NATIVE JAVA GCD FOR BIGINTEGER!
	 * @param g
	 * @param N
	 * @return
	 */
	private BigInteger gcd(BigInteger g, BigInteger N){
		return g.gcd(N);
	}
	
	
	
	/**
	 * NOT WRITTEN BY US!!!
	 * THIS WAS WRITTEN BY Faruk Akgul, August 22, 2009.
	 * http://faruk.akgul.org/blog/javas-missing-algorithm-biginteger-sqrt/
	 * 
	 * @param n
	 * @return
	 */
	private BigInteger sqrt(BigInteger n) {
		  BigInteger a = BigInteger.ONE;
		  BigInteger b = new BigInteger(n.shiftRight(5).add(new BigInteger("8")).toString());
		  while(b.compareTo(a) >= 0) {
		    BigInteger mid = new BigInteger(a.add(b).shiftRight(1).toString());
		    if(mid.multiply(mid).compareTo(n) > 0) b = mid.subtract(BigInteger.ONE);
		    else a = mid.add(BigInteger.ONE);
		  }
		  return a.subtract(BigInteger.ONE);
	}
}
