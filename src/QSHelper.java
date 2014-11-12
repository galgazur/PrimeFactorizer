import java.math.BigInteger;
import java.util.ArrayList;


public class QSHelper {
	
	
	public QuadraticSieve qs = null;
	public PrimeFinder primeFinder = null;
	
	public QSHelper(PrimeFinder primeFinder){
		qs = new QuadraticSieve();
		this.primeFinder = primeFinder;
	}
	
	public Tuple factorize(BigInteger N){
		ArrayList<String> extractedPrimes = new ArrayList<String>();
		ArrayList<BigInteger> extractedNumbers = new ArrayList<BigInteger>();
		
		BigInteger factor1 = null;
		BigInteger factor2 = null;
		
		long B = 100000000l;
		
		
		if(primeFinder.isPrime(N)){
			extractedPrimes.add(N.toString());
			return new Tuple(BigInteger.ONE, (extractedPrimes));
		}
		
		//The main loop
		extractedNumbers.add(N);
		do{
			N = extractedNumbers.get(0);
			do{
				factor1 = qs.factorize(B, primeFinder, N);
				if(factor1==null){
					B = B*2;
				}
			}while(factor1==null);
				
			factor2 = N.divide(N);
			
			if(primeFinder.isPrime(factor1)){
				extractedPrimes.add(factor1.toString());
			}else{
				extractedNumbers.add(factor1);
			}
			
			if(primeFinder.isPrime(factor2)){
				extractedPrimes.add(factor2.toString());
			}else{
				extractedNumbers.add(factor2);
			}
		}while(extractedNumbers.size()==0);	//until we only have prime factors left
		
		return new Tuple(BigInteger.ONE, (extractedPrimes));
	}
}
