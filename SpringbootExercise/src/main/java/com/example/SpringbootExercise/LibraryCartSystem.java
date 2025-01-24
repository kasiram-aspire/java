package com.example.SpringbootExercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/*
  This program is a Library Cart System built using Spring Boot. 
  The program simulates the management of a library where users can add books to a list (cart),
   remove books from it, and display the current list of books in the cart. 
   It uses the Spring Framework to manage the components and make the program modular and easily testable.
 */

class librarydetails
{   
	public int id;
	public String bookname;
	public librarydetails(int id,String name) //constructor for library details
	{
		this.id=id;
		this.bookname=name;
	}
	@Override
	public String toString() {
		return "librarydetails [id=" + id + ", bookname=" + bookname + "]";
	}
	
}

@Component
class librarymanagement
{
	public void display(librarydetails lbd)  //display opertaion take place here
	{
		System.out.println(lbd);
	}
}
@SpringBootApplication
public class LibraryCartSystem {
    public static void main(String[] args) {
    	List<librarydetails> library=new ArrayList<>();
        ApplicationContext context = SpringApplication.run(LibraryCartSystem.class, args); 
        librarymanagement lbm=context.getBean(librarymanagement.class);  // it stores the object for the libararymanagement class
        Scanner sc=new Scanner(System.in);
        while(true)   //it will loop upto pressing 4
		{   
			System.out.println("press 1 for Adding book");
			System.out.println("press 2 for Remove book from cart");
			System.out.println("press 3 for display");
			System.out.println("press 4 forExit");
			System.out.println("-------------------------------------------------");
			System.out.println("Enter the number:");
			int num=sc.nextInt();
			sc.nextLine();
			switch(num)
			{
			   case 1:     //adding data into the libarary details constructor
				   System.out.println("Enter the book id :");
					int idnum=sc.nextInt();
					sc.nextLine();
					System.out.println("Enter the book name:");
					String bookname=sc.nextLine();
					library.add(new librarydetails(idnum,bookname));
					break;
			   case 2:  //based on the id of the book it will remove the value from the array list
				   System.out.println("Enter the book id you want to remove :");
					int id=sc.nextInt();
					sc.nextLine();
					for(int index = 0;index<library.size();index++)
					{   
						librarydetails value=library.get(index);
						if(value.id==id)
						{
							library.remove(index);
						}
						
					}
					break;
			   case 3: //it display the all books in the library
				   System.out.println("-------------------------------------------------");
				    for(librarydetails lbd:library)
				    {
				    	lbm.display(lbd);
				    }
				    System.out.println("-------------------------------------------------");
					break;
			   
			   case 4:  //exit loop it initialized here
				 System.out.println(".................Exited...................");
				   return;
			   default:
				   System.out.println("*****invalid input******");
				   
			}
			
		}	
        
        
        
    }
}
