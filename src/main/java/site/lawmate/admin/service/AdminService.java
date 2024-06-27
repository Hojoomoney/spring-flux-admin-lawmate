package site.lawmate.admin.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.admin.domain.model.Admin;
import site.lawmate.admin.domain.dto.AdminDto;

public interface AdminService {
    Mono<Admin> save(AdminDto adminDto);
    Mono<Admin> findById(String id);
    Flux<Admin> findAll();
    Mono<Admin> update(String id,AdminDto adminDto);
    Mono<Void> delete(String id);

    Mono<String> permit(String id);
    Mono<String> revoke(String id);
    Flux<Admin> findAllByEnabled();
    Mono<Long> countAdminsEnabledFalse();

    Flux<AdminDto> searchByName(String keyword);
}
