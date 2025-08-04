package com.flare.quora.controllers;

import com.flare.quora.dto.QuestionRequestDTO;
import com.flare.quora.dto.QuestionResponseDTO;
import com.flare.quora.services.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/questions")
public class QuestionController {

    @Autowired
    private IQuestionService questionService;

   @PostMapping
    public Mono<QuestionResponseDTO> createQuestion(@RequestBody QuestionRequestDTO requestDTO) {
       return questionService.createQuestion(requestDTO)
               .doOnSuccess(q -> System.out.println("Question created: " + q.getId()))
               .doOnError(e -> System.err.println("Error creating question: " + e.getMessage()));
    }

    @GetMapping("/{id}")
    public Mono<QuestionResponseDTO> getQuestionById(@PathVariable String id) {
        return questionService.getQuestionById(id)
                .doOnSuccess(q -> System.out.println("Question retrieved: " + q.getId()))
                .doOnError(e -> System.err.println("Error retrieving question: " + e.getMessage()));
   }

   @GetMapping
   public Flux<QuestionResponseDTO> getAllQuestions(
              @RequestParam(required = false) String cursor,
              @RequestParam(defaultValue = "10") int limit) {
       return questionService.getAllQuestions(cursor,limit)
               .doOnComplete(() -> System.out.println("All questions retrieved"))
               .doOnError(e -> System.err.println("Error retrieving all questions: " + e.getMessage()));
   }

   @GetMapping("/search")
   public Flux<QuestionResponseDTO> searchQuestions(
           @RequestParam String query,
           @RequestParam(defaultValue = "0") int offset,
           @RequestParam(defaultValue = "10") int limit) {
         return questionService.searchQuestions(query, offset, limit)
                .doOnComplete(() -> System.out.println("Search completed for term: " + query))
                .doOnError(e -> System.err.println("Error searching questions: " + e.getMessage()));
   }

   @GetMapping("/tag/{tag}")
   public Flux<QuestionResponseDTO> getQuestionsByTag(
           @PathVariable String tag,
           @RequestParam(defaultValue = "0") int page,
           @RequestParam(defaultValue = "10") int size) {
       throw new UnsupportedOperationException("Method not implemented yet");

   }

}
