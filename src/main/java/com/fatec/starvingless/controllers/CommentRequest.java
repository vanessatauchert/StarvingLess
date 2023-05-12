package com.fatec.starvingless.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    @NotBlank
    @Size(max = 250, message = "Description must be between 10 and 250 chars")
    private String description;

    @NotBlank(message = "Required field")
    private String createDate;

    @NotNull
    private Long postId;

    @NotNull
    private Long userId;
}
