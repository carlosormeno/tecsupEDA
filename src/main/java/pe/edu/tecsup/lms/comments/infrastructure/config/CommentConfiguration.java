package pe.edu.tecsup.lms.comments.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pe.edu.tecsup.lms.comments.application.command.CommentCommandHandler;
import pe.edu.tecsup.lms.shared.infrastructure.eventsourcing.MemoryEventStore;

@Configuration
public class CommentConfiguration {
    @Bean
    public CommentCommandHandler commentCommandHandler(MemoryEventStore eventStore) {
        return new CommentCommandHandler(eventStore);
    }
    
}
