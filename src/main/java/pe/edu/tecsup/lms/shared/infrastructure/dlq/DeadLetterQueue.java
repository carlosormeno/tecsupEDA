package pe.edu.tecsup.lms.shared.infrastructure.dlq;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import pe.edu.tecsup.lms.shared.domain.event.DomainEvent;
import pe.edu.tecsup.lms.shared.infrastructure.config.KafkaConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class DeadLetterQueue {

    private final FailedEventRepository repository;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    // Usado por Spring Events handlers (@Retryable + @Recover)
    public void add(DomainEvent event, Exception exception) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            FailedEvent failedEvent = new FailedEvent();
            failedEvent.setEventId(event.getEventId());
            failedEvent.setEventType(event.getEventType());
            failedEvent.setPayload(payload);
            failedEvent.setMessage(exception.getMessage());
            failedEvent.setTimestamp(System.currentTimeMillis());
            repository.save(failedEvent);
            log.warn("Evento agregado a DLQ (H2): {}", event.getEventType());
        } catch (Exception e) {
            log.error("Error al guardar en DLQ: {}", e.getMessage());
        }
    }

    // Usado por @DltHandler de Kafka
    public void add(DomainEvent event, Exception exception, String originalTopic, long originalOffset) {
        add(event, exception); // persiste en H2

        Map<String, Object> dlqMessage = new HashMap<>();
        dlqMessage.put("eventId", event.getEventId());
        dlqMessage.put("eventType", event.getEventType());
        dlqMessage.put("originalTopic", originalTopic);
        dlqMessage.put("originalOffset", originalOffset);
        dlqMessage.put("error", exception.getMessage());
        dlqMessage.put("originalEvent", event);

        kafkaTemplate.send(KafkaConfig.DLQ_COURSE_EVENTS_TOPIC, event.getKey(), dlqMessage);
        log.warn("Evento enviado a Kafka DLQ: {} [{}]", event.getEventType(), event.getEventId());
    }

    public List<FailedEvent> getFailedEvents() {
        return repository.findAll();
    }
}
