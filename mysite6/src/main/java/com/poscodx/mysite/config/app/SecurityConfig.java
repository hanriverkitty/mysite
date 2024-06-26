package com.poscodx.mysite.config.app;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import com.poscodx.mysite.security.UserDetailsServiceImpl;

@SpringBootConfiguration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return new WebSecurityCustomizer() {
            @Override
            public void customize(WebSecurity web) {
                web
            		.ignoring()
            		.requestMatchers(new AntPathRequestMatcher("/favicon.ico"))
            		.requestMatchers(new AntPathRequestMatcher("/assets/**"));
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	String s = HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;
    	http
    		.logout()
    		.logoutUrl("/user/logout")
    		.logoutSuccessUrl("/")
    		.and()
    		.formLogin()
    		.loginPage("/user/login")
    		.loginProcessingUrl("/user/auth")
    		.usernameParameter("email")
    		.passwordParameter("password")
    		.defaultSuccessUrl("/")
       		//.failureUrl("/user/login?result=fail")
    		.failureHandler(new AuthenticationFailureHandler() {

				@Override
				public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
						AuthenticationException exception) throws IOException, ServletException {
					request.setAttribute("email", request.getParameter("email"));
					request
						.getRequestDispatcher("/user/login")
						.forward(request,response);
					
				}
    			
    		})  		
    		.and()
    		.csrf()
    		.disable()
    		.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
    			authorizationManagerRequestMatcherRegistry
    			/* ACL */
    				.requestMatchers(new RegexRequestMatcher("^/admin/?.*$",null))
    				.hasAnyRole("ADMIN")
    				
    				.requestMatchers(new RegexRequestMatcher("^/board/?(add|update|delete|reply)?/.*$",null))
	    			.hasAnyRole("ADMIN","USER")
    				
	    			.requestMatchers(new RegexRequestMatcher("^/user/update$",null))
	    			.hasAnyRole("ADMIN","USER")
	    			
	    			.anyRequest()
	        		.permitAll();
			});
    	
//		.exceptionHandling(exceptionHandlingConfigurer -> {
//		exceptionHandlingConfigurer.accessDeniedPage("/WEB-INF/views/error/403.jsp")
//});
    	
        return http.build();
    }
    
 // Authentication Manager
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    	authenticationProvider.setPasswordEncoder(passwordEncoder);
    	authenticationProvider.setUserDetailsService(userDetailsService);

    	return new ProviderManager(authenticationProvider);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
    	// 여기서 숫자가 클 수록 시간이 많이 걸린다
    	return new BCryptPasswordEncoder(4 /* 4~16 */);
    }

    @Bean
    public UserDetailsService userDetailsService() {
    	return new UserDetailsServiceImpl();
    }
}