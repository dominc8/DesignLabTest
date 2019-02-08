package de.jonashackt.springbootvuejs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


	@Autowired
	private DataSource dataSource;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.jdbcAuthentication().dataSource(dataSource)
        .usersByUsernameQuery("select username, password, enabled"
            + " from public.security_users where username=?")
        .authoritiesByUsernameQuery("select username, authority "
            + "from public.security_users where username=?")
        .passwordEncoder(new BCryptPasswordEncoder());
  }

	private static String REALM="APP_REALM";

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, "/checkuser").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "/api/**").hasAnyRole("USER", "ADMIN")
				.antMatchers("/api/**").hasRole("ADMIN")
				.and()
				.httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);// Authenticate users with HTTP basic authentication
 	}
	
	@Bean
	public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
		return new CustomBasicAuthenticationEntryPoint();
	}
	
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

}
