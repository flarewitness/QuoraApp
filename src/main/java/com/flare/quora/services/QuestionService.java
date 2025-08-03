package com.flare.quora.services;

import com.flare.quora.dto.QuestionRequestDTO;
import com.flare.quora.dto.QuestionResponseDTO;
import com.flare.quora.mapper.QuestionMapper;
import com.flare.quora.models.Question;
import com.flare.quora.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService{

    private final QuestionRepository questionRepository;

    @Override
    public Mono<QuestionResponseDTO> createQuestion(QuestionRequestDTO questionRequestDTO) {
        Question question = Question.builder().
                title(questionRequestDTO.getTitle()).
                content(questionRequestDTO.getContent()).
                createdAt(LocalDateTime.now()).
                updatedAt(LocalDateTime.now()).
                build();

        return questionRepository.save(question)
                .map(QuestionMapper::toQuestionResponseDTO)
                .doOnSuccess(q -> System.out.println("Question created: " + q.getId()))
                .doOnError(e -> System.err.println("Error creating question: " + e.getMessage()));
    }
}
