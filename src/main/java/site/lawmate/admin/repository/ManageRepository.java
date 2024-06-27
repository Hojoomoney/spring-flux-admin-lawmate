package site.lawmate.admin.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import site.lawmate.admin.domain.model.Lawyer;

@Repository
public interface ManageRepository extends ReactiveMongoRepository<Lawyer, String> {
}
