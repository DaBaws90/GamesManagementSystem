package com.example.AdrianCarrasco.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
		http.authorizeRequests()
			.antMatchers("/css/*", "/images/*", "/api/**").permitAll()
			.antMatchers("/home/*").permitAll()
			.antMatchers("/users/register").permitAll()
			.antMatchers("/users/signup").permitAll()
			.antMatchers("/noticias/details/*").permitAll()
			.antMatchers("/juegos/index/compras").permitAll()
			.antMatchers("/juegos/index/alquileres").permitAll()
			.antMatchers("/juegos/details/*").permitAll()
			.antMatchers("/competiciones/*").permitAll()
//			.antMatchers("/users/profile").hasRole("USER")
//			.antMatchers("/participaciones/add").hasRole("USER")
			
			.anyRequest().authenticated().and()
//			.anyRequest().permitAll().and()
			.formLogin().loginPage("/login").loginProcessingUrl("/signin")
			.usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/signin", true).permitAll().and()
			.logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll();
	}


}