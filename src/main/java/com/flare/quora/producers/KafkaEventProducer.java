package com.flare.quora.producers;

import com.flare.quora.configuration.KafkaConfig;
import com.flare.quora.events.ViewCountEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishViewCountEvent(ViewCountEvent viewCountEvent) {

        kafkaTemplate.send(KafkaConfig.TOPIC_NAME, viewCountEvent.getTargetId(),viewCountEvent)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        System.err.println("Failed to send message: " + ex.getMessage());
                    } else {
                        System.out.println("Message sent successfully: " + result.getProducerRecord().value());
                    }
                });
    }
}
