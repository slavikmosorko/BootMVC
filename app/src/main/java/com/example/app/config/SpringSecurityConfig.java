package com.example.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("mySqlDataSource")
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExceptionHandlingConfigurer<HttpSecurity> httpSecurityExceptionHandlingConfigurer = http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/").access("hasRole('ROLE_USER')")
                .antMatchers("/messages/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
                .rememberMe()
                .rememberMeParameter("remember-me")
                .rememberMeCookieName("spring-boot-remember-me-cookie")
                .tokenValiditySeconds(24 * 60 * 60)
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access_denied");
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username,password, enabled from users where username=?")
                .authoritiesByUsernameQuery(
                        "select username, role from user_roles where username=?");
    }
}
