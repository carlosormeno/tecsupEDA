package pe.edu.tecsup.lms.lessons.infrastructure.web.dto;

import lombok.Data;

@Data
public class CompleteLessonRequest {
    private String studentId;
    private String lessonId;
    private String courseId;
}
