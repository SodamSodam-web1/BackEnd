package goormton.backend.sodamsodam.global.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/places/**/reviews" //TODO: 리뷰 작성 임시 테스트용, 추후 jwt 적용 후 제거 예정
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin().disable()
                .httpBasic().disable()
                .csrf().disable();

        return http.build();
    }
}
