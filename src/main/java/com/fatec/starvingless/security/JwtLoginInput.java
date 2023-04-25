package com.fatec.starvingless.security;

import lombok.Data;

@Data
public class JwtLoginInput {
    private String email;
    private String password;
}
