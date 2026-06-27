package pe.edu.tecsup.lms.notifications.application.eventhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pe.edu.tecsup.lms.courses.domain.event.CourseCreatedEvent;
import pe.edu.tecsup.lms.courses.domain.event.CoursePublishedEvent;
import pe.edu.tecsup.lms.shared.domain.event.DomainEvent;
import pe.edu.tecsup.lms.shared.infrastructure.config.KafkaConfig;

@Slf4j
@Component
public class KafkaCourseEventHandler {

    @KafkaListener(
            topics = KafkaConfig.COURSE_EVENT_TOPIC,
            groupId = "notifications-group"
    )

    public void handleCourseEvents(DomainEvent event) {
        if (event instanceof CourseCreatedEvent e) {
            log.info("[Kafka] Course created event received: {}", e);
        } else if (event instanceof CoursePublishedEvent e) {
            log.info("[Kafka] Course published event received: {}", e);
        } else {
            throw new RuntimeException("Invalid event type " + event.getClass());
        }
    }
}