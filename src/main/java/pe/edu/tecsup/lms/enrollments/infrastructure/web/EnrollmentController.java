package pe.edu.tecsup.lms.enrollments.infrastructure.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.tecsup.lms.enrollments.application.command.EnrollStudentCommand;
import pe.edu.tecsup.lms.enrollments.application.command.EnrollmentCommandHandler;
import pe.edu.tecsup.lms.enrollments.application.query.EnrollmentQueryRepository;
import pe.edu.tecsup.lms.enrollments.application.query.EnrollmentReadModel;
import pe.edu.tecsup.lms.enrollments.domain.model.Enrollment;
import pe.edu.tecsup.lms.enrollments.infrastructure.dto.EnrollmentRequest;
import pe.edu.tecsup.lms.enrollments.infrastructure.dto.EnrollmentResponse;

@Slf4j
//Por el conflicto con el otro controller, ahora le hago la precisión dentro del restcontroller
@RestController("esEnrollmentController")
@RequestMapping("/api/es/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentCommandHandler enrollmentCommandHandler;
    private final EnrollmentQueryRepository enrollmentQueryRepository;

    /**
     *  Enroll a student in a course
     */
    @PostMapping
    public ResponseEntity<EnrollmentResponse>
    enrollStudent(@RequestBody EnrollmentRequest request) {

        EnrollStudentCommand command
                = EnrollStudentCommand.builder()
                .studentId(request.getStudentId())
                .studentName(request.getStudentName())
                .courseId(request.getCourseId())
                .build();

        String enrollmentId = enrollmentCommandHandler.enrollStudent(command);

        return ResponseEntity.ok(new EnrollmentResponse(enrollmentId));
    }

    /**
     *  Agregar una lesson al curso
     *  Cada lesson agrega un 10% de progreso al curso.
     * @param enrollmentId
     * @param lessonId
     * @return
     */
    @PostMapping("/{enrollmentId}/lessons/{lessonId}")
    public ResponseEntity<Void> addLesson(@PathVariable String enrollmentId,
                                          @PathVariable String lessonId) {

        enrollmentCommandHandler.addLesson(enrollmentId, lessonId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{enrollmentId}/progress")
    public ResponseEntity<Void> getEnrollmentProgress(@PathVariable String enrollmentId) {
        // Lógica para obtener el progreso de la inscripción

        Enrollment enrollment = enrollmentCommandHandler.getEnrollment(enrollmentId);

        log.info("Enrollment {} - Current progress: {}%",
                enrollmentId, enrollment.getProgressPercentage());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{enrollmentId}")
    public ResponseEntity<EnrollmentReadModel> getEnrollment(@PathVariable String enrollmentId) {

        EnrollmentReadModel readModel = this.enrollmentQueryRepository.findByEnrollmentId(enrollmentId)
        .orElseThrow(() -> new RuntimeException("No enrollment with id " + enrollmentId));
        log.info("CQRS Query - Leyendo desde ReadModel para enrollment: {}", enrollmentId);
        return ResponseEntity.ok(readModel);
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentReadModel>> getAllEnrollments() {
        log.info("CQRS Query - Leyendo todos los enrollments desde ReadModel");
        return ResponseEntity.ok(enrollmentQueryRepository.findAll());
    }
    
}
