package pe.edu.tecsup.lms.lessons.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.tecsup.lms.lessons.domain.event.LessonCompletedEvent;
import pe.edu.tecsup.lms.shared.domain.event.EventPublisher;

@Slf4j
@RequiredArgsConstructor
public class CompleteLessonUseCaseImpl implements CompleteLessonUseCase {

    private final EventPublisher eventPublisher;

    @Override
    public void completeLesson(String studentId, String lessonId, String courseId) {
        log.info("Estudiante {} completó la lección {} del curso {}", studentId, lessonId, courseId);
        LessonCompletedEvent event = new LessonCompletedEvent(studentId, lessonId, courseId);
        eventPublisher.publish(event);
    }
}
