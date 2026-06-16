package pe.edu.tecsup.lms.learning.application.eventhandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pe.edu.tecsup.lms.enrollment.domain.event.StudentEnrolledEvent;
import pe.edu.tecsup.lms.shared.infrastructure.dlq.DeadLetterQueue;

import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Component
public class EnrollmentEventHandler {

    private final Random random = new Random();
    private final DeadLetterQueue dlq;

    @EventListener
    @Retryable(
            maxAttempts = 2,
            backoff = @Backoff(delay = 1000, multiplier = 2))
    public void sendWelcomeEmail(StudentEnrolledEvent event) {
        log.info("Enviando email de bienvenida a: {}", event.getStudentEmail());

        if (random.nextBoolean()) {
            log.warn("Fallo al enviar email, reintentando...");
            throw new RuntimeException("Email service unavailable");
        }

        log.info("Email de bienvenida enviado exitosamente a: {}", event.getStudentEmail());
    }

    @Recover
    public void recover(RuntimeException e, StudentEnrolledEvent event) {
        log.error("Se agotaron los reintentos para email: {} - Error: {}", event.getStudentEmail(), e.getMessage());
        dlq.add(event, e);
    }

    @Async("eventExecutor")
    @EventListener
    public void updateCourseStats(StudentEnrolledEvent event) {
        log.info("Actualizando estadísticas del curso: {}", event.getCourseId());
    }

    @Async("eventExecutor")
    @EventListener
    public void createMaterialAccess(StudentEnrolledEvent event) {
        log.info("Creando acceso al material para el estudiante: {}", event.getStudentId());
    }
}
