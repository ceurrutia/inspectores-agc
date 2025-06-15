package com.agc.inspectores.security;

import com.agc.inspectores.service.CustomUserDetailsService;
import com.agc.inspectores.service.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                                // Vistas públicas
                                .requestMatchers("/auth/login").permitAll()

                                // Permisos para inspectores
                                .requestMatchers(HttpMethod.GET, "/api/inspectores/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/inspectores/**").hasAnyRole("ADMIN", "SUPERADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/inspectores/**").hasAnyRole("ADMIN", "SUPERADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/inspectores/**").hasAnyRole("ADMIN", "SUPERADMIN")

                                 // Otros endpoints
                                .requestMatchers("/api/**").authenticated()

                                // Vistas protegidas por rol
                                .requestMatchers("/admin-dashboard").hasRole("ADMIN")
                                .requestMatchers("/auth/admin/create").hasRole("SUPERADMIN")

                                // Cualquier otra necesita estar autenticado
                                .anyRequest().authenticated()
                )
                // Habilita formulario solo para vistas web
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/auth/dashboard", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login?logout")
                        .permitAll()
                )
                //solo las APIs serán stateless
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                // JWT se aplica solo si hay token
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

