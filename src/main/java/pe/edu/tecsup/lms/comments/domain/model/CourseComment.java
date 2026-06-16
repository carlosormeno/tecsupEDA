package pe.edu.tecsup.lms.comments.domain.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import pe.edu.tecsup.lms.comments.domain.event.CommentAddedEvent;
import pe.edu.tecsup.lms.comments.domain.event.CommentEditedEvent;
import pe.edu.tecsup.lms.shared.domain.event.DomainEvent;

@Getter
public class CourseComment {
    private String courseId;
    private Map<String, CommentDetail> comments = new HashMap<>();

    public static CourseComment fromEvents(List<DomainEvent> events) {
        CourseComment courseComment = new CourseComment();
        for (DomainEvent event : events) {
            courseComment.apply(event);
        }
        return courseComment;
    }

    private void apply(DomainEvent event) {
        if (event instanceof CommentAddedEvent e) {
            this.courseId = e.getCourseId();
            this.comments.put(e.getCommentId(),
                new CommentDetail(e.getCommentId(), e.getStudentId(), e.getText(), e.getRating()));
        } else if (event instanceof CommentEditedEvent e) {
            CommentDetail existing = this.comments.get(e.getCommentId());
            if (existing != null) {
                this.comments.put(e.getCommentId(),
                    new CommentDetail(existing.commentId(), existing.studentId(), e.getNewText(), e.getNewRating()));
            }
        }
    }

    public record CommentDetail(String commentId, String studentId, String text, int rating) {}
    
}
