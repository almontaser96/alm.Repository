package com.RESTService.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.RESTService.Exceptions.CustomAccessDeniedHandler;
import com.RESTService.Exceptions.CustomAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	JwtAuthenticationFilter jwtAuthFilter;
	
    private DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
		
		.authorizeHttpRequests()
		.requestMatchers(HttpMethod.POST,SecurityConstants.SIGN_UP_URL).hasAnyRole("MASTER","ADMIN")
		//.and().authorizeHttpRequests().requestMatchers(HttpMethod.POST,"/api/v1/users").permitAll()
		.and().authorizeHttpRequests().requestMatchers(HttpMethod.GET,SecurityConstants.SIGN_UP_URL).hasAnyRole("ADMIN","MASTER")
		.and().authorizeHttpRequests().requestMatchers(HttpMethod.GET,"/test/master/**").hasRole("MASTER")
		.and().authorizeHttpRequests().requestMatchers(HttpMethod.GET,"/test/admin/**").hasAnyRole("ADMIN","MASTER")
		.and().authorizeHttpRequests().requestMatchers(HttpMethod.GET,"/test/user/**").hasRole("USER")
		.and().authorizeHttpRequests().requestMatchers(HttpMethod.PUT,SecurityConstants.GRANT_ADMIN_URL).hasRole("MASTER")
		.and().authorizeHttpRequests().requestMatchers(HttpMethod.POST,SecurityConstants.Authenticate_URL)
		.permitAll().anyRequest().authenticated()
		.and().exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
		 .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
			.and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authenticationProvider(daoAuthenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
	
		return http.build();

	}
	/*@Bean
	SecurityFilterChain securityFilterChain0(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests().requestMatchers(HttpMethod.POST,"/users").hasAnyAuthority("ROLE_MASTER")
			.anyRequest().authenticated();
		return http.build();
	}*/
	@Bean
	 AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	DaoAuthenticationProvider authenticationProvider(){
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(bCryptPasswordEncoder);
		return authProvider;
	}
	/*@Bean
	 AuthenticationFailureHandler authenticationFailureHandler() {
	    return new CustomAuthenticationFailureHandler();
	} /*

	@Bean
	 AuthenticationSuccessHandler authenticationSuccessHandler() {
	   return new CustomAuthenticationSuccessHandler();
	}

	@Bean
	 AccessDeniedHandler accessDeniedHandler() {
	   return new CustomAccessDeniedHandler();
	} 
*/
}