package com.debait.debait.config;

import com.debait.debait.auth.JwtAccessDeniedHandler;
import com.debait.debait.auth.JwtAuthFilter;
import com.debait.debait.auth.JwtAuthenticationEntryPoint;
import com.debait.debait.auth.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthFilter jwtAuthFilter;

    // PasswordEncoder는 BCryptPasswordEncoder를 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(withDefaults())
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/user/**").permitAll())
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/room/**").permitAll())
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/rule/**").permitAll())
//            .authorizeHttpRequests(
//                    authorize -> authorize.requestMatchers("/api/members/**").permitAll())
//            .authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/maps/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll())
//            .authorizeHttpRequests(
//                    authorize -> authorize.requestMatchers("/api/report/admin").hasRole("ADMIN"))
//            .authorizeHttpRequests(
//                    authorize -> authorize.requestMatchers("/api/members/admin").hasRole("ADMIN"))
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());

        httpSecurity.addFilterAfter(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

}