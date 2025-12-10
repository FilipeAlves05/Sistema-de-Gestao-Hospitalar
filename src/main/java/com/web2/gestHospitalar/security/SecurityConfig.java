package com.web2.gestHospitalar.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Rotas públicas - acessíveis sem autenticação
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**", "/error").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                
                // Rotas da API REST - requerem autenticação
                .requestMatchers("/api/**").authenticated()
                
                // Rotas específicas por role
                .requestMatchers("/medicos/**").hasAnyRole("ADMIN", "MEDICO")
                .requestMatchers("/recepcionistas/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
                .requestMatchers("/pacientes/**").hasAnyRole("ADMIN", "MEDICO", "RECEPCIONISTA")
                .requestMatchers("/consultas/**").hasAnyRole("ADMIN", "MEDICO", "RECEPCIONISTA")
                .requestMatchers("/exames/**").hasAnyRole("ADMIN", "MEDICO")
                .requestMatchers("/prescricoes/**").hasAnyRole("ADMIN", "MEDICO")
                .requestMatchers("/medicamentos/**").hasAnyRole("ADMIN", "MEDICO")
                .requestMatchers("/estoques/**").hasAnyRole("ADMIN")
                
                // Página inicial acessível para usuários autenticados
                .requestMatchers("/", "/home").authenticated()
                
                // Todas as outras rotas requerem autenticação
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .exceptionHandling(exception -> exception
                .accessDeniedPage("/access-denied")
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
