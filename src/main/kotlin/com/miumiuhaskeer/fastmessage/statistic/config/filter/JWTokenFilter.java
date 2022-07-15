package com.miumiuhaskeer.fastmessage.statistic.config.filter;

import com.miumiuhaskeer.fastmessage.statistic.exception.AuthenticationFailedException;
import com.miumiuhaskeer.fastmessage.statistic.properties.bundle.ErrorBundle;
import com.miumiuhaskeer.fastmessage.statistic.util.JWTokenUtil;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

/**
 * Class copied and modified from FastMessage project
 */
@Component
@RequiredArgsConstructor
public class JWTokenFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JWTokenUtil jwTokenUtil;

    /**
     * Authenticate user by request
     *
     * @param request user request
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException if user not found
     * @throws AuthenticationFailedException if some error occurred
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getHeader(HttpHeaders.AUTHORIZATION) != null) {
            doAuthentication(request);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Authenticate user by HttpServletRequest
     *
     * @param request for auth
     */
    private void doAuthentication(HttpServletRequest request) {
        try {
            String token = parseToken(request);

            if (jwTokenUtil.validateToken(token)) {
                String email = jwTokenUtil.getEmailFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                authentication.setDetails(userDetails);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Parse token by request
     *
     * @param request to get token
     * @return result of parsing token
     * @throws ParseException if error occurred while parsing jwt token
     */
    private String parseToken(HttpServletRequest request) throws ParseException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!jwTokenUtil.headerIsToken(header)) {
            throw new ParseException(ErrorBundle.get("error.parseException.token.bearer.message"), 0);
        }

        return jwTokenUtil.getTokenFromHeader(header);
    }
}
