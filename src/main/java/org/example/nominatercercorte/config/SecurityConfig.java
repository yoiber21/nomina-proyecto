package org.example.nominatercercorte.config;


import lombok.RequiredArgsConstructor;
import org.example.nominatercercorte.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig  {
// It defines how routes are secured, what filters are applied and how sessions are managed.

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    return httpSecurity
        .authorizeHttpRequests(authRequest -> authRequest
            .requestMatchers("/auth/**", "/h2A/**", "/swagger-ui/**",
                "/api-docs/**", "/fake/**", "/v3/api-docs/**",
                "docs/specs/**", "/favicon.ico",
                "/swagger-resources/**", "/swagger-resources",
                "swagger-ui.html", "/swagger-ui/**",
                "/v3/api-docs/public-apis", "/v3/api-docs/public-apis/**")
            .permitAll()
            .anyRequest().authenticated())
        .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .csrf(AbstractHttpConfigurer::disable)
        .headers(httpSecurityHeadersConfigurer -> {
          httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
        })
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .headers(httpSecurityHeadersConfigurer -> {
          httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
        })
        .build();
  }

}
