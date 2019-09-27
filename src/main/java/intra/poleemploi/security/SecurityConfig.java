package intra.poleemploi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // Spring Security se base sur interface UserDetailsService (fournie par Spring) pour gérer authentification
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // définit les utilisateurs qui ont accès à l'appli => permet de les authentifier
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
    // autorisations et filtre
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.formLogin(); // form d'authentification basé sur les sessions
        http.csrf().disable(); // ne plus générer le csrf (synchronizer token)
        // authentification STATELESS et non plus basée sur sessions
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // ouvrir le login à tout le monde - idem pour créer un compte, s'inscrire => pas besoin d'être authentifié
        http.authorizeRequests().antMatchers("/login/**", "/register/**").permitAll();
        // pour gestion des users et des roles il faut être ADMIN
        http.authorizeRequests().antMatchers("/appUsers/**", "/appRoles/**").hasAuthority("ADMIN");
        // pour le reste pas besoin d'être ADMIN mais nécessite d'être authentifié
        http.authorizeRequests().anyRequest().authenticated();
        // ajout du filtre JWTAuth pour générer le token
        http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
        http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
