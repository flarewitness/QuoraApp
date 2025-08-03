package com.flare.quora.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequestDTO {
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 10,max = 127, message = "Title cannot exceed 127 characters")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    @Size(min = 10,max = 1000, message = "Title cannot exceed 1000 characters")
    private String content;
}
