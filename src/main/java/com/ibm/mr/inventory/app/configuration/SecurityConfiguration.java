package com.ibm.mr.inventory.app.configuration;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
	
	 String[] resources = new String[]{
	                "/css/**","/icons/**","/images/**","/js/**","/layer/**","/webjars/**"
	        };
	 
	httpSecurity
	.csrf().disable()
         .authorizeRequests()
             //.antMatchers("/rest/*", "/home").permitAll()
             .antMatchers(resources).permitAll()
             .anyRequest().authenticated()
             .and()
         .formLogin()
             .loginPage("/loginpage.html").failureUrl("/login_error.html").defaultSuccessUrl("/ProductsView.html").permitAll()
             .and()
         .logout()
             .permitAll();
    }

//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//	
//
//    }


    @Bean
    @Override
    public UserDetailsService userDetailsService() {
	PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
	propertiesFactoryBean.setLocations(new ClassPathResource("usercredentials.properties"));
	Properties users = null;
	try {
	    propertiesFactoryBean.afterPropertiesSet();
	    users = propertiesFactoryBean.getObject();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return new InMemoryUserDetailsManager(users);
    }


}