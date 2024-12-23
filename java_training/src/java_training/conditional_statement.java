package java_training;

import java.util.Scanner;

public class conditional_statement {
	public static void main(String args[])
	{
		int num1=10,num2=20,n;
		System.out.println("press 1 for find the max between two numbers");
		System.out.println("press 2 for find the min between two numbers");
		System.out.println("press 3 for find the square root");
		Scanner sc=new Scanner(System.in);
		n=sc.nextInt();
		if(n==1)
		{
			System.out.println("the max value is:"+Math.max(num1,num2));
		}
		else if(n==2)
		{
			System.out.println("the min value is:"+Math.min(num1,num2));
		}
		else if(n==3)
		{
			System.out.println("the square root value is:"+Math.sqrt(num2));
		}
		else
		{
			System.out.println("invalid input");
		}
	}

}
