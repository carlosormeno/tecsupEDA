package pe.edu.tecsup.lms.lessons.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import pe.edu.tecsup.lms.shared.domain.event.DomainEvent;

@AllArgsConstructor
@Getter
@ToString
public class LessonCompletedEvent extends DomainEvent {

    private final String studentId;
    private final String lessonId;
    private final String courseId;
}
