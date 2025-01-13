package java_training;

import java.util.ArrayList;
import java.util.List;
class gen<T>
{
	T a;
	
	public T getA() {
		return a;
	}

	public void setA(T a) {
		this.a = a;
	}

	public void show()
	{
		System.out.println(a.getClass().getName());
	}
	
}
class gen1<T extends Number>
{
	T a;
	
	public T getA() {
		return a;
	}

	public void setA(T a) {
		this.a = a;
	}

	public void show()
	{
		System.out.println(a.getClass().getName());
	}
	public void demo(ArrayList<? extends T> obj)
	{
		
	}
	
}
public class Genrics {

	public static void main(String[] args) {
		int a=10;
		/*the type of the vaue is int .it is type safe and it is define at the 
		  complile time 
             */
		List obj=new ArrayList();
		obj.add(7);
		obj.add("kasi");
		/* in this case we defined a list without genrics .we can able to add int 
		   folat,string so now it is not type safe.and we want to retrive and store in another 
		   variable we want to define data type so that time we want to type cast so that
		   the variable support all datatypes
		   eg:int value=obj.get(1); it produce error
		       int value=Integer.parseInt(obj.get(1)); its the correct way
		     
		 */
		//so now genrics is introduced
		List<Integer> obj1=new ArrayList<>();
		obj1.add(7);
		obj1.add(8);
		// now it support only integer type
		
		/* suppose if the class  type we dont know we have to implement like
		  that mentioned below
		 */
		gen<Integer> g=new gen<>();
		g.setA(10);
		g.show();
		
		gen1<Number> g1=new gen1<>();
		g1.setA(10);
		g1.show();
		g1.setA(12.4);
		g1.show();
		g1.demo(new ArrayList<Integer>());
		
		
		
	}

}
