package fatec.porygon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests ->
                    authorizeRequests
                            .requestMatchers("/auth/login").permitAll()
                            .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable()) // Desabilita CSRF para facilitar testes no Postman
            .addFilterBefore(jwtConfig(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig(); // Registra o filtro JWT
    }
}
          