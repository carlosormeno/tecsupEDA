package pe.edu.tecsup.lms.comments.application.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EditCommentCommand {
    private final String commentId;
    private final String courseId;
    private final String newText;
    private final int newRating;
    
}
