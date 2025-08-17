package com.flare.quora.consumers;

import com.flare.quora.configuration.KafkaConfig;
import com.flare.quora.events.ViewCountEvent;
import com.flare.quora.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaEventConsumer {

    private final QuestionRepository questionRepository;

    @KafkaListener(
        topics = KafkaConfig.TOPIC_NAME,
        groupId = "view-count-consumer",
        containerFactory = "kafkaListenerContainerFactory")
    public void handleViewCountEvent(ViewCountEvent viewCountEvent) {
        questionRepository.findById(viewCountEvent.getTargetId())
                .flatMap(question -> {
                    Integer views = question.getViews();
                    question.setViews(views == null ? 0 : views + 1);
                    return questionRepository.save(question);
                })
                .subscribe(updatedQuestion -> {
                    System.out.println("Updated question views: " + updatedQuestion.getId() + " - New views: " + updatedQuestion.getViews());
                }, error -> {
                    System.err.println("Error updating question views: " + error.getMessage());
                });
    }
}
