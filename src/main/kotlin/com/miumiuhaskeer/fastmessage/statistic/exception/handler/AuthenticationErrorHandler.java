package com.miumiuhaskeer.fastmessage.statistic.exception.handler;

import com.miumiuhaskeer.fastmessage.statistic.JsonConverter;
import com.miumiuhaskeer.fastmessage.statistic.model.response.ResponseEntityBuilder;
import com.miumiuhaskeer.fastmessage.statistic.properties.bundle.ErrorBundle;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class copied and modified from FastMessage project
 */
@RequiredArgsConstructor
public class AuthenticationErrorHandler implements AuthenticationEntryPoint {

    private final JsonConverter converter;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        ResponseEntityBuilder.SimpleResponse simpleResponse = new ResponseEntityBuilder.SimpleResponse(
                HttpStatus.UNAUTHORIZED.value(),
                ErrorBundle.get("error.authenticationError.message")
        );

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().append(converter.toJson(simpleResponse));
    }
}
