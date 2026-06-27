package pe.edu.tecsup.lms.enrollments.application.saga;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.tecsup.lms.enrollments.domain.event.EnrollmentRequestedEvent;
import pe.edu.tecsup.lms.shared.domain.event.KafkaEventPublisher;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnrollmentSagaHandler {
    private final KafkaEventPublisher kafkaEventPublisher;

    @Transactional
    public String requestEnrollment(String studentId, String studentName, String courseId, BigDecimal amount) {

        log.info("🚀 [SAGA] Iniciando saga de matrícula");

        // 1. Generar ID
        String enrollmentId = "enrollment-" + UUID.randomUUID();

        // 2. Publicar evento para iniciar saga
        EnrollmentRequestedEvent requestEvent = new EnrollmentRequestedEvent(
                enrollmentId,
                studentId,
                studentName,
                courseId,
                amount,
                LocalDateTime.now()
        );
        // Publicar el comando de inscripción
        kafkaEventPublisher.publish(requestEvent);

        return enrollmentId;
    }
}
