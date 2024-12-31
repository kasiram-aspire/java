package java_training;

import java.util.ArrayList;
import java.util.Scanner;

class employeeDetails
{
	private String Name;
	private int employeeId;
	private double salary;
	private boolean employementType;
	public employeeDetails(String name, int employeeId, double salary, boolean employementType) {
		Name = name;
		this.employeeId = employeeId;
		this.salary = salary;
		this.employementType = employementType;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public boolean isEmployementType() {
		return employementType;
	}
	public void setEmployementType(boolean employementType) {
		this.employementType = employementType;
	}
	
	
}
class employee extends employeeDetails
{
	
	public employee(String name, int employeeId, double salary, boolean employementType) {
		super(name, employeeId, salary, employementType);
	}
	
	public static void display(ArrayList<employeeDetails>empobj)
	{
		for(employeeDetails empdt:empobj)
		{
			System.out.println("name:"+empdt.getName());
			System.out.println("employeeid:"+empdt.getEmployeeId());
			System.out.println("salary:"+empdt.getSalary());
			if(empdt.isEmployementType()==true)
			{
			System.out.println("employee type:full time");
			}
			else
			{
				System.out.println("employee type:contract");
			}
			
		}
	}
	public static void total(ArrayList<employeeDetails>empobj)
	{
		double sum = 0;
		for(employeeDetails empdt:empobj)
		{
			sum+=empdt.getSalary();
		}
		System.out.println("total salary expenditure:"+sum);
	}
	
	
}
public class EmployeeManagementSystem {

	public static void main(String[] args) {
		 ArrayList<employeeDetails> empobj=new ArrayList<>();
		Scanner sc=new Scanner(System.in);
		while(true)
        {   
        	System.out.println("press 1 for Add employee"); 
			System.out.println("press 2 for Display employee details");
			System.out.println("press 3 for total salary expense for all employee");
			System.out.println("press 4 for Exit");
			System.out.println("-------------------------------------------------");
			System.out.println("Enter the number:");
			int num1=sc.nextInt();
			sc.nextLine();
			switch(num1)
			{
			   case 1:
				   System.out.println("Enter the name");
				   String name=sc.nextLine();
				   System.out.println("Enter the employeeid");
				   int employeeid=sc.nextInt();
				   System.out.println("Enter the salary");
				   double salary=sc.nextDouble();
				   System.out.println("Enter the employement type 1 for full time ,2 for not a full time");
				   int emptyp=sc.nextInt();
				   boolean emptype;
				   if(emptyp==1)
				   {
					   emptype=true;
				   }
				   else
				   {
					   emptype=false;
				   }
				   employee empd=new employee(name,employeeid,salary,emptype);
				   empobj.add(empd);
				   break;
			 case 2:
	 			  employee.display(empobj);
				 break;
			 case 3:
				  employee.total(empobj);   
				 break;
			 case 4:				 
				 System.out.println("exit");
				 return;
			}
        }
		

	}

}
