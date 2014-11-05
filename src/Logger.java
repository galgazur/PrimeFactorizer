import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;




public class Logger {
	public static void log(String N, String str, String name){
		String[] rows = str.split("\n");
		
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("factorization-" + name + ".txt", true)));

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
