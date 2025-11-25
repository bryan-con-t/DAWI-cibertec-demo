package pe.cibertec.cibertecdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // 1. Autenticación en memoria
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {

        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(encoder.encode("user123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    // 2. Encoder requerido por Spring Security
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 3. Configuración de rutas y permisos
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para Postman
                .authorizeHttpRequests(auth -> auth
                        // RUTAS PÚBLICAS ***********************
                        .requestMatchers(
                                "/api/public/**",
                                "/api/usuarios/registro",    // REGISTRO NO REQUIERE LOGIN
                                "/api/auth/login"            // LOGIN NO REQUIERE LOGIN
                        ).permitAll()

                        // RUTAS PROTEGIDAS ********************
                        .requestMatchers("/api/productos/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/api/listas/**").hasRole("USER")

                        // TODO LO DEMÁS REQUIERE AUTENTICACIÓN
                        .anyRequest().authenticated()
                )

                .httpBasic(Customizer.withDefaults()) // Personalización autenticación básica
                .build();
    }
}
