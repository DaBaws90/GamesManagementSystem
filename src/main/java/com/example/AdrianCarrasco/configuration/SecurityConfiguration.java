package com.example.AdrianCarrasco.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	@Qualifier("userDetailsServiceImpl")
	private UserDetailsService userDetailsService;
	
	@Autowired
	private void configureGlobal(AuthenticationManagerBuilder authentication) throws Exception{
		authentication.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/css/*", "/images/*").permitAll()
//			.antMatchers("/home/*").hasRole("ADMIN")
			.antMatchers("/users/register").permitAll()
			.antMatchers("/users/signup").permitAll()
			.anyRequest().authenticated().and()
//			.anyRequest().permitAll().and()
			.formLogin().loginPage("/login").loginProcessingUrl("/signin")
			.usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/signin", true).permitAll().and()
			.logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll();
	}


}