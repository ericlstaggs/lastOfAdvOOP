import java.util.*;
import java.io.*;

public class Exceptional {

	public static void main(String[] args) {
		System.out.println("start of main");

		double answer = 0.0;
		try {

			answer = 3 / 0;
			System.out.println(answer);

		} catch (ArithmeticException ae) {
			System.out.println("begining of catch");

			System.err.println(ae.getMessage());
			System.out.println("you blew it!");

		} finally {
//			finally will run this code no matter what
			System.out.println(answer);

		}

		readText("Exceptional.class");
		readBinary("yoda.jpg");
		System.out.println("end of main");
	}

	private static void readText(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
			br.ready();

		} catch (FileNotFoundException fnf) {
			System.err.println(fnf.getMessage());
		} catch (IOException ieo) {
			System.err.println(ieo.getMessage());
		}
	}

	private static void readBinary(String filename) {

		try {

			FileInputStream fis = new FileInputStream(filename);
			int bite = fis.read();

			System.out.printf("%x\n", bite);
			bite = fis.read();
			System.out.printf("%x\n", bite);

			System.out.println(bite);

			fis.close();

		} catch (FileNotFoundException fnf) {
			System.err.println(fnf.getMessage());
		} catch (IOException ieo) {
			System.err.println(ieo.getMessage());
		}
	}

}
