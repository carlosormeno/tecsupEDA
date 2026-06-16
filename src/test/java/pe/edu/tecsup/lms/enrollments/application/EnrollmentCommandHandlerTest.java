package pe.edu.tecsup.lms.enrollments.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import lombok.extern.slf4j.Slf4j;
import pe.edu.tecsup.lms.enrollments.application.command.EnrollStudentCommand;
import pe.edu.tecsup.lms.enrollments.application.command.EnrollmentCommandHandler;
import pe.edu.tecsup.lms.shared.infrastructure.eventsourcing.MemoryEventStore;

@Slf4j
public class EnrollmentCommandHandlerTest {
    private EnrollmentCommandHandler handler;
    private MemoryEventStore eventStore;
    private ApplicationEventPublisher publisher;

    @BeforeEach
    void setUp() {
        this.publisher = mock(ApplicationEventPublisher.class);
        this.eventStore = new MemoryEventStore(this.publisher);
        this.handler = new EnrollmentCommandHandler(this.eventStore);
    }

    @Test
    void enrollStudent() {
            EnrollStudentCommand command
                    = EnrollStudentCommand.builder()
                    .studentId("student-01")
                    .studentName("Maria")
                    .courseId("course-01")
                    .build();

            String enrollmentId = handler.enrollStudent(command);

            log.info("Enrollment Id: {}", enrollmentId);

            //
            assertNotNull(enrollmentId);

            //
            var events = this.eventStore.getEvents(enrollmentId);
            assertEquals(1, events.size());
    }
}
