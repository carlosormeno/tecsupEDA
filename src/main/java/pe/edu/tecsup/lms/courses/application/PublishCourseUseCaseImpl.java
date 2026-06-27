package pe.edu.tecsup.lms.courses.application;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.tecsup.lms.courses.domain.event.CoursePublishedEvent;
import pe.edu.tecsup.lms.courses.domain.exception.CourseNotFoundException;
import pe.edu.tecsup.lms.courses.domain.model.Course;
import pe.edu.tecsup.lms.courses.domain.repository.CourseRepository;
import pe.edu.tecsup.lms.shared.domain.event.EventPublisher;
import pe.edu.tecsup.lms.shared.domain.event.KafkaEventPublisher;
import pe.edu.tecsup.lms.shared.domain.event.RabbitMQEventPublisher;

import static pe.edu.tecsup.lms.shared.infrastructure.config.RabbitMQConfig.COURSE_PUBLISHED_ROUTING_KEY;

@Slf4j
@RequiredArgsConstructor
public class PublishCourseUseCaseImpl implements PublishCourseUseCase {

    private final CourseRepository repository;

    private final EventPublisher eventPublisher;

    private final RabbitMQEventPublisher rabbitMQEventPublisher;

    private final KafkaEventPublisher kafkaEventPublisher;

    @Override
    @Transactional
    public Course publishCourse(Long courseId) {
        Course course = repository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        course.publish();
        Course saved = repository.save(course);

        log.info("Course published: {}", saved.getId());

        CoursePublishedEvent event = new CoursePublishedEvent(
                saved.getId().toString(),
                saved.getTitle()
        );

        // Spring Events (EDA interno)
        this.eventPublisher.publish(event);

        // RabbitMQ (mensajería externa)
        this.rabbitMQEventPublisher.publish(COURSE_PUBLISHED_ROUTING_KEY, event);

        // Kafka (streaming)
        this.kafkaEventPublisher.publish(event);

        return saved;
    }
}
