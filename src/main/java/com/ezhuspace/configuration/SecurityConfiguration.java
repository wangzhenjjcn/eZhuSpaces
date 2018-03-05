package com.ezhuspace.configuration;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

 
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  public static final String SSO_HEADER = "AUTH_USER";
	public static final String ADMIN = "ROLE_ADMIN";
	public static final String USER = "ROLE_USER";
	public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http.csrf().disable()
    	.authorizeRequests()
    	.antMatchers("/", "/**", "/**/**", "/css/**", "/img/**", "/test", "/debug", "/event/authorize", "/callback/**")
    	.hasRole("ANONYMOUS").anyRequest().permitAll()
    	.and().authorizeRequests().antMatchers("/", "/**", "/**/**", "/css/**", "/img/**", "/test", "/debug", "/event/authorize", "/callback/**").permitAll()
        .anyRequest().fullyAuthenticated().and() 
        .formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
        .and().logout()
        .deleteCookies("remember-me").permitAll().and().rememberMe();
    }
    
    @Bean
    public AnonymousAuthenticationProvider anonymousAuthenticationProvider(){
        return new AnonymousAuthenticationProvider("foobar");
    }


}
