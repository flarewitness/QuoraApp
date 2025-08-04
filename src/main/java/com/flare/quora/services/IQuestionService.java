package com.flare.quora.services;

import com.flare.quora.dto.QuestionRequestDTO;
import com.flare.quora.dto.QuestionResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IQuestionService {

    Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO);

    Mono<QuestionResponseDTO> getQuestionById(String questionId);

    Flux<QuestionResponseDTO> getAllQuestions(String cursor, int limit);

    Flux<QuestionResponseDTO> searchQuestions(String searchTerm, int offset, int limit);
}
