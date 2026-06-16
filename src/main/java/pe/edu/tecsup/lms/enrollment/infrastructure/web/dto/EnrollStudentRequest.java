package pe.edu.tecsup.lms.enrollment.infrastructure.web.dto;

import lombok.Data;

@Data
public class EnrollStudentRequest {
    private String studentId;
    private String studentEmail;
    private String courseId;
}
