package pe.edu.tecsup.lms.comments.application.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddCommentCommand {
    private final String courseId;
    private final String studentId;
    private final String text;
    private final int rating;
    
}
