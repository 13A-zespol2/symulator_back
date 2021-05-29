package psk.isi.simulator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/login").permitAll()
                .anyRequest().authenticated().and()
                .logout().permitAll();
    }

    /*@Bean
    @Override
    public UserDetailsService userDetailsService() {
        User.withDefaultPasswordEncoder()
                .username("phoneNumber")
                .password("pin")
                .roles("USER").build();
    }
*/

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/", "/configuration/security", "/swagger-ui.html", "/webjars/");
    }
}
