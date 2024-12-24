package java_training;

import java.util.Scanner;

public class BankingApplication {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		int n,pressed_number;
		System.out.println("Enter the num of account u want to hold:");
		n=sc.nextInt();
		int Account_no[]=new int[n];
		String Name[]=new String[n];
		int Amount[]=new int[n];
		System.out.println("----------------------------------------------");
		while(true)
		{	
			System.out.println("Press 1 for create an account ");
			System.out.println("Press 2 for Deposit money ");
			System.out.println("Press 3 for Withdraw money ");
			System.out.println("Press 4 for Generate Account statement ");
			System.out.println("Press 5 for Exit the application");
			System.out.println("----------------------------------------------");
			System.out.println("  ");
			
			pressed_number=sc.nextInt();
			sc.nextLine();

		switch(pressed_number)
		{
		  case 1:
			  for (int i = 0; i < n; i++) {
		           
		            int rand_acc_num = (int) (Math.random() * 400000) + 500000;
		            Account_no[i] = rand_acc_num;
		            System.out.println("Enter the name:");
		            Name[i] = sc.nextLine();  
		            
		            Amount[i] = 0;  
		            
			  }
			  break;
		  case 2:
			  System.out.println("-------------Deposit the amount---------------------");
			  System.out.println("Enter Your Account number:");
			  int acc=sc.nextInt();
			  int index=-1;
			  for(int i=0;i<n;i++)
			  {
				  if(Account_no[i]==acc)
				  {
					  index=i;
					  break;
				  }
			  }
			  if (index != -1) {
				  System.out.println("Enter the Amount you want to deposit:");
				  int amnt=sc.nextInt();
				  Amount[index]+=amnt;
				  
		        } 
			  else {
		            System.out.println( " Element is not present in the array.");
		        }
			  System.out.println("----------------------------------------------");
			  break;
		  case 3:
			  System.out.println("-------------withdraw the amount---------------------");
			  System.out.println("Enter Your Account number:");
			  int accn=sc.nextInt();
			  int index1=-1;
			  for(int i=0;i<n;i++)
			  {
				  if(Account_no[i]==accn)
				  {
					  index1=i;
					  break;
				  }
			  }
			  if (index1 != -1) {
				  System.out.println("Enter the Amount you want to withdraw:");
				  int amnt=sc.nextInt();
				  if(Amount[index1]>=amnt)
				  {
					Amount[index1]-=amnt;
					System.out.println("------The amount withdrawn success fully-------");
					 System.out.println("   The balance amount is:"+Amount[index1]);
					 System.out.println("----------------------------------------------");
				  }
				  else
				  {
					  System.out.println("Insufficient balance");
				  }
				  
		        } 
			  else {
		            System.out.println( " Element is not present in the array.");
		        }
			  break;
		  case 4:
			  System.out.println("-------------Account Statement--------------------");
			  for(int i=0;i<n;i++)
			  {
				  System.out.println("Account No:"+ Account_no[i]);
				  System.out.println("Name:"+ Name[i]);
				  System.out.println("Amount:"+ Amount[i]);  
				  System.out.println("----------------------------------------------");  
				  
			  }
			  break;
		  case 5:
              
              System.out.println("Exiting the application........");
              sc.close();
              return;
	      default:
	    	  System.out.println("you pressed the invalid no ");
			  
		}
	}
		
         
	}

}
