package pl.damian.demor.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.damian.demor.security.filter.JwtAuthenticationFilter;
import pl.damian.demor.security.filter.JwtAuthorizationFilter;
import pl.damian.demor.service.definition.AppUserService;

import javax.crypto.SecretKey;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebMvc
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends AbstractHttpConfigurer<SecurityConfiguration, HttpSecurity> {

    private final PasswordEncoder passwordEncoder;

    private final AppUserService userService;

    private final JwtConfiguration jwtConfiguration;

    private final SecretKey secretKeyAccessToken;

    private final SecretKey secretKeyRefreshToken;

    public SecurityConfiguration(AppUserService userService,
                                 PasswordEncoder passwordEncoder,
                                 JwtConfiguration jwtConfiguration,
                                 @Qualifier("secretKeyAccessToken") SecretKey secretKeyAccessToken,
                                 @Qualifier("secretKeyRefreshToken") SecretKey secretKeyRefreshToken) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtConfiguration = jwtConfiguration;
        this.secretKeyAccessToken = secretKeyAccessToken;
        this.secretKeyRefreshToken = secretKeyRefreshToken;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/api/auth/register").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .anyRequest().authenticated();

        http.addFilter(new JwtAuthenticationFilter(authenticationManager, jwtConfiguration, secretKeyAccessToken, secretKeyRefreshToken))
                .addFilterAfter(new JwtAuthorizationFilter(jwtConfiguration, secretKeyAccessToken), JwtAuthenticationFilter.class);

        http.sessionManagement().sessionCreationPolicy(STATELESS);

        return http.build();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);

    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userService;
    }


}
