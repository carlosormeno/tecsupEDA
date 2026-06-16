package pe.edu.tecsup.lms.shared.infrastructure.dlq;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FailedEventRepository extends JpaRepository<FailedEvent, Long>{
    
}
