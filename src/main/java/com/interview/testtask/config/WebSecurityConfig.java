package com.interview.testtask.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity(debug = true)
@Configuration
@AllArgsConstructor
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private AuthProvider authProvider;

    @Autowired
    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/greeting", "/login" )
                        .permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/order/**").hasRole("COURIER")
                    .antMatchers("/order-manage/**").hasRole("OPERATOR")

                .anyRequest()
                        .authenticated()
                .and()
                    .authenticationProvider(authProvider)
                        .formLogin()
                            .loginPage("/login")
                                .permitAll()
                                    .successForwardUrl("/success")
                .and().rememberMe()
                .and() .logout().permitAll()
                .and().csrf().disable()
        ;

    }
}
