package com.miumiuhaskeer.fastmessage.statistic.exception.handler;

import com.miumiuhaskeer.fastmessage.statistic.exception.AuthenticationFailedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class copied and modified from FastMessage project
 */
public class AuthenticationErrorHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        throw new AuthenticationFailedException();
    }
}
