import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;


public class QuadraticSieve {
	long[] times = new long[10];
	
	long qxFindTimeout = 5000;
	long tComboTimeout = 5000;
	
	long timeouts = 100;
	
	long tempTime1 = 0;
	long tempTime2 = 0;
	
	
	private SmoothnessChecker smoothness = null;
	long[] factorBase = null;
	
	public int linearIndependenceLimit;
	
	long bitCombo = 0;
	BigInteger m;
	BigInteger N;
	
	int eVectorCount = 0;
	
	/**
	 * returns a string representation for all primes and the amount of occurrences
	 * @return
	 */
	public BigInteger factorize(long SmoothnessBound, int linearIndependenceLimit, PrimeFinder firstXprimes, BigInteger N, long timeouts){
		tComboTimeout = timeouts;
		qxFindTimeout = timeouts;
		this.linearIndependenceLimit = linearIndependenceLimit;
		tempTime1 = System.nanoTime();
		smoothness = new SmoothnessChecker();
		this.N = N;
		tempTime2 = System.nanoTime();
		times[0] += tempTime2-tempTime1;
		tempTime1 = System.nanoTime();
		
		long[] primeArray = firstXprimes.getPrimes();
		factorBase = getFactorBase(primeArray, N, SmoothnessBound);
		
		tempTime2 = System.nanoTime();
		times[1] += tempTime2-tempTime1;
		tempTime1 = System.nanoTime();
		
		m = sqrt(N);
		eVectorCount = factorBase.length+1;
		
		long[][] qxEVectorsArray = new long[eVectorCount][factorBase.length+1]; //contains e-vectors for each q(x)
		
		tempTime2 = System.nanoTime();
		times[2] += tempTime2-tempTime1;
		tempTime1 = System.nanoTime();
		System.out.println("Instantiated qxVector.");
		int qxPointer = 0;
		
		long time = System.currentTimeMillis();
		
		for(long x = 0; x<Long.MAX_VALUE-5; x++){
			//try different x 
			if(x%1000==0){
				//System.out.println("x: " + x);
				if(System.currentTimeMillis()-time>qxFindTimeout){
					x = Long.MAX_VALUE-5;
					System.out.println("Q(x) find process timed out.");
				}
			}
			for (long i = 1; i >=-1; i-=2) {
				if(x==0){
					i=-1;
				}
				long[] factorization = smoothnessAndExponentVectors(i*x, factorBase);	//first byte is not part of the vector, it contains the x
				if(factorization!=null){
					//System.out.println("qx: " + qxPointer);
					qxEVectorsArray[qxPointer] = factorization;
					qxEVectorsArray[qxPointer][0] = i*x;
					qxPointer++;
					if(qxPointer==eVectorCount){
						x=Long.MAX_VALUE-5;
						i = -2;
					}
				}
			}
		}
		System.out.println("Found q(x)'s");
		tempTime2 = System.nanoTime();
		times[3] += tempTime2-tempTime1;
		tempTime1 = System.nanoTime();
		
		BigInteger aAns = null;
		BigInteger bAns = null;
		
		bitCombo = (long)Math.pow((double)2, (double)qxEVectorsArray.length);
		long tLong = findTCombination(qxEVectorsArray, 0);
		System.out.println("Found first T-combo");
		tempTime2 = System.nanoTime();
		times[4] += tempTime2-tempTime1;
		tempTime1 = System.nanoTime();
		System.out.println("");
		
		time = System.currentTimeMillis();

		while(tLong < bitCombo){
			
			boolean[] T = makeValidCombo(tLong, qxEVectorsArray.length);
			if(tLong%1000==0){
				//System.out.println("x: " + x);
				if(System.currentTimeMillis()-time>tComboTimeout){
					tLong = Long.MAX_VALUE-5;
					System.out.println("T-combo find process timed out.");
				}
			}
			aAns = getA(T, qxEVectorsArray);
			bAns = getB(T, qxEVectorsArray).negate().mod(N);
			
			if( aAns.compareTo(bAns)!=0&&(!(aAns.equals(BigInteger.ZERO)))&&(!(bAns.equals(BigInteger.ZERO)))){
				break;
			}
			tLong = findTCombination(qxEVectorsArray, tLong);
				
		}
		
		tempTime2 = System.nanoTime();
		times[5] += tempTime2-tempTime1;
		
		
		System.out.println(times[0]/1000 + " - time to instantiate SmoothnessChecker");
		System.out.println(times[1]/1000 + " - time to get Factorbase");
		System.out.println(times[2]/1000 + " - time to instantiate qxVectorArray");
		System.out.println(times[3]/1000 + " - time to find q(x)'s");
		System.out.println(times[4]/1000 + " - time to find first T-combo");
		System.out.println(times[5]/1000 + " - time to find final T-combo");
		System.out.println("");
		
		if(tLong > bitCombo){
			return null;
		}
		
		if(aAns==null){
			return null;
		}else{
			//MAKE OUR OWN GCD!!!
			BigInteger b = gcd(aAns.add(bAns), N);
			return b;
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
	private long[] getFactorBase(long[] primes, BigInteger N, long SmoothnessBound){
		ArrayList<Long> factorBase = new ArrayList<Long>();
		factorBase.add((long)-1);
		for(long p : primes){
			if(isQuadraticResidue(p, N)){
				if(p>=SmoothnessBound){
					break;
				}
				factorBase.add(p);
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
	private boolean isQuadraticResidue(long p, BigInteger N){		//BigInteger N){
		long q = N.mod(new BigInteger(p+"")).longValue();
		long mod = 0;
		for (long i = 0; i < p; i++) {
			mod = (i*i)%p;
			if(mod==q){
				return true;
			}
		};
		return false;
	}
	
	/**
	 * Factorize a given number x and determine if it is smooth with regards to the factorbase.
	 * return an array of factors if it is, and null if not
	 * @param qX
	 * @param factorBase
	 * @return
	 */
	private long[] smoothnessAndExponentVectors(long x, long[] factorBase){
		long[] eVector = new long[factorBase.length+1];
		long factor = 0l;
		long factorBs = 0l;
		
		if(x==4){
			//System.out.println();
		}
		
		long qX = (m.add(new BigInteger(""+x))).pow(2).subtract(N).longValue();
		//System.out.println("Q(" + x + ") = " + qX );
		boolean isSmooth = smoothness.factorizeSmallNumbers(qX, factorBase);;
		if(isSmooth){
			//
			ArrayList<Long> factors = smoothness.getFactors();	// 7 11 13 19 19 19 23 37 47
			if(factors.size()==0){
				return null;
			}
			for (int i = 0; i < factorBase.length; i++) {		//5 7 11 13 17 19 23 29 31 47 53
				factor = factors.get(0);
				factorBs = factorBase[i];
				
				if(factor==factorBs){
					factors.remove(0);
					eVector[i+1]++;
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
		//for (int j3 = 1; j3 < qxFactorArray.length; j3++) {
			for (long i = start+1; i < bitCombo; i++) {
//				if(i==100){
//					System.out.println(Integer.toString((100 >> 0)&1,2));
//					System.out.println(Integer.toString((100 >> 1)&1,2));
//					System.out.println(Integer.toString((100 >> 2)&1,2));
//					System.out.println(Integer.toString((100 >> 3)&1,2));
//					System.out.println(Integer.toString((100 >> 4)&1,2));
//					System.out.println(Integer.toString((100 >> 5)&1,2));
//					System.out.println(Integer.toString((100 >> 6)&1,2));
//					System.out.println(Integer.toString((100 >> 7)&1,2));
//				}
				isValidCombo = true;
				int isOk = 0; //denotes if the following eVector should be included or if it includes too many eVectors, or includes j3
				for (int j = 0; j < factorBase.length; j++) { 
					isOk += (i >> j)&1;
				}
				
				//boolean x = ((i >> j3)&1)==0;
				if((isOk<=linearIndependenceLimit)){//&&(x)){
					for (int j = 1; j < factorBase.length+1; j++) {		//for every bit
						bitSum = 0;//qxFactorArray[j3][j];
						for (int j2 = 0; j2 < qxFactorArray.length; j2++) {		//for every x
							boolean xHasBit = (qxFactorArray[j2][j]%2==1);
							boolean xIsInT = (((i >> j2)&1)==1);
							if(xHasBit&&xIsInT){
								bitSum++;
							}
						}
						if(bitSum%2!=0){
							j=factorBase.length;
							isValidCombo = false;
						}
					}
				}
				if(isValidCombo){
					return i;
				}
			}
		//}
		return Long.MAX_VALUE;
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
			iVector[i] = ((num>>i)&1)==1;
		}
		return iVector;
	} 
	
	/**
	 * retrieve a boolean array with corresponding index set to true
	 * @param num
	 * @param bits
	 * @return
	 */
	public static boolean[] makeValidCombo2(long num, int count, int max){
		boolean[] iVector = new boolean[max];
		
		long[] x = new long[count];
		for (long i = 0; i<count; i++) {
			long var = num;
			
			for(int z = 0;z<i ;z++ ){
				var -= x[z];
			}
			for (long j = (max-count+i); j < max; j++) {
				var = var/j;
			}
			var = var%(max-count+i);
			x[(int)i] = var;
		}

		System.out.print("[" + max + " = ");
		for (int i = 0; i < x.length; i++) {
			System.out.print(" " + x[i]);
		}
		System.out.println("]");
//		
//		
//		int max = 13*12*11;
//		int a = 0;
//		int b = 0;
//		int c = 0;
//		for (int i = 0; i < max; i++) {
//			a = (i)%11;
//			b = ((i-a)/11)%12;
//			c = ((i-a-b)/(11*12))%13;
//			System.out.println("[" + i + " = " + c + ", " + b + ", " + a + "]");
//		}
		

		return iVector;
	} 
	
	/**
	 * Get the Y value from the given combination of Q(x)
	 * @param T
	 * @return
	 */
	private BigInteger getB(boolean[] T, long[][] qxEVectors){
		BigInteger product = BigInteger.ONE;
		int negatives = -1;
		for (int i = 0; i < T.length; i++) {
			if(T[i]){
				product = product.multiply(getBi(new BigInteger(qxEVectors[i][0]+"")));
				if(qxEVectors[i][0]<=0){
					negatives = negatives*(-1);
				}
			}
		
		}
		product = (new BigInteger(""+negatives)).multiply(sqrt(product)).mod(N);
		return product;
	}
	
	/**
	 * Get the X value from the given combination of Q(x)
	 * @param T
	 * @return
	 */
	private BigInteger getA(boolean[] T, long[][] qxEVectors){
		BigInteger product = BigInteger.ONE;
		for (int i = 0; i < T.length; i++) {
			if(T[i]){
				product = product.multiply(getAi(new BigInteger(qxEVectors[i][0]+"")));
			}
		}
		product = product.mod(N);
		return product;
	}

	
	private BigInteger getAi(BigInteger x){
		return (x.add(m));
	}
	
	private BigInteger getBi(BigInteger x){
		return (x.add(m).pow(2).subtract(N));
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
