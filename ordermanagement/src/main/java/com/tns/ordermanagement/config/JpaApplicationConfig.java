package com.tns.ordermanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.tns.ordermanagement.model.repo.BaseRepoImpl;

@Configuration
@EnableJpaRepositories(basePackages = {
		"com.tns.ordermanagement.model.entity",
		"com.tns.ordermanagement.model.repo"},
repositoryBaseClass = BaseRepoImpl.class)
@EnableJpaAuditing
public class JpaApplicationConfig {

}
