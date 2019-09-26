package intra.poleemploi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import intra.poleemploi.entities.AppUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // récupération username et pwd de l'utilisateur authentifié et retour a Spring security
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // jackson pour récupérer data envoyées en JSON
           AppUser appUser = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
           return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword()));
        } catch (IOException e) {
            e.printStackTrace();
            // si pb dans le corps de la requête, une exception est levée
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
       // récupère l'utilisateur authentifié
        User user = (User) authResult.getPrincipal();
        // déclarer une List de roles
        List<String> roles = new ArrayList<>();
        authResult.getAuthorities().forEach(auth -> {
            roles.add(auth.getAuthority());
        });

        String jwt = JWT.create()
                .withIssuer(request.getRequestURI()) // nom de l'autorité de l'application ayant généré le token
                .withSubject(user.getUsername()) // nom de l'utilisateur
                .withArrayClaim("roles", roles.toArray(new String[roles.size()])) // ajout des rôles
                .withExpiresAt(new Date(System.currentTimeMillis() + 10*24*3600)) // date d'expiration
                .sign(Algorithm.HMAC256("asBH56Ml1pWWuiopH45")); // signature + secret
        response.addHeader("Authorization", jwt);
    }
}
