package com.tomek.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // configure that every POST request requires authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().and()
                .logout().and()
                .csrf()
                // CookieCsrfTokenRepository saves token not in session but in cookie named XSRF-TOKEN
                // default flag withHttpOnly does not allow to read cookie in Js so it must be set as False
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST).authenticated();
    }

    // authorization with in-memory db for practicing purpose
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.inMemoryAuthentication()
               .withUser("User1").password("pass1").roles("USER").and()
               .withUser("User2").password("pass2").roles("USER");
    }
}
