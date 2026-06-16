package pe.edu.tecsup.lms.comments.infrastructure.dto;

import lombok.Data;

@Data
public class AddCommentRequest {
    private String courseId;
    private String studentId;
    private String text;
    private int rating;
}
