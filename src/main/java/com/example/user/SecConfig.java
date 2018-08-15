package com.example.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecUserDetailsService secUserDetailsService;

    @Autowired
    public void registerGlobalAuthentification(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        authenticationManagerBuilder
                .userDetailsService(secUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .exceptionHandling()
                .accessDeniedPage("/loginNeeds")
                .and()
                .authorizeRequests()
                .antMatchers("/loginSuccess").authenticated()
                .antMatchers("/css/**", "/js/**", "/img/**", "/js/**").permitAll()
                .antMatchers("/*").permitAll()
                .anyRequest().denyAll()
                .and()
                .formLogin()
                .loginPage("/loginNeeds")
                .loginProcessingUrl("/login")
                .failureUrl("/profileError")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/loginSuccess")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logoutSuccess")
                .permitAll()
                .invalidateHttpSession(true);

    }

}
