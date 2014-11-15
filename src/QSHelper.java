import java.math.BigInteger;
import java.util.ArrayList;


public class QSHelper {
	
	
	public QuadraticSieve qs = null;
	public PrimeFinder primeFinder = null;
	int state = 0;
	
	public QSHelper(PrimeFinder primeFinder){
		qs = new QuadraticSieve();
		this.primeFinder = primeFinder;
	}
	
	public Tuple factorize(BigInteger N){
		ArrayList<String> extractedPrimes = new ArrayList<String>();
		ArrayList<BigInteger> extractedNumbers = new ArrayList<BigInteger>();
		
		BigInteger factor1 = null;
		BigInteger factor2 = null;
		
		int linearIndependenceLimit = 10;
		long bLimit = 1000;
		long B = 100;//100000000l;
		long timeouts = 5000;
		
		
		if(primeFinder.isPrime(N)){
			extractedPrimes.add(N.toString());
			return new Tuple(BigInteger.ONE, (extractedPrimes), primeFinder);
		}
		
		
		boolean t = true;
		//The main loop
		extractedNumbers.add(N);
		do{
			N = extractedNumbers.remove(0);
			do{
				factor1 = qs.factorize(B, linearIndependenceLimit, primeFinder, N, timeouts);
				if((factor1==null)||factor1.toString().equalsIgnoreCase("1")||factor1.toString().equalsIgnoreCase(N.toString())){
					factor1 = null;
					//UPDATES Linear Independence limit and B
					if(state==0){
						System.out.println("Failed to factorize. Doubling B... (B="+B+")");
						B = B+1;
						if(B == bLimit){
							B = 100;
						}
						state = 1;
					}else if(state==1){
						System.out.println("Failed to factorize. Increasing dependence limit...(dl="+linearIndependenceLimit+")");
						linearIndependenceLimit++;
						if(linearIndependenceLimit==15){
							linearIndependenceLimit=3;
						}
						state = 2;
					}else{
						System.out.println("Failed to factorize. Increasing timeout limit... (t="+(timeouts/1000)+"."+(timeouts%1000)/100+")");
						timeouts += 400;
						state = 0;
					}
//					else if(state==2){
//						System.out.println("Failed to factorize. Splitting and incrementing B...(B="+B+")");
//						B = (long)(Math.sqrt(B) + 1);
//						state = 0;
//					}
				}
			}while(factor1==null);
				
			factor2 = N.divide(factor1);
			
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
		}while(extractedNumbers.size()>0);	//until we only have prime factors left
		
		return new Tuple(BigInteger.ONE, (extractedPrimes), primeFinder);
	}
}
