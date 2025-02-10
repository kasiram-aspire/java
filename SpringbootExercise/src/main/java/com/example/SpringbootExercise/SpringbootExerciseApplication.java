package com.example.SpringbootExercise;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
//@ComponentScan("com.example.SpringbootExercise.*")
public class SpringbootExerciseApplication {
    public static Logger logger=LogManager.getLogger( SpringbootExerciseApplication.class);
    
    public static void process()
    {   System.out.println("-------------------logger info-------------------------------------------------");
	   	logger.trace("This is a TRACE log message");
	    logger.debug("This is a DEBUG log message");
	    logger.info("This is an INFO log message");
	    logger.warn("This is a WARN log message");
	    logger.error("This is an ERROR log message");
    }
	public static void main(String[] args) {
		ApplicationContext context=SpringApplication.run(SpringbootExerciseApplication.class, args);
	    process();
//		demo obj=context.getBean(demo.class);
//		obj.show();
	}

}
