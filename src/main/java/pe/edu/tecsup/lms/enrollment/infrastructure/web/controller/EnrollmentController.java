package pe.edu.tecsup.lms.enrollment.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.tecsup.lms.enrollment.application.EnrollStudentUseCase;
import pe.edu.tecsup.lms.enrollment.infrastructure.web.dto.EnrollStudentRequest;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollStudentUseCase enrollStudentUseCase;

    @PostMapping
    public ResponseEntity<Void> enrollStudent(@RequestBody EnrollStudentRequest request) {
        enrollStudentUseCase.enrollStudent(
                request.getStudentId(),
                request.getStudentEmail(),
                request.getCourseId()
        );
        return ResponseEntity.ok().build();
    }
}
