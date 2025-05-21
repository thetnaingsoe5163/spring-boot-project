package com.jdc.online.balances.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.jdc.online.balances.model.entity.consts.Role;
import com.jdc.online.balances.security.LoginFailureHandler;
import com.jdc.online.balances.security.LoginSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(req -> {
			req.requestMatchers("/", "/signin", "/signup", "/resources/**").permitAll();
			req.requestMatchers("/admin/**").hasAuthority(Role.Admin.name());
			req.requestMatchers("/member/**").hasAuthority(Role.Member.name());
			req.anyRequest().authenticated();
		});
		
		http.formLogin(form -> {
			form.loginPage("/signin");
			form.successHandler(new LoginSuccessHandler());
			
			form.failureHandler(new LoginFailureHandler());
		});
		
		http.logout(logout -> {
			logout.logoutUrl("/signout");
			logout.logoutSuccessUrl("/");
		});
		
		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
