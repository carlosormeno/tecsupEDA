package pe.edu.tecsup.lms.payment.application.saga;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.tecsup.lms.enrollments.domain.event.EnrollmentRequestedEvent;
import pe.edu.tecsup.lms.payment.domain.event.PaymentFailedEvent;
import pe.edu.tecsup.lms.payment.domain.event.PaymentProcessedEvent;
import pe.edu.tecsup.lms.shared.domain.event.KafkaEventPublisher;
import pe.edu.tecsup.lms.shared.infrastructure.config.KafkaConfig;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentSagaHandler {

    private final KafkaEventPublisher kafkaEventPublisher;
    private final Random random = new Random();

    @KafkaListener(
            topics = KafkaConfig.ENROLLMENT_REQUEST_TOPIC,
            groupId = "payment-saga-group"
    )
    @Transactional
    public void handleEnrollmentRequested(EnrollmentRequestedEvent event) {

        log.info("💳 [PAYMENT] Procesando pago para enrollment");
        log.info("   Enrollment ID: {}", event.getEnrollmentId());
        log.info("   Student: {}", event.getStudentName());
        log.info("   Amount: ${}", event.getAmount());

        try {
            Thread.sleep(1000 + random.nextInt(2000));

            //No falla
            //boolean paymentSuccess = random.nextInt(100) < 60;

            //Obligamos el fallo
            boolean paymentSuccess = false;

            if (paymentSuccess) {

                // PAGO EXITOSO

                log.info("✅ [PAYMENT] Pago procesado exitosamente para enrollment ID: {}", event.getEnrollmentId());
                // Aquí se podría publicar un evento de pago exitoso si fuera necesario

                // PaymentProcessedEvent
                String transactionId = "tx-" + UUID.randomUUID();

                PaymentProcessedEvent processedEvent = new PaymentProcessedEvent(
                        event.getEnrollmentId(),
                        transactionId,
                        event.getAmount(),
                        LocalDateTime.now()
                );

                kafkaEventPublisher.publish(processedEvent);

                log.info("✅ [PAYMENT] Pago procesado exitosamente");
                log.info("   Transaction ID: {}", transactionId);

            } else {
                log.warn("❌ [PAYMENT] El pago falló para enrollment ID: {}", event.getEnrollmentId());
                // Aquí se podría publicar un evento de pago fallido si fuera necesario
                // PaymentFailedEvent
                PaymentFailedEvent failedEvent = new PaymentFailedEvent(
                        event.getEnrollmentId(),
                        "PAYMENT_DECLINED",
                        "El pago fue rechazado por el proveedor, saldo insuficiente.",
                        LocalDateTime.now()
                );

                kafkaEventPublisher.publish(failedEvent);

                log.warn("📨 [PAYMENT] Evento PaymentFailed publicado");
            }

        } catch (Exception e) {
            log.error("💥 [PAYMENT] Error procesando pago", e);
        }
    }
}
