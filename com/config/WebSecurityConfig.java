package com.config;

import com.domain.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
        auth.inMemoryAuthentication()
                .withUser(userBuilder.
                        username(Role.DIRECTOR.getLogin()).password(Role.DIRECTOR.getPassword()).roles(Role.DIRECTOR.name()))
                .withUser(userBuilder.
                        username(Role.HEAD.getLogin()).password(Role.HEAD.getPassword()).roles(Role.HEAD.name()))
                .withUser(userBuilder.
                        username(Role.SPECIALIST.getLogin()).password(Role.SPECIALIST.getPassword()).roles(Role.SPECIALIST.name()));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/")
                    .hasAnyRole(Role.DIRECTOR.name(),Role.HEAD.name(),Role.SPECIALIST.name())
                .antMatchers("/main")
                    .hasAnyRole(Role.DIRECTOR.name(),Role.HEAD.name(),Role.SPECIALIST.name())
                .antMatchers("/director")
                    .hasRole(Role.DIRECTOR.name())
                .antMatchers("/head")
                    .hasRole(Role.HEAD.name())
                .antMatchers("/specialist")
                    .hasRole(Role.SPECIALIST.name())
                .and().formLogin().permitAll();

    }
}
