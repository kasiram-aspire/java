package java_training;
class MyOwnException extends Exception
{
	public MyOwnException(String s)
	{
		super(s);
	}
}
public class ExceptionHandling {
	public static void main(String args[])
	{
	    int a=9,b=0;
	    int array1[]=new int[10];
	    try
	    {
	    	int div=a/b;     //through exception
	    }
	    catch(Exception e)
	    {
	    	System.out.println("Someting went wrong....");
	    }
	    try
	    {
	    	int div1=a/b;     //Arithmetic exception
	    }
	    catch(ArithmeticException e)
	    {
	    	System.out.println("went wrong: "+e);
	    }
	    try
	    {
	    	System.out.println(array1[19]);  //index out of bound exception
	    } 
	    catch(ArrayIndexOutOfBoundsException e)
	    {
	    	System.out.println("Maintain with in the length: "+e);
	    }
	    try
	    {
	    	int div=18/20;
	    	if(div==0)
	    	{
	    		throw new ArithmeticException("i dont want to print zero");
	    	}
	    }
	    catch(ArithmeticException e)
	    {
	    	System.out.println(e);
	    }
	    try
	    {
	    	int div=18/20;
	    	if(div==0)
	    	{
	    		throw new MyOwnException("i dont want to print zero");  //CREATING CUSTOM 
	    	}
	    }
	    catch(MyOwnException e)
	    {
	    	System.out.println(e);
	    }
	    
	}

}
