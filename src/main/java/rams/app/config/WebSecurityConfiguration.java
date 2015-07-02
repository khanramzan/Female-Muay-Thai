package rams.app.config;

import org.neo4j.cypher.internal.compiler.v2_1.ast.rewriters.deMorganRewriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import rams.app.security.UserRepositoryUserDetailsService;

@Configuration
@EnableWebMvcSecurity
@ComponentScan(basePackageClasses = UserRepositoryUserDetailsService.class)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
	
	@Override
	protected void configure(final AuthenticationManagerBuilder auth)
			throws Exception {
		auth.authenticationProvider(authProvider());
	}

	
	
	// @formatter:off
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
			.antMatchers("/resources/**", "/signup","/successRegister",
					"/user/resetpassword","/forgotPassword","/user/changePassword"
					,"/user/savePassword","/user/updatePassword","/user/registration"
					,"/regitrationConfirm","/user/resendRegistrationToken"
					,"/testPage","/fmtkb/**")
			.permitAll().antMatchers("/invalidSession*").anonymous()
				.anyRequest().authenticated().and().formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/home")
				
				.failureUrl("/login-error")
				.successHandler(myAuthenticationSuccessHandler)
				.usernameParameter("username")
				.passwordParameter("password").permitAll().and()
				.sessionManagement().invalidSessionUrl("/invalidSession")
				.sessionFixation().none().and().logout().logoutSuccessUrl("/fmtkb/greeting")
				.invalidateHttpSession(false)
				
				.deleteCookies("JSESSIONID").permitAll();
		// @formatter:on
	}

	// @formatter:on

	// @formatter:off
	@Autowired
	public void registerAuthentication(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService);
		
	}
	// @formatter:on
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}
	
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11);
	}

	
}