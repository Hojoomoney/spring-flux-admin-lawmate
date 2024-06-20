package site.lawmate.admin.admin.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.admin.admin.model.Admin;
import site.lawmate.admin.admin.model.AdminDto;

public interface AdminService {
    Mono<Admin> save(AdminDto adminDto);
    Mono<Admin> findById(String id);
    Flux<Admin> findAll();
    Mono<Admin> update(AdminDto adminDto);
    Mono<Void> delete(String id);
}
