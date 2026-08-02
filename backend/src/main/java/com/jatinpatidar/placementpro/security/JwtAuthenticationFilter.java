package com.jatinpatidar.placementpro.security;

import com.jatinpatidar.placementpro.entity.User;
import com.jatinpatidar.placementpro.repository.UserRepository;
import com.jatinpatidar.placementpro.service.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserRepository userRepository
    ) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {


        // 1. Get Authorization header
        String authHeader = request.getHeader("Authorization");


        // 2. If no token, continue request
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }


        // 3. Extract token
        String token = authHeader.substring(7);


        // 4. Extract email from JWT
        String email = jwtService.extractEmail(token);


        // 5. Check user is not already authenticated
        if(email != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {


            // 6. Find user from database
            User user = userRepository.findByEmail(email)
                    .orElse(null);


            // 7. Validate token
            if(user != null && jwtService.isTokenValid(token, user)) {


                // 8. Create Authentication object
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                user.getAuthorities()
                        );


                // 9. Store authentication in SecurityContext
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            }
        }


        // 10. Continue request
        filterChain.doFilter(request, response);
    }
}
