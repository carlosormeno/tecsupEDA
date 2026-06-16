package pe.edu.tecsup.lms.enrollment.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.edu.tecsup.lms.enrollment.application.EnrollStudentUseCase;
import pe.edu.tecsup.lms.enrollment.application.EnrollStudentUseCaseImpl;
import pe.edu.tecsup.lms.shared.domain.event.EventPublisher;

@Configuration
public class EnrollmentBeanConfiguration {

    @Bean
    public EnrollStudentUseCase enrollStudentUseCase(EventPublisher eventPublisher) {
        return new EnrollStudentUseCaseImpl(eventPublisher);
    }
}
