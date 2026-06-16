package pe.edu.tecsup.lms.shared.infrastructure.eventsourcing;

import java.util.List;

import pe.edu.tecsup.lms.shared.domain.event.DomainEvent;

public interface EventStore {

    void save(String aggregateId, DomainEvent event);

    List<DomainEvent> getEvents(String aggregateId);

}
