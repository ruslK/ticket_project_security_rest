package com.ticketsproject.config;

import com.ticketsproject.servises.SecurityService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final SecurityService securityService;
    private final AuthSuccessHandler authSuccessHandler;

    public SecurityConfiguration(SecurityService securityService, AuthSuccessHandler authSuccessHandler) {
        this.securityService = securityService;
        this.authSuccessHandler = authSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/login", "/fragments/**", "/asserts/**", "/images/**")
                .permitAll()
                .antMatchers("welcome").authenticated()
                .antMatchers("/administrator/createUser").hasAuthority("Admin")
                .antMatchers("/administrator/createProject").hasAuthority("Manager")
                .antMatchers("/manager/**").hasAuthority("Manager")
                .antMatchers("/employee/**").hasAuthority("Employee")
                .and()
                .formLogin()                            // form login
                .loginPage("/login")
//                .defaultSuccessUrl("/welcome")
                .successHandler(authSuccessHandler)
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .and()
                .rememberMe()
                .tokenValiditySeconds(120)
                .key("ticketSecret")
                .userDetailsService(securityService);
    }
}
