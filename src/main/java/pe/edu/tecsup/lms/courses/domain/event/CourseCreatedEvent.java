package pe.edu.tecsup.lms.courses.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pe.edu.tecsup.lms.shared.domain.event.DomainEvent;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class CourseCreatedEvent extends DomainEvent {

    private final String courseId;
    private final String title;
    private final String instructor;

    @Override
    public String getKey() {
        return this.courseId;
    }

}
