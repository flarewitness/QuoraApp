package com.flare.quora.services;

import com.flare.quora.dto.QuestionRequestDTO;
import com.flare.quora.dto.QuestionResponseDTO;
import com.flare.quora.events.ViewCountEvent;
import com.flare.quora.mapper.QuestionMapper;
import com.flare.quora.models.Question;
import com.flare.quora.producers.KafkaEventProducer;
import com.flare.quora.repositories.QuestionRepository;
import com.flare.quora.utils.CursorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService{

    private final QuestionRepository questionRepository;

    private final KafkaEventProducer kafkaEventProducer;

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

    @Override
    public Mono<QuestionResponseDTO> getQuestionById(String questionId) {
        return questionRepository.findById(questionId)
                .map(QuestionMapper::toQuestionResponseDTO)
                .doOnSuccess(q -> {
                    System.out.println("Question retrieved: " + q.getId());
                    ViewCountEvent viewCountEvent = ViewCountEvent.builder()
                            .targetId(q.getId())
                            .targetType("question")
                            .timestamp(LocalDateTime.now())
                            .build();
                    kafkaEventProducer.publishViewCountEvent(viewCountEvent);
                })
                .doOnError(e -> System.err.println("Error retrieving question: " + e.getMessage()));
    }

    @Override
    public Flux<QuestionResponseDTO> getAllQuestions(String cursor, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        if(!CursorUtils.isValidCursor(cursor)){
            return questionRepository.findTop10ByOrderByCreatedAtAsc()
                    .take(limit)
                    .map(QuestionMapper::toQuestionResponseDTO)
                    .doOnComplete(() -> System.out.println("Top 10 questions retrieved"))
                    .doOnError(e -> System.err.println("Error retrieving top 10 questions: " + e.getMessage()));
        }
        else {
            LocalDateTime cursorDateTime = CursorUtils.parseCursor(cursor);
            return questionRepository.findByCreatedAtGreaterThanOrderByCreatedAtAsc(cursorDateTime, pageable)
                    .map(QuestionMapper::toQuestionResponseDTO)
                    .doOnComplete(() -> System.out.println("All questions retrieved"))
                    .doOnError(e -> System.err.println("Error retrieving all questions: " + e.getMessage()));
        }
    }

    @Override
    public Flux<QuestionResponseDTO> searchQuestions(String searchTerm, int offset, int limit) {
        return questionRepository.findByTitleOrContentContainingIgnoreCase(searchTerm, PageRequest.of(offset, limit))
                .map(QuestionMapper::toQuestionResponseDTO)
                .doOnComplete(() -> System.out.println("Search completed for term: " + searchTerm))
                .doOnError(e -> System.err.println("Error searching questions: " + e.getMessage()));
    }
}
