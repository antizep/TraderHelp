package traderHelp;

import java.util.Random;

public class ForVsWhile {

	
	public static void main(String[] args) {
		
		for (int i = 0; i < 10; i++) {
			System.out.println("����:"+ (10-i));
		}
		System.out.println("go");
		
		boolean f = true;
		
		while (f) {
			Random random = new Random();
			int i = random.nextInt(1000);
			System.out.println("������ - "+i);
			
			if(i<100) {
				f = false;
				System.out.println("���������");

			}else {
				System.out.println("�� �� ����� ����������");
			}
			
		}
		

	}
	
	private static void run() {
		
	}

}
