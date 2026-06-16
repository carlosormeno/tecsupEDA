package pe.edu.tecsup.lms.comments.infrastructure.dto;

import lombok.Data;

@Data
public class EditCommentRequest {
    private String courseId;
    private String newText;
    private int newRating;
}
