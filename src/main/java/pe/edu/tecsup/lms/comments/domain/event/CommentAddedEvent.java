package pe.edu.tecsup.lms.comments.domain.event;

import lombok.Builder;
import lombok.Getter;
import pe.edu.tecsup.lms.shared.domain.event.DomainEvent;

@Getter
@Builder
public class CommentAddedEvent  extends DomainEvent {

    private final String commentId;
    private final String courseId;
    private final String studentId;
    private final String text;
    private final int rating;
    
}
