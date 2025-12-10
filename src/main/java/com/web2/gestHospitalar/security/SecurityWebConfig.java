package com.web2.gestHospitalar.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityWebConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .headers(h -> h.frameOptions(f -> f.disable()))
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers("/h2-console/**").permitAll();

                auth.requestMatchers("/api/consultas/**").hasRole("MEDICO");
                auth.requestMatchers("/api/estoque/**").hasRole("FARMACEUTICO");
                auth.requestMatchers("/api/exames/**").hasAnyRole("MEDICO", "RECEPCIONISTA");
                auth.requestMatchers("/api/medicamentos/**").hasRole("FARMACEUTICO");
                auth.requestMatchers("/api/pacientes/**").hasAnyRole("PACIENTE", "RECEPCIONISTA", "MEDICO");
                auth.requestMatchers("/api/prescricoes/**").hasRole("MEDICO");
                auth.requestMatchers("/api/recepcionistas/**").hasRole("RECEPCIONISTA");

                auth.anyRequest().authenticated();
            })
            .formLogin(f -> f.permitAll())
            .logout(l -> l.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
