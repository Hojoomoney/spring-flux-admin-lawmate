package site.lawmate.admin.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.admin.domain.dto.LawyerStatsDto;
import site.lawmate.admin.domain.model.Lawyer;

public interface ManageService {
    Mono<LawyerStatsDto> countLawyersAll();

    Flux<Lawyer> getLawyersAuthFalse();

    Mono<Long> countLawyersAuthFalse();

    Mono<Long> countLawyersAuthTrue();
    Mono<Long> countLawyers();
    Mono<Long> countNewLawyers();
    Mono<Long> getIncreaseRate();

    void saveLawyerStats();

    Flux<LawyerStatsDto> getLawyerStats();
}
