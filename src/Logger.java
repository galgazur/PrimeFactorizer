import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;




public class Logger {
	
	private class PrimePair {
		private int amount;
		private BigInteger prime;
		
		private PrimePair(int amount, BigInteger prime) {
			this.amount = amount;
			this.prime = prime;
		}
	}
	
	public static void log(Tuple tuple, PrintWriter out){
//		String[] rows = str.split("\n");
		
		BigInteger N = tuple.getNumber();
		ArrayList<String> factors = tuple.getFactors();
		
		ArrayList<BigInteger> primes = new ArrayList<BigInteger>();
		
		for (int i = 0; i < factors.size(); i++) {
			primes.add(new BigInteger(factors.get(i).toString()));
		}
		
		Collections.sort(primes);
		
		BigInteger comparePrime = primes.get(0);
		
		for (int i = 1; i < primes.size(); i++) {
			
		}
		
		try {
			


			
			out.println("Prime factorization of N: " + N);
			out.println("--------------------------------------");
			for (int i = 0; i < rows.length; i++) {
				out.println(rows[i]);
			}
			out.println("");
			out.println("");
			
			out.close();
		}catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}
		
		
	}
}
