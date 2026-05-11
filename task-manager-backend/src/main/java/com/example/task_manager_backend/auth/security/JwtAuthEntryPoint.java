package com.example.task_manager_backend.auth.security;

import com.example.task_manager_backend.common.Exception.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;


@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(@NonNull HttpServletRequest request,
                         @NonNull HttpServletResponse response,
                         @NonNull AuthenticationException e)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        String message;

        if(e instanceof BadCredentialsException) {
            message = "Złe dane logowania";
        } else if (e instanceof InsufficientAuthenticationException) {
            message = "Brak lub niepoprawny token";
        } else{
            message = "Brak autoryzacji";
            message = "Brak autoryzacji";
        }

        ApiError apiError = new ApiError(401, message);

        response.getWriter().write(new ObjectMapper().writeValueAsString(apiError));
    }



}
