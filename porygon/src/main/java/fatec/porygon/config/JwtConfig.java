package fatec.porygon.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JwtConfig extends OncePerRequestFilter {
    private final String SECRET = "porygon123";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.replace("Bearer ", "");
            try{
                DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET))
                        .acceptExpiresAt(5) // Garantindo que o token não esteja expirado
                        .build()
                        .verify(token);

                String username = decodedJWT.getSubject();
                List<String> roles = decodedJWT.getClaim("roles").asList(String.class); // Extrai as roles


                if (username != null) {
                        List<GrantedAuthority> authorities = roles.stream()
                                .map(SimpleGrantedAuthority::new) // Mapeia as roles para GrantedAuthority
                                .collect(Collectors.toList());

                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        new User(username, "", authorities),
                                        null, authorities
                                );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }else{
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Código 401
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Token inválido ou ausente\"}");
                    return;
                }
            } catch (JWTVerificationException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Código 401
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Token inválido ou ausente\"}");
                return;
            }
        }else{
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Código 401
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token inválido ou ausente\"}");
            return;
        }
        chain.doFilter(request, response);
    }
}
