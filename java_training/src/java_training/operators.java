package java_training;
import java.util.*;
public class operators {

	public static void main(String[] args) {
		   
           Scanner sc=new Scanner(System.in);
           int age1,age2;
           System.out.println("Enter the age 1:");
           age1=sc.nextInt();
           System.out.println("Enter the age 2:");
           age2=sc.nextInt();
           String name="kasi";
           
           if(age1 < age2)
           {
        	   System.out.println("Age 2 is lesser than age 1");
           }
           else if(age1 > age2)
           {
        	   System.out.println("Age 1 is grater than age 2");
           }
           else
           {
        	   System.out.println("Age 1 is equal to age 2");
           }
           
           if(name=="kasi" && age1>=18)
           {
        	   System.out.println("you are eligible for voting");
           }
           
           if(name=="kasi" || age1>=18)
           {
        	   System.out.println("it will execute if any of the value is true and both are true");
           }
          
          
	}

}
