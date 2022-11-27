package com.uroom.backend.auth.configuration;

import com.uroom.backend.auth.jwt.AuthEntryPointJwt;
import com.uroom.backend.auth.jwt.AuthTokenFilter;
import com.uroom.backend.auth.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;


    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    private static final String[] publicResources = new String[]
            {
                    "/oauth/token",
                    "/oauth/authorize**",
                    "/sign-up",
                    "/log-in",
                    "/get-users",
                    "/get-services",
                    "/get-rules",
                    "/change-active",
                    "/test-favorite",
                    "/add-question",
                    "/remove-question",
                    "/update-question",
                    "/update-answer",
                    "/test-question",
                    "/testNotification",
                    "/test-rent",
                    "/get-post**"
            };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers(publicResources).permitAll()
                .anyRequest().authenticated() // Esto se va luego
                //.antMatchers("/**").hasRole("STUDENT")
                //.antMatchers("/**").hasRole("OWNER")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("https://github.com/rcalvom/help-desk/blob/master/src/main/java/com/helpdesk/HelpDesk/Configuration/ConfigurationImpl.java")
                    .invalidateHttpSession(true);
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(final WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.OPTIONS);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
