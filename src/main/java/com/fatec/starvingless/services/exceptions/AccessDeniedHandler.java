package com.fatec.starvingless.services.exceptions;

import com.fatec.starvingless.security.ServletUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler{

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException ex) throws IOException {

            Authentication auth
                    = SecurityContextHolder.getContext().getAuthentication();

            if (auth != null) {

                String json = ServletUtil.getJson("error", "Access denied");
                ServletUtil.write(response, HttpStatus.FORBIDDEN, json);
            }
    }
}
