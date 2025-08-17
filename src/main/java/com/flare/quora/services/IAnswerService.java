package com.flare.quora.services;

import com.flare.quora.dto.AnswerResponseDTO;
import com.flare.quora.dto.QuestionRequestDTO;
import com.flare.quora.dto.QuestionResponseDTO;
import reactor.core.publisher.Mono;

public interface IAnswerService {

    Mono<AnswerResponseDTO> createQuestion(AnswerResponseDTO answerResponseDTO);

    Mono<AnswerResponseDTO> getQuestionById(String questionId);

}
