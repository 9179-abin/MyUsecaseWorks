package com.cts.training.frontendservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class FrontendWebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	FrontendConfiguration config;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.inMemoryAuthentication()
		.withUser(config.getAuthUsername()).password("{noop}"+config.getAuthPassword()).authorities("ROLE_ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http
		.csrf().disable()
		.authorizeRequests()
		.anyRequest().authenticated()
		.and().httpBasic();
	}
	

}
