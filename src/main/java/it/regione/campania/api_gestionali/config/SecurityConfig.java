package it.regione.campania.api_gestionali.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.POST, "/turismoweb/api-gestionali/v1/auth/login").permitAll()
            .requestMatchers("/v1/auth/**").permitAll()
            .requestMatchers("/v1/codici-istat/**").permitAll()
            .requestMatchers("/v3/api-docs/**").permitAll()
            .requestMatchers("/swagger-ui/**").permitAll()
            .requestMatchers("/swagger-ui.html").permitAll()
            .requestMatchers("/turismoweb/api-gestionali/v1/auth/**").permitAll()
            .requestMatchers("/turismoweb/api-gestionali/v1/codici-istat/**").permitAll()
            .requestMatchers("/turismoweb/api-gestionali/v3/api-docs/**").permitAll()
            .requestMatchers("/turismoweb/api-gestionali/swagger-ui/**").permitAll()
            .requestMatchers("/turismoweb/api-gestionali/swagger-ui.html").permitAll()
            .anyRequest().authenticated()
        )
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
   
    return http.build();
}

}