package pe.edu.tecsup.lms.enrollment.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.tecsup.lms.enrollment.domain.event.StudentEnrolledEvent;
import pe.edu.tecsup.lms.shared.domain.event.EventPublisher;

@Slf4j
@RequiredArgsConstructor
public class EnrollStudentUseCaseImpl implements EnrollStudentUseCase {

    private final EventPublisher eventPublisher;

    @Override
    public void enrollStudent(String studentId, String studentEmail, String courseId) {
        log.info("Matriculando estudiante {} en curso {}", studentId, courseId);
        StudentEnrolledEvent event = new StudentEnrolledEvent(studentId, studentEmail, courseId);
        eventPublisher.publish(event);
    }
}
