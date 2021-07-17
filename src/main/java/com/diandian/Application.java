package com.diandian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.DelegatingFilterProxyRegistrationBean;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.DelegatingFilterProxy;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		DelegatingFilterProxy a;
		FilterChainProxy b;
		//SpringSecurityFilterChain c;
		FilterChainProxy d;
		DefaultSecurityFilterChain e;
		DelegatingFilterProxyRegistrationBean f;
		DaoAuthenticationProvider dao;
		WebSecurityConfigurerAdapter web;
		//WebAsyncManagerIntegrationFilter  weba;
		//UsernamePasswordAuthenticationFilter upa;
		//DefaultLoginPageGeneratingFilter dl;
		ExceptionTranslationFilter aaa;
		FilterSecurityInterceptor bb;
		SecurityContextPersistenceFilter ddd;
		UsernamePasswordAuthenticationFilter upa;
		BasicAuthenticationFilter bat;
		ProviderManager pm;
		HttpSecurity hs;
		//InitializeAuthenticationProviderBeanManagerConfigurer iudb;
		AuthenticationConfiguration acf;
		
		//InitializeAuthenticationProviderBeanManagerConfigurer iap;
		SpringApplication.run(Application.class, args);
	}
}
