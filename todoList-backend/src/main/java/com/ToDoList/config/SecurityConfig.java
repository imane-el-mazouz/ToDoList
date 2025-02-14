package com.ToDoList.config;

import com.gestion.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Bean
    public UserDetailsService userDetailsService() {
        return new AuthService();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

      http.csrf(AbstractHttpConfigurer::disable)
        .cors(withDefaults())
        .sessionManagement(sessionManagement ->
          sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authorizeHttpRequests(authorizeRequests ->
          authorizeRequests
                  .requestMatchers("/api/auth/**").permitAll()
//                  .requestMatchers("/api/**").permitAll()
                  .requestMatchers("/api/ordre/import-excel").permitAll()
                  .requestMatchers("/api/affaires").permitAll()
//                  .requestMatchers("/api/users/**").permitAll()
                  .requestMatchers("/api/mission/**").permitAll()
                  .requestMatchers("/api/mission-service/**").permitAll()
                  .requestMatchers("api/avances/**").permitAll()
                  .requestMatchers("api/avenants/**").permitAll()
                  .requestMatchers("api/mandataires/**").permitAll()
                  .requestMatchers("api/banques/**").permitAll()
                  .requestMatchers("api/cautions/**").permitAll()
                  .requestMatchers("api/charges-divers/**").permitAll()
                  .requestMatchers("api/client/**").permitAll()
                  .requestMatchers("api/charges/**").permitAll()
                  .requestMatchers("api/commentaires/**").permitAll()
                  .requestMatchers("api/division/**").permitAll()
                  .requestMatchers("api/imputation-jour/**").permitAll()
                  .requestMatchers("api/export/**").permitAll()
                  .requestMatchers("api/missions/**").permitAll()
                  .requestMatchers("api/pays/**").permitAll()
                  .requestMatchers("api/partenaires/**").permitAll()

                  .requestMatchers("api/pole/**").permitAll()
                  .requestMatchers("api/soustraitants/**").permitAll()
                  .requestMatchers("api/userclasses/**").permitAll()
                  .requestMatchers("api/usergrades/**").permitAll()
                  .requestMatchers("api/usergrades/**").permitAll()
                  .requestMatchers("/api/users/user/agents").hasAnyRole("CHEF_DE_PROJET", "CADRE_ADMINISTRATIF", "CHEF_DE_DIVISIONS", "CHEF_DE_POLE", "AGENT")
                  .requestMatchers("/api/users/by-division/{divisionId}").permitAll()
                  .requestMatchers("/api/ordre-mission/nextNumero").permitAll()
                  .requestMatchers("/api/ordre-mission/{id}/export").permitAll()
                  .requestMatchers("/api/note-de-frais/export/noteDeFrais/{id}").permitAll()
                  .requestMatchers("/api/note-de-frais/export/noteDeFrais/excel/{id}").permitAll()

                  // ... other configurations
                  .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                  .anyRequest().authenticated()

        );

      http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
      return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
      authenticationProvider.setUserDetailsService(userDetailsService());
      authenticationProvider.setPasswordEncoder(passwordEncoder());
      return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
      return config.getAuthenticationManager();
    }


}
