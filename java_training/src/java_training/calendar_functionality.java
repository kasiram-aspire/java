
package java_training;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

public class calendar_functionality {
	public static void main(String args[])
	{
		Calendar calc=Calendar.getInstance();
	     System.out.println("year: "+calc.get(Calendar.YEAR)); //fetch year
	     System.out.println("Month: "+calc.get(Calendar.MONTH)); //fetch month
	     System.out.println("Day:"+calc.get(Calendar.DATE));  //fetch date
	     System.out.println("HOUR:"+calc.get(Calendar.HOUR));  //fetch hour
	     System.out.println("Minute:"+calc.get(Calendar.MINUTE));  //fetch minute
	     System.out.println("SECOND:"+calc.get(Calendar.SECOND));  //fetch second
	     System.out.println("DAY_OF_WEEK:"+calc.get(Calendar.DAY_OF_WEEK)); //fetch day of week
	     LocalTime time=LocalTime.now();    //fetch localtime
	     
	 	System.out.println(time);
	 	System.out.println(time.getHour());    //fetch hour in localtime
	 	LocalDate date=LocalDate.now();
	 	System.out.println(date);    //fetch localdate
	 	LocalDateTime datetime=LocalDateTime.now();    //fetch locatdatetime
	 	System.out.println(datetime);
	 	Calendar calender=Calendar.getInstance();  //fetch  calendar
	 	System.out.println(calender);    
	 	System.out.println(calender.getTimeZone());    //fetch gettimeone inside calendar
	 	int year=calender.getWeekYear();    //fetch weakYear
	 	System.out.println(year);
	 	TimeZone temp=calender.getTimeZone();    
	 	System.out.println(temp.getID());                  //fetch id inside timezone
	 	ZonedDateTime zonedatetime=ZonedDateTime.now();    //fetch zoneddatetime
	 	System.out.println(zonedatetime);
	 	 Instant currentInstant = Instant.now();
	      System.out.println("Current Timestamp: " + currentInstant);    //fetch currentTimeStamp
	      Instant futureInstant = currentInstant.plusSeconds(30);	//add extra seconds
	      System.out.println("Future Timestamp (10 seconds later): " + futureInstant);
	      DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");    //change format of the time
	      System.out.println(datetime.format(format));
	     
	     
	}

}
