package iss.tim4.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import iss.tim4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userDetails;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if (request.getRequestURL().toString().contains("/api/")) {
            System.out.println("####" + request.getMethod() + ":" + request.getRequestURL());
            System.out.println("#### Authorization: " + request.getHeader("Authorization"));
            String requestTokenHeader = request.getHeader("Authorization");
            String username = null;
            String jwtToken = null;
            if (requestTokenHeader != null && requestTokenHeader.contains("Bearer")) {
                jwtToken = requestTokenHeader.substring(requestTokenHeader.indexOf("Bearer ") + 7);
                System.out.println(">>>>>JWT TOKEN: " + jwtToken);
                try {
                    username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                    UserDetails userDetails = this.userDetails.loadUserByUsername(username);
                    if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        System.out.println("Username: " + userDetails.getUsername() + ", role: " + userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Unable to get JWT Token.");
                } catch (ExpiredJwtException e) {
                    System.out.println("JWT Token has expired.");
                } catch (MalformedJwtException e) {
                    System.out.println("Bad JWT Token.");
                }
            } else {
                logger.warn("JWT Token does not exist.");
            }
        }
        chain.doFilter(request, response);
    }
}