package com.kkh.jangbogimanager.config;

import com.kkh.jangbogimanager.member.CustomMemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private final CustomMemberService customMemberService;

	public SecurityConfig(CustomMemberService customMemberService) {
		this.customMemberService = customMemberService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		System.out.println("!!!!securityfilter!!!!");
		http
				.authenticationManager(authenticationManager())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/member/login", "/member/signup", "/css/**", "/js/**", "/img/**").permitAll() // 로그인 페이지, 정적 리소스 허용
						.anyRequest().authenticated() // 그 외 요청은 로그인 필요
				)
				.formLogin(login -> login
						.loginPage("/login") // 로그인 페이지 지정
						.loginProcessingUrl("/login")
						.defaultSuccessUrl("/ledger/home", true) // 로그인 성공 후 이동할 페이지
						.permitAll()
				)
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID", "XSRF-TOKEN")
						.permitAll()
				)
				.csrf(csrf -> csrf
						.ignoringRequestMatchers("/idcheck", "/member/signup") // 특정 URL에 대해 CSRF 보호 비활성화
				)
				.exceptionHandling(exception -> exception
						.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/member/login"))
				);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		System.out.println("!!!!passwordEncoder!!!!");
		return new BCryptPasswordEncoder();  // BCryptPasswordEncoder를 사용하여 비밀번호 암호화
	}
	@Bean
	public UserDetailsService userDetailsService() {
		System.out.println("!!!!userDetailsService!!!!");
		return customMemberService;
	}

	@Bean
	AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(customMemberService);
		provider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(provider);
	}
}
