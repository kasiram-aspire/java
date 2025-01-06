package java_training;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
class HandlingOpertaion
{
	public void create()
	{   System.out.println("Enter the file name");
	    Scanner sc=new Scanner(System.in);
	    String filename=sc.nextLine();
	    try {
            File file = new File(filename);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File exists");
            }
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e);
        }
	}
	public void delete()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the file name.you want to delete");
	    String filename=sc.nextLine();
	    try
	    {
	    	 File file = new File(filename);
	    	 if(file.exists())
	    	 {
	    		 file.delete();
	    		 System.out.println("file deleted sucessfully");
	    	 }
	    	 else
	    	 {
	    		 System.out.println("File not found");
	    	 }
	    	 
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Something went wrong: " + e);
	    }
	    
	}
	public void write()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the file name.you want to write");
	    String filename=sc.nextLine();
	    try
	    {
		  FileWriter fw=new FileWriter(filename,true);
		  BufferedWriter bw=new BufferedWriter(fw);
		  System.out.println("Enter the word you want to write");
		  String content=sc.nextLine();
		  bw.append(content);
		  bw.close();
		  fw.close();
		  
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Something went wrong: " + e);
	    }
	}
	public void read()
	{

		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the file name.you want to read");
	    String filename=sc.nextLine();
	    try
	    {
		  FileReader fw=new FileReader(filename);
		  BufferedReader bw=new BufferedReader(fw);
		  String status=bw.readLine();
		  while(status!=null)
		  {
			  System.out.println(status);
			  status=bw.readLine();
		  }
		  bw.close();
		  fw.close();
		  
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Something went wrong: " + e);
	    }
	}
}

public class FileHandlingOperation {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		HandlingOpertaion hop=new HandlingOpertaion();
		while(true)
		{
		 System.out.println("1.create a file\n2.write into a file\n3.read a file\n4.delete a file\n5.exit");
		 int num=sc.nextInt();
		 switch(num)
		  {
		      case 1:
		    	  hop.create();
		    	  break;  
		      case 2:
		    	   hop.write();
		    	   break;
		      case 3:
		    	   hop.read();
		    	   break;
		      case 4:
		    	  hop.delete();
		    	  break;
		      case 5:
		    	  System.out.println("Exit..........");
		    	  return;
		    	  
		  }
		}

	}

}
