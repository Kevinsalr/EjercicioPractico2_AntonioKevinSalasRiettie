package com.kevin.eventos.config;

import com.kevin.eventos.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioService usuarioService;
    private final BCryptPasswordEncoder passwordEncoder;

    public SecurityConfig(UsuarioService usuarioService,
                          BCryptPasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(usuarioService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth

                // Públicos
                .requestMatchers("/registro", "/guardarUsuario", "/css/**").permitAll()

                // ADMIN
                .requestMatchers("/usuarios/**").hasRole("ADMIN")
                .requestMatchers("/roles/**").hasRole("ADMIN")

                // Eventos
                .requestMatchers("/eventos").hasAnyRole("ADMIN", "ORGANIZADOR", "CLIENTE")
                .requestMatchers("/eventos/nuevo", "/eventos/guardar",
                                 "/eventos/editar/**", "/eventos/eliminar/**")
                .hasAnyRole("ADMIN", "ORGANIZADOR")

                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}