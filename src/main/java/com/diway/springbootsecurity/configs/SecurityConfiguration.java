package com.diway.springbootsecurity.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Set in-memory configuration on the auth object
        /*auth.inMemoryAuthentication()
                .withUser("blah").password("blah").roles("USER")
                .and()
                .withUser("foo").password("foo").roles("ADMIN");*/

        // Using H2 database with default dataSource & default schema and creating bunch of users
        /*auth.jdbcAuthentication()
                .dataSource(dataSource)
                .withDefaultSchema()
                .withUser(User.withUsername("user").password("pass").roles("USER"))
                .withUser(User.withUsername("admin").password("pass").roles("ADMIN"));*/

        // Using H2 with my schema (refer files schema.sql & data.sql)
        /*auth.jdbcAuthentication()
                .dataSource(dataSource);
                // if you are not using default table & column names - you can pass queries that Spring Security should run on your tables
                .usersByUsernameQuery("select username,password,enabled from my_users where username = ?")
                .authoritiesByUsernameQuery("select username,authority from my_authorities where username = ?");*/

        // Using JPA(UserDetailsService) to connect to dataSource(MySQL)
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // Order should always be from most restrictive to least restrictive
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/").permitAll()
                .and()
                .formLogin();
    }
}
