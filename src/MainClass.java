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
		
		Pollard pollard = new Pollard();
		QuadraticSieve qSieve = new QuadraticSieve();
		
		String result = "";
		
		for(int i = 1; i<=100; i++){
			for (int j = 0; j < 2; j++) {
				
				
				
				result = "";
				
				Logger.log(result);	
			}
		}
		
	}
	
}
