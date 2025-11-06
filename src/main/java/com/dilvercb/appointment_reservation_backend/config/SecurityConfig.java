package com.dilvercb.appointment_reservation_backend.config;

import com.dilvercb.appointment_reservation_backend.config.filter.JwtTokenValidator;
import com.dilvercb.appointment_reservation_backend.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtUtils jwtUtils;
    @Value("${cors.allowed-origins}")
    private String allowedOrigins;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                )
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {

                    //Endpoints publicos
                    http.requestMatchers(HttpMethod.POST,"/auth/**").permitAll();
                    http.requestMatchers("/h2-console/**").permitAll();
                    http.requestMatchers(HttpMethod.GET,"/specialty/findAll").permitAll();
                    http.requestMatchers(HttpMethod.GET,"/doctor/findBySpecialty/{idSpecialty}").permitAll();
                    //http.requestMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll();

                    //Endpoints privados
                    http.requestMatchers(HttpMethod.GET,"/patient/findAll").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.GET,"/doctor/findAll").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.GET,"/user/admin").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.POST,"/doctor/save").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.POST,"/schedule/save").hasRole("DOCTOR");
                    //Resto de endpoints
                    http.anyRequest().denyAll();
                })
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(allowedOrigins));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
