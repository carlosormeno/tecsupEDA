package pe.edu.tecsup.lms.comments.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponse {
    private String status;
    private String commentId;
    private String courseId;
    private String studentId;
    private String text;
    private int rating;
}
