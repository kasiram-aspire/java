package java_training;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

class StudentDatabase
{
	private int id;
    private String name;
    private int age;
    private double grade;
    private String department;
    public  StudentDatabase(int id,String name,int age,double grade,String department)
    {
    	this.id=id;
    	this.name=name;
    	this.age=age;
    	this.grade=grade;
    	this.department=department;
    }
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "StudentDatabase [id=" + id + ", name=" + name + ", age=" + age + ", grade=" + grade + ", department="
				+ department + "]";
	}
	
    
}
class SchoolManagingSystem
{   List<StudentDatabase>Schoolrecord;
	Scanner sc=new Scanner(System.in);
	public SchoolManagingSystem()
	{
		Schoolrecord=new ArrayList<>();
	}
	
	public void Add()
	{
		System.out.println("Enter the student id:");
		int id=sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the student name:");
		String name=sc.nextLine();
		System.out.println("Enter the student age:");
		int age=sc.nextInt();
		System.out.println("Enter the student grade:");
		double grade=sc.nextDouble();
		sc.nextLine();
		System.out.println("Enter the student department:");
		String department=sc.nextLine();
		Schoolrecord.add(new StudentDatabase(id,name,age,grade,department));
		
	}
	public void View()
	{
		for(StudentDatabase sdb:Schoolrecord)
		{
			System.out.println(sdb);
		}
	}
	public void Filter()
	{   System.out.println("1.grade grater than 75");
	    System.out.println("2.grade less than 75");
		System.out.println("Enter the number:");
		int num=sc.nextInt();
		sc.nextLine();
		switch(num)
		{
		case 1:
	    System.out.println("----------------------------------------------------");	
		Schoolrecord.stream()
		             .filter(student->student.getGrade()>75)
		             .forEach(n->System.out.println(n));
		System.out.println("----------------------------------------------------");
		  break;
		
	   case 2:
		   System.out.println("----------------------------------------------------");
		Schoolrecord.stream()
		             .filter(student1->student1.getGrade()<75)
		             .forEach(n1->System.out.println(n1));
		System.out.println("----------------------------------------------------");
		  break;
		
		}
	}
	public void Group()
	{
		Map<String,List<StudentDatabase>>groupstudents=Schoolrecord.stream()
				                                                    .collect(Collectors.groupingBy(studentobj->studentobj.getDepartment()));
		groupstudents.forEach((department,students)->System.out.println(department +" "+students));
	}
	public void Sort()
	{
		Schoolrecord.stream()
		            .sorted((stu1,stu2)->stu1.getName().compareTo(stu2.getName()))
		            .forEach(Stu->System.out.println(Stu));
	}
}
public class ManagementOfSchoolUsingStreams {

	public static void main(String[] args) {
		SchoolManagingSystem sms=new SchoolManagingSystem();
		Scanner sc=new Scanner(System.in);
		while(true)
		{   	 	   	
		    System.out.println("1.Add Student Records");
			System.out.println("2.View All Students");
			System.out.println("3.Filter Students");
			System.out.println("4.Sort Students");
			System.out.println("5.Group Students by Department");
			System.out.println("6.Find Top Performer");
			System.out.println("7.Exit..........");
			System.out.println("----------------------------------------------------");
			System.out.println("enter the no:");
		int n=sc.nextInt();
		sc.nextLine();
		  switch(n)
		  {
		  case 1:
			  sms.Add();
			  break;
		  case 2:
			  System.out.println("----------------------------------------------------");
			  sms.View();
			  System.out.println("----------------------------------------------------");
			  break;
		  case 3:
			  sms.Filter();
			  break;
		  case 4:
			  sms.Sort();
		  case 5:
			  sms.Group();
			  break;
		  case 7:
			  System.out.println("exited..................");
			  return ;
		  }
		}	

	}

}
