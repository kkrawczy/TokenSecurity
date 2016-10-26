package com.example.config;

import com.example.business.User;
import com.example.business.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiTokenFilter extends OncePerRequestFilter {
    public static final String TOKEN_HEADER = "X-Authorization";
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = retrieveTokenFromRequest(request);
        log.info("The token is: " + token);

        Optional<User> user = userRepository.getUserByToken(token);
        if (user.isPresent()) {
            SecurityContextHolder.getContext().setAuthentication(generateAuthenticationBasedOnUser(user.get()));
            filterChain.doFilter(request, response);
        } else {
            response.sendError(FORBIDDEN.value());
        }

    }

    private String retrieveTokenFromRequest(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader(TOKEN_HEADER);
    }

    private Authentication generateAuthenticationBasedOnUser(User user) {
        Authentication auth = new TestingAuthenticationToken("key", user.getName(), user.getGrantedAuthorities());
        auth.setAuthenticated(true);
        return auth;
    }
}