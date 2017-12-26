package com.example.app.config;

import com.example.app.daos.BootDaoAuthenticationProvider;
import com.example.app.daos.IMatcherDAO;
import com.example.app.daos.MatcherDAO;
import com.example.app.services.MvcUserDetailsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@PropertySource("classpath:application.properties")
public class MvcConfig extends WebMvcConfigurerAdapter {
    private final Logger logger = Logger.getLogger(this.getClass());

    @Value("${spring.datasource.driver-class-name}")
    private String jdbcDriverClassName;
    @Value("${spring.datasource.url}")
    private String jdbcUrl;
    @Value("${spring.datasource.username}")
    private String jdbcUsername;
    @Value("${spring.datasource.password}")
    private String jdbcPassword;

    @Bean(name = "mySqlDataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(jdbcDriverClassName);
        driverManagerDataSource.setUrl(jdbcUrl);
        driverManagerDataSource.setUsername(jdbcUsername);
        driverManagerDataSource.setPassword(jdbcPassword);
        logger.info("Connected to DB: " + jdbcUrl);
        return driverManagerDataSource;

    }

    @Bean(name = "userDetailsService")
    public UserDetailsService userDetailsService() {
        MvcUserDetailsService jdbcImpl = new MvcUserDetailsService();
        jdbcImpl.setDataSource(dataSource());
        logger.info("UserDetailsService used: " + jdbcImpl.getClass());
        return jdbcImpl;
    }

    @Bean(name = "bootDaoAuthenticationProvider")
    public BootDaoAuthenticationProvider bootDaoAuthenticationProvider() {
        BootDaoAuthenticationProvider provider = new BootDaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        logger.info("UserDetailsAuthenticationProvider used: " + provider.getClass());
        return provider;
    }

    @Bean(name = "matcherDAO")
    public IMatcherDAO matcherDAO() {
        return new MatcherDAO();
    }

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}