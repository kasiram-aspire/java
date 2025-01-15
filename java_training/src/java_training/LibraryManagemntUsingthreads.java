package java_training;

import java.util.concurrent.Semaphore;

class libraryitems
{
	private int totalbooks;
	private Semaphore semaphore;
	
	public libraryitems(int totalbooks) {
		this.totalbooks = totalbooks;
	    this.semaphore = new Semaphore(totalbooks);
	}
	public int getTotalbooks() {
		return totalbooks;
	}
	public void setTotalbooks(int totalbooks) {
		this.totalbooks = totalbooks;
	}
	public void borrow(int id)
	{
		System.out.println("user "+id+"is trying to borrow book");
		if(semaphore.tryAcquire())
		{
			System.out.println("user "+id+ "is borrowed book successfully");
			totalbooks--;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			returnBook(id);
		}
		else
		{
			System.out.println( "book not available");
		}
	}
	
	public synchronized void returnBook(int userId) {
        System.out.println("User " + userId + " is returning the book...");
        totalbooks++;
        semaphore.release(); // Release the book back to the library
        System.out.println("User " + userId + " returned a book. Books available: " +  totalbooks);
    }
}
class users extends Thread
{
	public int id;
	public libraryitems lib;
	public users(int id, libraryitems lib)
	{
		this.id = id;
		this.lib = lib;
	}
	public void run()
	{
		lib.borrow(id);
	}
	
	
}

public class LibraryManagemntUsingthreads {

	public static void main(String[] args) {
		Thread[] thread=new Thread[5];
		libraryitems lib=new libraryitems(3);
		for(int i=0;i<5;i++)
		{    int item=i+1;
			thread[i]=new Thread(new users(i+1,lib));  
			thread[i].start();
		}
        for(Thread t:thread)
        {
        	try {
				t.join();
			} 
        	catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
	}

}
