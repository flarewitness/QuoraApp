package com.flare.quora.repositories;

import com.flare.quora.models.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;


@Repository
public interface QuestionRepository extends ReactiveMongoRepository<Question, String> {
    /**
     * Finds questions by title or content containing the specified search term, ignoring case.
     *
     * @param searchTerm the term to search for in the title or content
     * @return a Flux of Question objects that match the search criteria
     */
    @Query("{'$or': [{'title': {'$regex': ?0, '$options': 'i'}}, {'content': {'$regex': ?0, '$options': 'i'}}]}")
    Flux<Question> findByTitleOrContentContainingIgnoreCase(String searchTerm, Pageable pageable);

    /**
     * Finds questions created after a specific date, ordered by creation date in ascending order.
     *
     * @param cursor the date to compare against
     * @param pageable  pagination information
     * @return a Flux of Question objects that were created after the specified date
     */
    Flux<Question> findByCreatedAtGreaterThanOrderByCreatedAtAsc(LocalDateTime cursor, Pageable pageable);

    /**
     * Finds the top 10 questions ordered by creation date in ascending order.
     *
     * @return a Flux of the top 10 Question objects ordered by creation date
     */
    Flux<Question> findTop10ByOrderByCreatedAtAsc();
}
