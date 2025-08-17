package com.flare.quora.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerRequestDTO {

    @NotBlank(message = "Content cannot be blank")
    @Size(min = 10, max = 1000, message = "Content cannot exceed 1000 characters")
    private String content;

    @NotBlank(message = "Question ID cannot be blank")
    private String questionId;
}
