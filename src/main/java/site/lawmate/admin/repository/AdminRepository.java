package site.lawmate.admin.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import site.lawmate.admin.domain.model.Admin;

@Repository
public interface AdminRepository extends ReactiveMongoRepository<Admin, String> {
}