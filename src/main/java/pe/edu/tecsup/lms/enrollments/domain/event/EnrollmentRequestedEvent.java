package pe.edu.tecsup.lms.enrollments.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.tecsup.lms.shared.domain.event.DomainEvent;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentRequestedEvent extends DomainEvent {

    private String enrollmentId;
    private String studentId;
    private String studentName;
    private String courseId;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    @Override
    public String getKey() {
        return enrollmentId;
    }
    
}
