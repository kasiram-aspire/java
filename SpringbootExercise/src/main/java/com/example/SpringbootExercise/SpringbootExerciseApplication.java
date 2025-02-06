package com.example.SpringbootExercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
//@ComponentScan("com.example.SpringbootExercise.*")
public class SpringbootExerciseApplication {

	public static void main(String[] args) {
		ApplicationContext context=SpringApplication.run(SpringbootExerciseApplication.class, args);
//		demo obj=context.getBean(demo.class);
//		obj.show();
	}

}
