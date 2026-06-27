package pe.edu.tecsup.lms.notifications.application.eventhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import pe.edu.tecsup.lms.courses.domain.event.CourseCreatedEvent;
import pe.edu.tecsup.lms.shared.infrastructure.config.RabbitMQConfig;
import pe.edu.tecsup.lms.shared.infrastructure.dlq.DeadLetterQueue;

import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Component
public class CourseEventHandler {

    private final Random random = new Random();
    private final DeadLetterQueue dlq;

    // Consumidor RabbitMQ
    @RabbitListener(queues = RabbitMQConfig.COURSE_QUEUE)
    public void handleCourseCreatedRabbit(CourseCreatedEvent event) {
        log.info("[RabbitMQ] Course created event received: {}", event);
    }

    // Consumidor Spring Events (EDA interno - mantiene retry + DLQ)
    @EventListener
    @Retryable(
            maxAttempts = 2,
            backoff = @Backoff(delay = 1000, multiplier = 2))
    public void handleCourseCreated(CourseCreatedEvent event) {
        log.info("Procesando notificación para curso creado: {}", event.getTitle());

        if (random.nextBoolean()) {
            log.warn("Fallo al enviar notificación, reintentando...");
            throw new RuntimeException("Notification service unavailable");
        }

        log.info("Notificación enviada exitosamente para curso: {}", event.getTitle());
    }

    @Recover
    public void recover(RuntimeException e, CourseCreatedEvent event) {
        log.error("Se agotaron los reintentos para curso: {} - Error: {}", event.getTitle(), e.getMessage());
        dlq.add(event, e);
    }
}
