package site.lawmate.admin.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.admin.model.Admin;
import site.lawmate.admin.model.AdminDto;

public interface AdminService {
    Mono<Admin> save(AdminDto adminDto);
    Mono<Admin> findById(String id);
    Flux<Admin> findAll();
    Mono<Admin> update(AdminDto adminDto);
    Mono<Void> delete(String id);

    Mono<String> permit(String id);

    Mono<String> revoke(String id);
}
