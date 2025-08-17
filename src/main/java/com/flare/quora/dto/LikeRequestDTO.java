package com.flare.quora.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeRequestDTO {

    @NotBlank(message = "Target ID cannot be blank")
    private String targetId;

    @NotBlank(message = "Target type cannot be blank")
    private String targetType;

    @NotNull(message = "Like status cannot be null")
    private boolean isLike;
}
