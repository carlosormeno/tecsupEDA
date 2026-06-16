package pe.edu.tecsup.lms.enrollments.application.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.tecsup.lms.enrollments.domain.event.LessonCompletedEvent;
import pe.edu.tecsup.lms.enrollments.domain.event.StudentEnrolledEvent;
import pe.edu.tecsup.lms.enrollments.domain.model.Enrollment;
import pe.edu.tecsup.lms.shared.infrastructure.eventsourcing.MemoryEventStore;

@Slf4j
@RequiredArgsConstructor
public class EnrollmentCommandHandler {

    private final MemoryEventStore eventStore;

    public String enrollStudent(EnrollStudentCommand command) {
        String enrollmentId = "enrollment-" + System.currentTimeMillis();

        StudentEnrolledEvent event = StudentEnrolledEvent.builder()
                .enrollmentId(enrollmentId)
                .studentId(command.getStudentId())
                .studentName(command.getStudentName())
                .courseId(command.getCourseId())
                .build();

        this.eventStore.save(enrollmentId, event);

        return enrollmentId;
    }

    public void addLesson(String enrollmentId, String lessonId) {
        var events = this.eventStore.getEvents(enrollmentId);
        var enrollment = Enrollment.fromEvents(events);

        int newProgress = enrollment.getProgressPercentage() + 10;

        log.info("Adding lesson {} to enrollment {} with progress {}",
                lessonId, enrollmentId, newProgress);

        var eventLesson = LessonCompletedEvent.builder()
                .enrollmentId(enrollmentId)
                .lessonId(lessonId)
                .newProgressPercentage(newProgress)
                .build();

        this.eventStore.save(enrollmentId, eventLesson);
    }

    public Enrollment getEnrollment(String enrollmentId) {
        var events = this.eventStore.getEvents(enrollmentId);
        return Enrollment.fromEvents(events);
    }
}
