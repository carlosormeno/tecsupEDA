package pe.edu.tecsup.lms.enrollments.infrastructure.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class EnrollmentRequest {
    private  String studentId;
    private  String studentName;
    private  String courseId;

    // Nuevo campo
    private BigDecimal amount;
    
}
