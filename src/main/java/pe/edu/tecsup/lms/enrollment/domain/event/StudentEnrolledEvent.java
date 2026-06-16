package pe.edu.tecsup.lms.enrollment.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import pe.edu.tecsup.lms.shared.domain.event.DomainEvent;

@AllArgsConstructor
@Getter
@ToString
public class StudentEnrolledEvent extends DomainEvent {

    private final String studentId;
    private final String studentEmail;
    private final String courseId;
}
