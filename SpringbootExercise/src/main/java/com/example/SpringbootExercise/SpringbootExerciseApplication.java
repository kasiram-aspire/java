package com.example.SpringbootExercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
interface Computer
{
	public void display();
}
@Component 
class Desktop implements Computer
{
	public void display()
	{
		System.out.println("computer showed");
	}
}

@Component
class Laptop implements Computer
{
	public void display()
	{
		System.out.println("laptop showed");
	}
}
@Component
class demo
{   
	Computer com;
	@Autowired 
	@Qualifier("desktop")
	public void setlpatop(Computer obj)
	{
		com=obj;
	}
	public void show()
	{
		System.out.println("demo showed");
		com.display();
	}
}

@SpringBootApplication
public class SpringbootExerciseApplication {

	public static void main(String[] args) {
		ApplicationContext context=SpringApplication.run(SpringbootExerciseApplication.class, args);
		demo obj=context.getBean(demo.class);
		obj.show();
	}

}
