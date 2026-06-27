package pe.edu.tecsup.lms.shared.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@Configuration
public class KafkaConfig {

    // Topics
    public static final String COURSE_EVENT_TOPIC = "course.events";
    public static final String DLQ_COURSE_EVENTS_TOPIC = "dlq.course.events";

    // SAGA
    public static final String ENROLLMENT_REQUEST_TOPIC = "enrollment.requested";

    @Bean
    public NewTopic courseEventTopic() {
        return new NewTopic(COURSE_EVENT_TOPIC, 3, (short) 1);
    }

    @Bean
    public NewTopic dlqCourseEventsTopic() {
        return TopicBuilder.name(DLQ_COURSE_EVENTS_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }

    // SAGA
    @Bean
    public NewTopic enrollmentRequestedTopic() {
        return TopicBuilder
                .name(ENROLLMENT_REQUEST_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
