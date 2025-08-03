package com.flare.quora.services;

import com.flare.quora.dto.QuestionRequestDTO;
import com.flare.quora.dto.QuestionResponseDTO;
import reactor.core.publisher.Mono;

public interface IQuestionService {

    Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO);
}
