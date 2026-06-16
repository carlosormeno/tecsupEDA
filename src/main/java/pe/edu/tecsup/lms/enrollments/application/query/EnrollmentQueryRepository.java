package pe.edu.tecsup.lms.enrollments.application.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class EnrollmentQueryRepository {
    private final Map<String, EnrollmentReadModel> readModels = new HashMap<>();

    // Update

    /**
     *
     * @param readModel
     */
    public void save(EnrollmentReadModel readModel) {
        this.readModels.put(readModel.getEnrollmentId(), readModel);
    }

    // Read

    /**
     *
     * @param enrollmentId
     * @return
     */
    public Optional<EnrollmentReadModel> findByEnrollmentId(String enrollmentId) {

        return Optional.ofNullable(this.readModels.get(enrollmentId));

    }


    /**
     *
     * @return
     */
    public List<EnrollmentReadModel> findAll() {

        return  new ArrayList<>(this.readModels.values());
    }
}
