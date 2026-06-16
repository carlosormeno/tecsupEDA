package pe.edu.tecsup.lms.lessons.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.tecsup.lms.lessons.application.CompleteLessonUseCase;
import pe.edu.tecsup.lms.lessons.infrastructure.web.dto.CompleteLessonRequest;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final CompleteLessonUseCase completeLessonUseCase;

    @PostMapping("/complete")
    public ResponseEntity<Void> completeLesson(@RequestBody CompleteLessonRequest request) {
        completeLessonUseCase.completeLesson(
                request.getStudentId(),
                request.getLessonId(),
                request.getCourseId()
        );
        return ResponseEntity.ok().build();
    }
}
