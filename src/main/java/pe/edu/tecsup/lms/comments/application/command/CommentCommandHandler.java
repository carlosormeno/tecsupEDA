package pe.edu.tecsup.lms.comments.application.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.tecsup.lms.comments.domain.event.CommentAddedEvent;
import pe.edu.tecsup.lms.comments.domain.event.CommentEditedEvent;
import pe.edu.tecsup.lms.comments.domain.model.CourseComment;
import pe.edu.tecsup.lms.shared.infrastructure.eventsourcing.MemoryEventStore;

@Slf4j
@RequiredArgsConstructor
public class CommentCommandHandler {
    private final MemoryEventStore eventStore;

    public String addComment(AddCommentCommand command) {
        String commentId = "comment-" + System.currentTimeMillis();

        CommentAddedEvent event = CommentAddedEvent.builder()
                .commentId(commentId)
                .courseId(command.getCourseId())
                .studentId(command.getStudentId())
                .text(command.getText())
                .rating(command.getRating())
                .build();

        this.eventStore.save(command.getCourseId(), event);

        log.info("Comment {} added to course {}", commentId, command.getCourseId());

        return commentId;
    }

    public void editComment(EditCommentCommand command) {
        CommentEditedEvent event = CommentEditedEvent.builder()
                .commentId(command.getCommentId())
                .newText(command.getNewText())
                .newRating(command.getNewRating())
                .build();

        this.eventStore.save(command.getCourseId(), event);

        log.info("Comment {} edited in course {}", command.getCommentId(), command.getCourseId());
    }

    public CourseComment getCourseComments(String courseId) {
        var events = this.eventStore.getEvents(courseId);
        return CourseComment.fromEvents(events);
    }
}
