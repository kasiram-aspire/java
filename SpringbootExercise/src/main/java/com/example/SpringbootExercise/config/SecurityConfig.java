package com.example.SpringbootExercise.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity   // it says dont use default security use this configuration
public class SecurityConfig {
	@Autowired
	 UserDetailsService userDetailsService;
	@Bean
	public SecurityFilterChain securityfilterchain(HttpSecurity http) throws Exception
	{
		return http.csrf(customizer->customizer.disable()) //disable the csrf
	        .authorizeHttpRequests(request->request.anyRequest().authenticated()) //Applying authentication 
	      //http.formLogin(Customizer.withDefaults());//for authentication we need user id and name
		    .httpBasic(Customizer.withDefaults())//acess for restapi if not mentioned it return html body in reqbin
		    .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		    .build();  //return object of securityfilter chain
	}
//	@Bean
//	public UserDetailsService userDetailsservice() //user detailsservice is interface
//	{
//		UserDetails User1=User             
//				          .withDefaultPasswordEncoder()
//				          .username("kasiram")
//				          .password("123456789")
//				          .roles("User")
//				          .build();
//		return new InMemoryUserDetailsManager(User1);  //inside memoryuserdetailmanager implements userdetailsservice
//	}
	@Bean
	public AuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		provider.setUserDetailsService(userDetailsService);  //USING THIS CRATING A OWN USERDETAIL SERVICE
		return provider;
	}

}
