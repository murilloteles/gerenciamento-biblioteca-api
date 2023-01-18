package com.telesdev.biblioteca.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

@EnableWebSecurity
@Component
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value("${project.security.usuario}")
	private String usuario;
	
	@Value("${project.security.senha}")
	private String senha;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.inMemoryAuthentication().withUser(usuario)
		.password("{noop}" + senha).roles("USER");
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		http.
			authorizeRequests()
			.anyRequest().authenticated()
			.and()
				.httpBasic()
			.and()
				.csrf().disable();
	}
}