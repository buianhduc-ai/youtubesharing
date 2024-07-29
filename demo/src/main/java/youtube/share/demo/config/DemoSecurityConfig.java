package youtube.share.demo.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import youtube.share.demo.dao.UsersRepository;
import youtube.share.demo.ultilies.JwtTokenFilter;

@Configuration
@EnableWebSecurity
@SuppressWarnings("deprecation")
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UsersRepository usersRepository;
		
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	
	@Bean
	public PasswordEncoder passwordEncoder () {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(username -> usersRepository.findByUserName(username));
	}

	@Override @Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.exceptionHandling().authenticationEntryPoint(
					(request, response, ex) -> {
						response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
					}
				);
		
		http.authorizeRequests()
			.antMatchers("/auth/login").permitAll()
			.antMatchers("/api/register").permitAll()
			.antMatchers("/api/check").permitAll()
			.antMatchers("/api/videos/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.oauth2Login();
		
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		
	}
	
}
