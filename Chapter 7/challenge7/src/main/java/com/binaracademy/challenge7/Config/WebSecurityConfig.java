package com.binaracademy.challenge7.Config;

import com.binaracademy.challenge7.Service.LoginRegisterService;
import com.binaracademy.challenge7.Service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    LoginRegisterService loginRegisterService;

    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/**", "/api/auth/**", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/webjars/**", "/error").permitAll( )
 //               .antMatchers("/api/merchant/**").hasAuthority(ERole.ROLE_MERCHANT.name())
 //               .antMatchers("/api/product/**").hasAuthority(ERole.ROLE_MERCHANT.name())
 //               .antMatchers("/api/user/**").hasAuthority(ERole.ROLE_CUSTOMER.name())
 //               .antMatchers("/api/orders/**").hasAuthority(ERole.ROLE_CUSTOMER.name())
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login().successHandler((request, response, authentication) -> {
                    log.info("logged in as user : {}", authentication.getPrincipal());
                    if(!loginRegisterService.loginOauth2User(authentication).isPresent()) {
                        loginRegisterService.registerOauth2User(authentication);
                    };
                })
                .and()
//                .csrf(c -> c
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .logout(l -> l.logoutSuccessUrl("/"));
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}