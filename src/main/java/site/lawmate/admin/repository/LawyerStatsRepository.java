package site.lawmate.admin.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import site.lawmate.admin.domain.model.LawyerStats;

@Repository
public interface LawyerStatsRepository extends ReactiveMongoRepository<LawyerStats, String> {
}
