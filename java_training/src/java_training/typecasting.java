package java_training;

public class typecasting {
	public static void main(String arg[]) {
		int a=10;
		double c=a;     //widening casting
		float d=a;       //widening casting
		float e=10.56f;
		int b=(int)e;      //narrow casting
		String num="10";
		int n=Integer.parseInt(num);
	    int num1=100;
	    String s=Integer.toString(num1);
		System.out.println("widening casting from int to double:"+c);
		System.out.println("widening casting from int to float:"+d);
		System.out.println("type casting from float to int:"+b);
		System.out.println("type casting from string to int:"+n);
		System.out.println("type casting from int to string:"+s);
	}

}
