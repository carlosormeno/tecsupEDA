package pe.edu.tecsup.lms.comments.domain.event;

import lombok.Builder;
import lombok.Getter;
import pe.edu.tecsup.lms.shared.domain.event.DomainEvent;

@Getter
@Builder
public class CommentEditedEvent extends DomainEvent {
    private final String commentId;
    private final String newText;
    private final int newRating;
    
}
