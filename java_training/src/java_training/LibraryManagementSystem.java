package java_training;

import java.util.ArrayList;
import java.util.Scanner;

class Bookdetails
{
	String bookName="",Author="";
	int copies=0,id;
	
	public Bookdetails(int id,String bookName,String Author,int copies)
	{
		this.id=id;
		this.bookName=bookName;
		this.Author=Author;
		this.copies=copies;
		
	}
	public void showBookInfo()
	{
		System.out.println("*******************************************************");
		System.out.println("Book Id:"+id);
		System.out.println("Book Name:"+bookName);
		System.out.println("Author:"+Author);
		System.out.println("copies:"+copies);
		System.out.println("********************************************************");

	}
	
	
}
class Library
{
	
	public ArrayList<Bookdetails> l;
	public Library()
	{
		this.l=new ArrayList<>();
		
	}
	public void addinfo(Bookdetails book)
	{
		l.add(book);
	}
	public void libDetails()
	{
		for(Bookdetails books:l)
		{
			books.showBookInfo();
		}
	}
	public void borrow(int idnum)
	{
		for(Bookdetails books:l)
		{
			if(books.id == idnum)
			{
				if(books.copies>0)
				{
					books.copies--;
					System.out.println("                ----sucessfull---");
					break;
				}
				else
				{
					System.out.println("           -------sorry book not available-------------");
				}
			}
		}
	}
		public void submit(int idnum)
		{
			for(Bookdetails books:l)
			{
				if(books.id == idnum)
				{
					
						books.copies++;
						System.out.println("         ----sucessfully submited---");
						break;
					
				}
			}
		
	}
	
}
public class LibraryManagementSystem {

	public static void main(String[] args) {
		Bookdetails book1=new Bookdetails(1,"Don Quixote","Miguel de Cervantes",4);
		Bookdetails book2=new Bookdetails(2,"Alice's Adventures in Wonderland","Lewis Carroll",3);
		Bookdetails book3=new Bookdetails(3,"The Adventures of Tom Sawyer","Mark Twain",5);
		Library lib=new Library();
		lib.addinfo(book1);
		lib.addinfo(book2);
		lib.addinfo(book3);
		lib.libDetails();
		Scanner sc=new Scanner(System.in);
		System.out.println("-------------------------------------------------");
		while(true)
		{   
			System.out.println("press 1 for Borrow");
			System.out.println("press 2 for Return");
			System.out.println("press 3 for display");
			System.out.println("press 4 forExit");
			System.out.println("-------------------------------------------------");
			System.out.println("Enter the number:");
			int num=sc.nextInt();
		switch(num)
		{
		   case 1:
			   System.out.println("Enter the book id you want to take:");
				int idnum=sc.nextInt();
				lib.borrow(idnum);
				break;
		   case 2:
			   System.out.println("Enter the book id you want to return :");
				int idn=sc.nextInt();
				lib.submit(idn);
				break;
		   case 3:
			   lib.libDetails();
			   break;
		   case 4:
			   System.out.println("     Exited.......");
			   return;
		   default:
			   System.out.println("*****invalid input******");
			   
		}
		
		}	
		
		
	}

}
