package pe.edu.tecsup.lms.comments.infrastructure.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.tecsup.lms.comments.application.command.AddCommentCommand;
import pe.edu.tecsup.lms.comments.application.command.CommentCommandHandler;
import pe.edu.tecsup.lms.comments.application.command.EditCommentCommand;
import pe.edu.tecsup.lms.comments.domain.model.CourseComment;
import pe.edu.tecsup.lms.comments.infrastructure.dto.AddCommentRequest;
import pe.edu.tecsup.lms.comments.infrastructure.dto.CommentResponse;
import pe.edu.tecsup.lms.comments.infrastructure.dto.EditCommentRequest;

@Slf4j
@RestController("commentController")
@RequestMapping("/api/es/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentCommandHandler commentCommandHandler;

    @PostMapping
    public ResponseEntity<CommentResponse> addComment(@RequestBody AddCommentRequest request) {
        AddCommentCommand command = AddCommentCommand.builder()
                .courseId(request.getCourseId())
                .studentId(request.getStudentId())
                .text(request.getText())
                .rating(request.getRating())
                .build();

        String commentId = commentCommandHandler.addComment(command);
        //return ResponseEntity.ok(commentId);
        return ResponseEntity.ok(new CommentResponse(
            "Comentario registrado con exito",
            commentId,
            request.getCourseId(),
            request.getStudentId(),
            request.getText(),
            request.getRating()
    ));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> editComment(@PathVariable String commentId,
                                            @RequestBody EditCommentRequest request) {
        EditCommentCommand command = EditCommentCommand.builder()
                .commentId(commentId)
                .courseId(request.getCourseId())
                .newText(request.getNewText())
                .newRating(request.getNewRating())
                .build();

        commentCommandHandler.editComment(command);
        //return ResponseEntity.ok().build();
        return ResponseEntity.ok(new CommentResponse(
            "Comentario actualizado con exito",
            commentId,
            request.getCourseId(),
            null,
            request.getNewText(),
            request.getNewRating()
    ));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseComment> getCourseComments(@PathVariable String courseId) {
        CourseComment courseComment = commentCommandHandler.getCourseComments(courseId);
        return ResponseEntity.ok(courseComment);
    }
}
