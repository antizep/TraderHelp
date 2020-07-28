package traderHelp;

import java.util.Random;

public class ForVsWhile {

	
	public static void main(String[] args) {
		
		for (int i = 0; i < 10; i++) {
			System.out.println("ждем:"+ (10-i));
		}
		System.out.println("go");
		
		boolean f = true;
		
		while (f) {
			Random random = new Random();
			int i = random.nextInt(1000);
			System.out.println("выпало - "+i);
			
			if(i<100) {
				f = false;
				System.out.println("закончили");

			}else {
				System.out.println("не то число продолжаем");
			}
			
		}
		

	}
	
	private static void run() {
		
	}

}
