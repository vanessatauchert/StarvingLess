package com.fatec.starvingless.services.exceptions;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final HttpServletRequest request;

    public CustomAccessDeniedHandler(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        System.out.println("CustomAccessDeniedHandler.handle() was called.");
        String uri = request.getRequestURI();
        System.out.println("URI: " + uri);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().append(json(uri));
    }

    private String json(String uri) {
        long date = new Date().getTime();
        return "{"
                + "\"timestamp\": " + date + ", "
                + "\"status\": 403, "
                + "\"error\": \"Acesso negado\", "
                + "\"message\": \"Você não tem permissão para acessar este recurso.\", "
                + "\"path\": \"" + uri + "\"}";
    }
    
}
