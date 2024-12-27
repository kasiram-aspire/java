package java_training;

import java.util.ArrayList;
import java.util.Scanner;

class student
{
	private String  Name;
	private int  PhoneNo;
	private String  Rollno;
	private String  Adress;
	
	public student(String Name,int PhoneNo,String Rollno,String Adress)
	{
		this.Name=Name;
		this.PhoneNo=PhoneNo;
		this.Rollno=Rollno;
		this.Adress=Adress;
	}
  
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getPhoneNo() {
		return PhoneNo;
	}

	public void setPhoneNo(int phoneNo) {
		PhoneNo = phoneNo;
	}

	public String getRollno() {
		return Rollno;
	}

	public void setRollno(String rollno) {
		Rollno = rollno;
	}

	public String getAdress() {
		return Adress;
	}

	public void setAdress(String adress) {
		Adress = adress;
	}

	@Override
	public String toString() {
		return "student [Name=" + Name + ", PhoneNo=" + PhoneNo + ", Rollno=" + Rollno + ", Adress=" + Adress + "]";
	}
	
	
}
class studentManagementSystems
{
	public ArrayList<student> stud;
	public studentManagementSystems()
	{
		stud=new ArrayList<>();
	}
	public void addStudent(student obj)
	{
		stud.add(obj);
	}
	public void viewStudent()
	{
		System.out.println("-------------------------------------------------");
		for(student st:stud)
		{
		     System.out.println("student name:"+st.getName());
		     System.out.println("Phone no:"+st.getPhoneNo());
		     System.out.println("RollNo:"+st.getRollno());
		     System.out.println("Adress:"+st.getAdress());
		     System.out.println(" ");
		}
		System.out.println("-------------------------------------------------");
	}
	public void update()
	{
		Scanner sc=new Scanner(System.in);
		int n;
		System.out.println("press 1 to update the name");
		System.out.println("press 2 to update the phone no");
		System.out.println("press 3 to update the adress");
		System.out.println("Enter the number");
		n=sc.nextInt();
		sc.nextLine();
		switch(n)
		{
		   case 1:
			   System.out.println("Enter the roll no .you want to update the name");
			   String roll=sc.nextLine();
			   for(student st:stud)
			   {
				   if(st.getRollno().equals(roll))
				   {
					   System.out.println("Enter the new name:");
					   String name=sc.nextLine();
					   st.setName(name);
				   }
				   else
				   {
					   System.out.println("Roll no not found");
				   }
			   }
			   break;
		   case 2:
			   System.out.println("Enter the roll no .you want to update the name");
			   roll=sc.nextLine();
			   for(student st:stud)
			   {
				   if(st.getRollno().equals(roll))
				   {
					   System.out.println("Enter the new phone no:");
					   int phn=sc.nextInt();
					   st.setPhoneNo(phn);
					   sc.nextLine();
				   }
				   else
				   {
					   System.out.println("Roll no not found");
				   }
			   }
			   break;
		   case 3:
			   System.out.println("Enter the roll no .you want to update the name");
			    roll=sc.nextLine();
			   for(student st:stud)
			   {
				   if(st.getRollno().equals(roll))
				   {
					   System.out.println("Enter the new name:");
					   String addr=sc.nextLine();
					  st.setAdress(roll);
				   }
				   else
				   {
					   System.out.println("Roll no not found");
				   }
			   }
			   break;
		}
	}
	
	
}
public class StudentManagementSystem {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		String Name,RollNo,Adress;
		int PhoneNo;
		studentManagementSystems sms=new studentManagementSystems();
        while(true)
        {
        	System.out.println("press 1 for Add student info");
			System.out.println("press 2 for update student info");
			System.out.println("press 3 for view student info");
			System.out.println("press 4 for Exit");
			System.out.println("-------------------------------------------------");
			System.out.println("Enter the number:");
			int num1=sc.nextInt();
			sc.nextLine();
			switch(num1)
			{
			 	case 1:
			 		System.out.println("Enter Name:");
			 		Name=sc.nextLine();
			 		System.out.println("Enter the RollNo:");
			 		RollNo=sc.nextLine();
			 		System.out.println("Enter the Adress:");
			 		Adress=sc.nextLine();
			 		System.out.println("Enter the PhoneNo");
			 		PhoneNo=sc.nextInt();
                    sc.nextLine();
                    student Student=new student(Name,PhoneNo,RollNo,Adress);
                    sms.addStudent(Student);
			 		break;
			 	case 2:
			 		  sms.update();
			 		  break;
			 	case 3:
			 		sms.viewStudent();
			 		break;
			 	case 4:
			 		System.out.println("Exited..............");
			 		return;
			 		
			}
        }
               
	}

}
