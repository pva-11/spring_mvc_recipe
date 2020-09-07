package mvc.spring.example.recipe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers( "/login").permitAll()
                .antMatchers( "/","/recipes").hasAnyRole("USER", "ADMIN")
                .mvcMatchers("/recipe/{id}/view").hasAnyRole("USER", "ADMIN")
                .antMatchers("/recipe/**", "/ingredient/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("{noop}password")
                .permitAll()
            .and()
                .exceptionHandling().accessDeniedPage("/unauthorized")
            .and()
                .logout()
                .permitAll();
    }
}
