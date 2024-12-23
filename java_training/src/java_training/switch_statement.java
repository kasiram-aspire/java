package java_training;

import java.util.Scanner;

public class switch_statement {

	public static void main(String[] args) {
		int n;
		Scanner sc=new Scanner(System.in);
		System.out.println("enter the number between 1-4");
		n=sc.nextInt();
        switch(n)
        {
           case 1:
        	   System.out.println("The number 1 is pressed");
        	   break;
           case 2:
        	   System.out.println("The number 2 is pressed");
        	   break;
           case 3:
        	   System.out.println("The number 3 is pressed");
        	   break;
           case 4:
        	   System.out.println("The number 4 is pressed");
        	   break;
           default:
        	   System.out.println("The invalid number is pressed");
            
        }
	}

}
