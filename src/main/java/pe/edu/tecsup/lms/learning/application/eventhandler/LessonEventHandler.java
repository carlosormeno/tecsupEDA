package pe.edu.tecsup.lms.learning.application.eventhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pe.edu.tecsup.lms.lessons.domain.event.LessonCompletedEvent;

@Slf4j
@Component
public class LessonEventHandler {

    @Async("eventExecutor")
    @EventListener
    public void updateProgress(LessonCompletedEvent event) {
        log.info("Actualizando progreso del estudiante {} en lección: {}", event.getStudentId(), event.getLessonId());
    }

    @Async("eventExecutor")
    @EventListener
    public void sendAchievementNotification(LessonCompletedEvent event) {
        log.info("Enviando notificación de logro al estudiante: {}", event.getStudentId());
    }

    @Async("eventExecutor")
    @EventListener
    public void checkCourseCompletion(LessonCompletedEvent event) {
        log.info("Verificando si el estudiante {} completó el curso: {}", event.getStudentId(), event.getCourseId());
    }
}
