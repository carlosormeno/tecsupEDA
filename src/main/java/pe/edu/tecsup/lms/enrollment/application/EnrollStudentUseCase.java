package pe.edu.tecsup.lms.enrollment.application;

public interface EnrollStudentUseCase {

    void enrollStudent(String studentId, String studentEmail, String courseId);
}
