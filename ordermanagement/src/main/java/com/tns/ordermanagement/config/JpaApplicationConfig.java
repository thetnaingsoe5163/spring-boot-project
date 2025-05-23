package com.tns.ordermanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {
		"com.tns.ordermanagement.model.entity",
		"com.tns.ordermanagement.model.repo"
})
@EnableJpaAuditing
public class JpaApplicationConfig {

}
