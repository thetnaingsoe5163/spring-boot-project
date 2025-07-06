package com.tns.ordermanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.tns.ordermanagement.model.entity.constant.Role;
import com.tns.ordermanagement.security.LoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(req -> {
			req.requestMatchers("/guest/**", "/signin", "/signup").permitAll();
			req.requestMatchers("/resources/**").permitAll();
			req.requestMatchers("/admin/**").authenticated();
			req.requestMatchers("/admin/**").hasRole(Role.Admin.name());
			req.anyRequest().authenticated();
		});
		
		http.formLogin(form -> {
			form.loginPage("/signin");
			form.successHandler(new LoginSuccessHandler());
			
		});
		
		http.logout(form -> {
			form.logoutUrl("/signout");
			form.logoutSuccessUrl("/");
		});
		
		return http.build();
	}

	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
