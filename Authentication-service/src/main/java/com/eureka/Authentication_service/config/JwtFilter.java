package com.eureka.Authentication_service.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.eureka.Authentication_service.service.JwtService;
import com.eureka.Authentication_service.service.MyUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtFilter extends OncePerRequestFilter{
	
    @Autowired
     private JwtService jwtservice;
    @Autowired
     ApplicationContext context;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		     //when i request .from client side response is Bearer actualtoken 
		String authHeader=request.getHeader("Authorization");//The "Authorization" header typically contains authentication credentials, 
		                                                     //such as a JWT (JSON Web Token) or a Basic Auth token.
		String token=null;
		String username=null;
		if(authHeader !=null && authHeader.startsWith("Bearer"))  // extract user name from token
		{
			token=authHeader.substring(7);
			username=jwtservice.extractUserName(token);
		}
		if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			 UserDetails userDetails= context.getBean(MyUserDetailService.class).loadUserByUsername(username);
			if(jwtservice.validateToken(token,userDetails))  //check token is valid based on user name
			{
				UsernamePasswordAuthenticationToken authToken=
						new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				 SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);  //Passes the request and response to the next filter in the chain.
	}
   
}
