package com.flare.quora.dto;


import java.time.LocalDateTime;

public class LikeResponseDTO {
    private String id;

    private String targetId;

    private String targetType;

    private boolean isLike;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
