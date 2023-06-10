package fr.dtent.gestsport.configuration;

import javax.management.relation.Role;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfiguration {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("springuser").password(passwordEncoder().encode("spring123")).roles("ROLE_USER")
            .and()
            .withUser("springadmin").password(passwordEncoder().encode("admin123")).roles("ROLE_ADMIN", "ROLE_USER");
    }
    
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
            .antMatchers("/admin").hasRole("ROLE_ADMIN")
            .antMatchers("/users").hasRole("ROLE_USER")
            .anyRequest().authenticated()
            .and()
            .formLogin();
    }
}