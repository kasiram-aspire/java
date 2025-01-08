package java_training;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileHandling {

	public static void main(String[] args) {
		// write to file
		try
		{
		 FileWriter fw=new FileWriter("demo.txt",true);
		 fw.write("hello world");
		 fw.append("welcome to java t point");
		 fw.close();
		}
		catch(Exception e)
		{
			System.out.println("something went wrong");
		}
		//using buffer to write a file
		try
		{
			FileWriter fw=new FileWriter("demo1.txt",true);
			BufferedWriter bw=new BufferedWriter(fw);
			bw.write("welcome");
			bw.newLine();
			bw.write("to new line");
			bw.close();
			
		}
		catch(Exception e)
		{
			System.out.println("something went wrong");
		}
		//read a file
		try
		{
			FileReader fr=new FileReader("demo.txt");
			int c=fr.read();
			
			while(c!=-1)
			{
				System.out.print((char)c);
				c=fr.read();
				
			}
			fr.close();
		}
		
		catch(Exception e)
		{
			System.out.println("something went wrong");
		}
		// read a file using buffered
		try
		{
			FileReader fr=new FileReader("demo.txt");
			BufferedReader br=new BufferedReader(fr);
			String line=br.readLine();
			while(line!=null)
			{
				System.out.println(line);
				line=br.readLine();
			}
			br.close();
		}
		
		
		catch(Exception e)
		{
			System.out.println("something went wrong");
		}
		try
		{
			File file=new File("demo.txt");
			file.delete();
		}
		catch(Exception e)
		{
			System.out.println("something went wrong");
		}

	}

}
