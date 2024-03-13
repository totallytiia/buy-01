package com.tiia.buy_01.security;

import com.tiia.buy_01.services.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private CorsHandler corsHandler;

	/*

	String[] unSecuredPaths = new String[] { "/login", "/register" };

	private AntPathRequestMatcher[] getAntPathRequestMatchers() {
		AntPathRequestMatcher[] requestMatchers = new AntPathRequestMatcher[unSecuredPaths.length];
		for (int i = 0; i < unSecuredPaths.length; i++) {
			requestMatchers[i] = new AntPathRequestMatcher(unSecuredPaths[i]);
		}
		return requestMatchers;
	}

	 */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // AntPathRequestMatcher[] requestMatchers = getAntPathRequestMatchers();
        return httpSecurity.csrf(csrf -> csrf.disable()).cors(cors -> cors.configurationSource(corsHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling((exception) -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .authorizeHttpRequests(
                        authorize -> authorize
                                //.requestMatchers(requestMatchers).anonymous()

                                .requestMatchers("/api/users/login", "/api/users/register/**").anonymous()
                                .requestMatchers("/api/users/loggedIn").permitAll()
                                // .requestMatchers("/api/products/allProducts", "/api/products/productDetails/**").permitAll()
                                .anyRequest().authenticated()

                ).addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
