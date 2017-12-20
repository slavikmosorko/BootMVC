package com.example.app.config;

import com.example.app.daos.BootDaoAuthenticationProvider;
import com.example.app.daos.IMatcherDAO;
import com.example.app.daos.MatcherDAO;
import com.example.app.daos.UserJdbcDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Bean(name = "mySqlDataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://den1.mysql6.gear.host/bootmvcdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Poland&useSSL=false");
        driverManagerDataSource.setUsername("bootmvcdb");
        driverManagerDataSource.setPassword("Zq5Sxazbd7~!");
        return driverManagerDataSource;

    }

    @Bean(name = "userDetailsService")
    public UserDetailsService userDetailsService() {
        UserJdbcDao jdbcImpl = new UserJdbcDao();
        jdbcImpl.setDataSource(dataSource());
        jdbcImpl.setEnableAuthorities(false);
        jdbcImpl.setEnableGroups(true);
        return jdbcImpl;
    }

    @Bean(name = "bootDaoAuthenticationProvider")
    public BootDaoAuthenticationProvider bootDaoAuthenticationProvider() {
        BootDaoAuthenticationProvider provider = new BootDaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
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