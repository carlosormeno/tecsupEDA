for i in 1 2 3 4 5; do
  curl -s -X POST http://localhost:8080/api/courses \
    -H "Content-Type: application/json" \
    -d "{\"title\": \"Curso $i\", \"description\": \"Descripción $i\", \"instructor\": \"Instructor $i\"}"
  echo ""
done

for i in 1 2 3 4 5; do
  curl -s -X PUT http://localhost:8080/api/courses/$i/publish
  echo ""
done

for i in 1 2 3 4 5; do
  curl -s -X POST http://localhost:8080/api/enrollments \
    -H "Content-Type: application/json" \
    -d "{\"studentId\": \"student-00$i\", \"studentEmail\": \"student$i@tecsup.edu.pe\", \"courseId\": \"$i\"}"
  echo ""
done

for i in 1 2 3 4 5; do
  curl -s -X POST http://localhost:8080/api/lessons/complete \
    -H "Content-Type: application/json" \
    -d "{\"studentId\": \"student-00$i\", \"lessonId\": \"lesson-0$i\", \"courseId\": \"$i\"}"
  echo ""
done

curl -s http://localhost:8080/api/admin/dlq


----------------Event-------------------

curl -s -X POST http://localhost:9099/api/es/enrollments \
  -H "Content-Type: application/json" \
  -d '{"studentId":"s-01","studentName":"Juan","courseId":"course-01"}'

curl -s -X POST http://localhost:9099/api/es/enrollments/{enrollmentId}/lessons/lesson-01
curl -s -X POST http://localhost:9099/api/es/enrollments/{enrollmentId}/lessons/lesson-02
curl -s -X POST http://localhost:9099/api/es/enrollments/{enrollmentId}/lessons/lesson-03

curl -s http://localhost:9099/api/es/enrollments/{enrollmentId}/progress



curl -s -X POST http://localhost:9099/api/es/comments \
  -H "Content-Type: application/json" \
  -d '{"courseId":"course-01","studentId":"s-01","text":"Buen curso","rating":4}'

curl -s -X POST http://localhost:9099/api/es/comments \
  -H "Content-Type: application/json" \
  -d '{"courseId":"course-01","studentId":"s-02","text":"Muy bueno","rating":5}'

curl -s -X PUT http://localhost:9099/api/es/comments/{commentId} \
  -H "Content-Type: application/json" \
  -d '{"courseId":"course-01","newText":"Excelente curso","newRating":5}'

curl -s http://localhost:9099/api/es/comments/course-01


------ CQRS -------
curl -s -X POST http://localhost:9099/api/es/enrollments \
  -H "Content-Type: application/json" \
  -d '{"studentId":"s-01","studentName":"Juan","courseId":"course-01"}'

curl -s -X POST http://localhost:9099/api/es/enrollments/{enrollmentId}/lessons/lesson-01
curl -s -X POST http://localhost:9099/api/es/enrollments/{enrollmentId}/lessons/lesson-02
curl -s -X POST http://localhost:9099/api/es/enrollments/{enrollmentId}/lessons/lesson-03

curl -s http://localhost:9099/api/es/enrollments/{enrollmentId}

curl -s http://localhost:9099/api/es/enrollments