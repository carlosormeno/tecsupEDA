package pe.edu.tecsup.lms.shared.domain.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static pe.edu.tecsup.lms.shared.infrastructure.config.RabbitMQConfig.EXCHANGE_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publish(String routingKey, DomainEvent event) {
        try {
            log.info("Publishing event in RabbitMQ: {}", event);
            log.info("routingKey: {}", routingKey);

            this.rabbitTemplate.convertAndSend(
                    EXCHANGE_NAME,
                    routingKey,
                    event,
                    message -> {
                        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        return message;
                    }
            );
        } catch (Exception e) {
            log.warn("RabbitMQ not available, skipping publish: {}", e.getMessage());
        }
    }
}
