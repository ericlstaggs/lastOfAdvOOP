


public class Add{
	public static void main(String[] args){
		
		if(!(args.length >= 2)){
			System.out.println("Usage: java Add num1 num2");
			return;
		} 
		
		int total = 0;
		int index = 0;
		do {
			int arg = 0;
			try {
				arg = Integer.parseInt(args[index]);
		
			} catch(NumberFormatException nfe) {
				System.err.println("invalid input");
			}
			total += arg;
			index ++;
		} while (index < args.length);
		System.out.println(total);
	}
}