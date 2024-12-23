package java_training;
import java.util.*;
public class string_methods {

	public static void main(String[] args) {
		String a="The Java programming language is statically-typ";
		String name1="kasi";
		String name2="ram";
		Scanner sc=new Scanner(System.in);
		System.out.println(a.toUpperCase());
		System.out.println(a.toLowerCase());
		System.out.println(a.indexOf("programming"));
		System.out.println("concated one is:"+name1+name2);
		System.out.println("concated one is:"+name1.concat(name2));
		System.out.println("press for display full name");
		int n=sc.nextInt();
		if(n==1)
		{
			System.out.println("full name is:"+name1.concat(name2));
		}
		
	}

}
;