package ru.bogdanov.clientservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.Objects;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private Environment env;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").hasRole("SYSTEM")
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    /*    http.authorizeRequests()
              //  .antMatchers("/").permitAll()
                .antMatchers("/**").hasRole("SYSTEM")
                        .and().formLogin().permitAll();

        http.csrf().disable();*/
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(Objects.requireNonNull(env.getProperty("system.username")))
                .password("{noop}"+ Objects.requireNonNull(env.getProperty("system.password")))
                .roles("SYSTEM");
    }
}
