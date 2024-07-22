package com.example.movies.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Value;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${auth0.audience}")
    private String audience;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.GET, "/api/videos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/videos/{videoId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/videos/{videoId}/comments").permitAll()
                        .anyRequest().authenticated()
                )
                // Don't store any user information or session data between requests
                // In REST APIs each request should be self-contained
                // and provide all necessary information
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // This ensures that Spring Security applies the CORS configuration
                // It controls which domains can make requests to your server
                // Default configuration permits all origins
                .cors(withDefaults())
                // For authenticated endpoints it expects requests to
                // include a JWT token for authentication and validates the token
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(withDefaults()));
            return http.build();
    }

    /**
     * Ensures that any JWT token application processes has been issued
     * by a trusted issuer, contains the correct audience claim
     * and is intended for application
     *
     * @return a configured JwtDecoder instance
     */
    @Bean
    JwtDecoder jwtDecoder() {
        OAuth2TokenValidator<Jwt> withAudience = new AudienceValidator(audience);
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        OAuth2TokenValidator<Jwt> validator = new DelegatingOAuth2TokenValidator<>(withAudience, withIssuer);

        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer);
        jwtDecoder.setJwtValidator(validator);
        return jwtDecoder;
    }
}
