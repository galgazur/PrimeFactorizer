import java.math.BigInteger;


public class MainClass {

	
	
	
	public static void main(String[] args){
		String andreas = "9103090198";
		String niklas = "9204123476";
		
		for (int i = 0; i < 80; i++) {
			andreas+= "0";
			niklas+= "0";
		}
		
		BigInteger andreasBase = new BigInteger(andreas);
		BigInteger niklasBase = new BigInteger(niklas);
		
		QuadraticSieve qSieve = new QuadraticSieve();
		
		PrimeFinder p = new PrimeFinder();
		BigInteger temp = null;
		String result = "";
		
		
		for(int i = 1; i<=100; i++){
			for (int j = 0; j < 2; j++) {
				if(j==0){
					temp = andreasBase.add(new BigInteger(""+i));
				}else{
					temp = niklasBase.add(new BigInteger(""+i));
				}
				 
				result = qSieve.factorize(p,temp);
				
				Logger.log(result);	
			}
		}
		
	}
	
}
