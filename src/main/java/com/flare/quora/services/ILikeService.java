package com.flare.quora.services;

import com.flare.quora.dto.LikeRequestDTO;
import com.flare.quora.dto.LikeResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ILikeService {

    Mono<LikeResponseDTO> createLike(LikeRequestDTO likeRequestDTO);
    Mono<LikeResponseDTO> countLikesByTargetIdAndTargetType(String targetId, String targetType);
    Mono<LikeResponseDTO> countDislikesByTargetIdAndTargetType(String targetId, String targetType);
    Mono<LikeResponseDTO> toggleLike(String targetId, String targetType, boolean isLike);

}
