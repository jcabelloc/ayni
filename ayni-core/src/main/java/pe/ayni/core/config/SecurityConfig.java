package pe.ayni.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import pe.ayni.core.seguridad.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
		.authorizeRequests()
		//.antMatchers("/api/**").permitAll() // 2) front end + rest api version
		.antMatchers("/api/**").authenticated() // 1) single server version
		.antMatchers("/reportes/**").authenticated() // 1) single server version
		.antMatchers("/oauth2callback/**").authenticated() // 1) single server version
		.and()
		.httpBasic().and()
		//.csrf().disable()  // 2) front end + rest api version
		.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // 1) single server version
		;
	}
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
}
