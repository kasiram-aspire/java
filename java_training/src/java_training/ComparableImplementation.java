package java_training;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class detailsofstudent1 implements Comparable< detailsofstudent1 >
{
     private String name;
     private int age;
     
	
	public detailsofstudent1(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "detailsofstudent1 [name=" + name + ", age=" + age + "]";
	}
	public int compareTo( detailsofstudent1  o) {
		if(this.getAge()>=o.getAge())
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
     
}
public class ComparableImplementation {

	public static void main(String[] args) {
		List<detailsofstudent1>studentlist=new ArrayList<>();
		Comparator<detailsofstudent1>com=new Comparator<>()
				{

				
					public int compare(detailsofstudent1 i,detailsofstudent1 j) {
						if(i.getAge()>=j.getAge())
						{
							return 1;
						}
						else
						{
							return -1;
						}
					}
				};
		studentlist.add(new detailsofstudent1("kasi",12));
		studentlist.add(new detailsofstudent1("ram",10));
		studentlist.add(new detailsofstudent1("rahul",19));
		studentlist.add(new detailsofstudent1("kannan",15));
		Collections.sort(studentlist,com);
		Collections.sort(studentlist);
		System.out.println(studentlist.get(1));
	}

}
