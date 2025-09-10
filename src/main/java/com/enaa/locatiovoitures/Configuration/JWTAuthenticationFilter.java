package com.enaa.locatiovoitures.Configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JWTAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        System.out.println("JWTAuthenticationFilter: doFilterInternal called for " + request.getRequestURI());
        System.out.println("Authorization Header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("JWTAuthenticationFilter: No Bearer token found or malformed header.");
            filterChain.doFilter(request, response);
            return;

        }
        jwt = authHeader.substring(7);
        System.out.println("JWT: " + jwt);
        userEmail = jwtService.extractUserName(jwt);
        System.out.println("Extracted User Email: " + userEmail);

        //ila kan 3ndna userEmail o had luser machi authenticated
        if (userEmail != null && (SecurityContextHolder.getContext().getAuthentication() == null || SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            System.out.println("User Email is not null and SecurityContextHolder is null or anonymous.");
            // kanakhdo user mn database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            System.out.println("User Details loaded: " + userDetails.getUsername() + " Authorities: " + userDetails.getAuthorities());
            //kantchikiw wach user valid ola la
            if (jwtService.isTokenValid(jwt, userDetails)) {
                System.out.println("Token is valid. Authenticating user.");
                //ila kan valid kandiro update authentication token
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                System.out.println("SecurityContextHolder updated.");

            } else {
                System.out.println("Token is NOT valid.");
            }
        } else {
            System.out.println("User Email is null OR SecurityContextHolder is already authenticated (not anonymous).");
        }
        filterChain.doFilter(request, response);

    }
}
