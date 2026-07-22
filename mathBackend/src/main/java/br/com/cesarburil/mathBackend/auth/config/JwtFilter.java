package br.com.cesarburil.mathBackend.auth.config;

import br.com.cesarburil.mathBackend.auth.service.JwtService;
import br.com.cesarburil.mathBackend.auth.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private UserService userService;

    private JwtService jwtService;


    public JwtFilter(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String username = null;
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            token = header.substring(7);

            username = jwtService.getUsernameFromToken(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails user = userService.loadUserByUsername(username);

                if (jwtService.validateToken(token, user)) {

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            }


        }

        filterChain.doFilter(request, response);

    }


}
