package com.example.app.config;

import com.example.app.daos.IMatcherDAO;
import com.example.app.daos.MatcherDAO;
import com.example.app.models.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserDetailsService userDetailsService;
    private List<Matcher> matchersList = Collections.emptyList();

    @Bean(name = "matcherDAO")
    public IMatcherDAO matcherDAO() {
        return new MatcherDAO();
    }

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        matchersList = matcherDAO().getAllMatchers();
        if (!matchersList.isEmpty()) {
            matchersList.stream().forEach(
                    x -> {
                        try {
                            http.authorizeRequests().antMatchers(x.getUrl()).hasAuthority(x.getAccessRole());
                        } catch (Exception e) {
                            logger.error("Can't add matcher: " + x.getUrl());
                        }
                    }
            );
        }
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/images/**").permitAll()
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
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
