package pe.edu.tecsup.lms.lessons.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.edu.tecsup.lms.lessons.application.CompleteLessonUseCase;
import pe.edu.tecsup.lms.lessons.application.CompleteLessonUseCaseImpl;
import pe.edu.tecsup.lms.shared.domain.event.EventPublisher;

@Configuration
public class LessonsBeanConfiguration {

    @Bean
    public CompleteLessonUseCase completeLessonUseCase(EventPublisher eventPublisher) {
        return new CompleteLessonUseCaseImpl(eventPublisher);
    }
}
