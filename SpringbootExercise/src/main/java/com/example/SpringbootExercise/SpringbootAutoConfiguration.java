package com.example.SpringbootExercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

// implemented the loose
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
@Configuration           //using configuration annaotation to autoconfiguration detect the configuration file.
class Appconfig
{
	@Bean                       //bean created manually
	demo Demo()
	{
		return new demo();
	}
	
}
@SpringBootApplication
public class SpringbootAutoConfiguration {
	public static void main(String[] args) {
	ApplicationContext context= SpringApplication.run(SpringbootAutoConfiguration.class, args);
	demo obj=context.getBean(demo.class);     // get the bean for the demo class
	obj.show();
}
	
	
}
