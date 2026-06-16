package pe.edu.tecsup.lms.shared.infrastructure.dlq;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pe.edu.tecsup.lms.shared.domain.event.DomainEvent;

//Le cambio el getter a Data, xq ahora voy a setear
@Data
@AllArgsConstructor
//agregamos lo correspondiente para convertirlo en entidad
@NoArgsConstructor
@Entity
@Table(name = "failed_events")
public class FailedEvent {

    //creamos un ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private final DomainEvent event;
    // lo descomponemos a eventId, eventType, payload, porque JPA no puedo meter todo en 1
    private String eventId;
    private String eventType;

    @Column(length = 2000)
    private String payload;

    //elimino el final en estos dos campos xq ahora usamos @NoArgsConstructor
    private String message;
    private long timestamp;


}
