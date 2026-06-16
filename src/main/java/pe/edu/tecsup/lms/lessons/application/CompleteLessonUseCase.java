package pe.edu.tecsup.lms.lessons.application;

public interface CompleteLessonUseCase {

    void completeLesson(String studentId, String lessonId, String courseId);
}
