package com.eureka.Authentication_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

@Configuration
@EnableWebSecurity   // it says dont use default security use this configuration
public class SecurityConfig {
	@Autowired    // for custom Authentication 
	 UserDetailsService userDetailsService;
	@Autowired
	private JwtFilter jwtfilter;
	@Bean
	public SecurityFilterChain securityfilterchain(HttpSecurity http) throws Exception
	{
		HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(
		        new ClearSiteDataHeaderWriter(ClearSiteDataHeaderWriter.Directive.CACHE, 
		                                      ClearSiteDataHeaderWriter.Directive.COOKIES, 
		                                      ClearSiteDataHeaderWriter.Directive.STORAGE)
		);
		return http.csrf(customizer->customizer.disable())
		//forces 403 forbidden for encryption
		.authorizeHttpRequests(request->request
				                        .requestMatchers("auth/adduser","auth/login","auth/refresh-token","auth/validatetoken")
				                        .permitAll() //it permits the adduser and login without username and password
				                        .requestMatchers("/auth/uploadUsers").hasAnyAuthority("SuperAdmin") // Both ADMIN and USER can view employees
				                        .anyRequest().authenticated())
		//this generates login
		//.formLogin(Customizer.withDefaults())
		//for JSON data but it by passes if form login is not present but generates each session for every refresh or restart
		.httpBasic(Customizer.withDefaults())
		//this generates each session every time
		//to make this stateless means generating each session for every refresh or every run
		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.addFilterBefore(jwtfilter,UsernamePasswordAuthenticationFilter.class)
		.logout((logout) -> logout.addLogoutHandler(clearSiteData))
		.build();
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
	@Bean   // for custom Authentication like username password handling from database
	public AuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
//		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());  // plain text
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));   // decode the encrypted password
		provider.setUserDetailsService(userDetailsService);  //USING THIS Creating A OWN USERDETAIL SERVICE
		return provider;
	}
	@Bean
	 public AuthenticationManager authenticationmanagaer(AuthenticationConfiguration config ) throws Exception
	 {
		return config.getAuthenticationManager();// this will give object we have to hold that
		// now the authentication manager will talk to authentication provider;
		
	 }
	

}