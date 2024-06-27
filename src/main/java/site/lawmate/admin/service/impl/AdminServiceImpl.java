package site.lawmate.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.admin.domain.model.Admin;
import site.lawmate.admin.domain.dto.AdminDto;
import site.lawmate.admin.repository.AdminRepository;
import site.lawmate.admin.service.AdminService;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public Mono<Admin> save(AdminDto adminDto) {
        return adminRepository.save(Admin.builder()
                .username(adminDto.getUsername())
                .email(adminDto.getEmail())
                .name(adminDto.getName())
                .password(adminDto.getPassword())
                .role(adminDto.getRole())
                .enabled(adminDto.getEnabled())
                .build());
    }


    @Override
    public Mono<Admin> findById(String id) {
        return adminRepository.findById(id);
    }

    @Override
    public Flux<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public Mono<Admin> update(String id, AdminDto adminDto) {
        return adminRepository.findById(id)
                .map(admin -> {
                    admin.setUsername(adminDto.getUsername());
                    admin.setPassword(adminDto.getPassword());
                    admin.setRole(adminDto.getRole());
                    return admin;
                })
                .flatMap(adminRepository::save);
    }

    @Override
    public Mono<Void> delete(String id) {
        return adminRepository.deleteById(id);
    }

    @Override
    public Mono<String> permit(String id) {
        return adminRepository.findById(id)
                .map(admin -> {
                    admin.setEnabled(true);
                    return admin;
                })
                .flatMap(adminRepository::save)
                .flatMap(admin -> Mono.just("Permit Success"))
                .switchIfEmpty(Mono.just("Permit Failure"));
    }

    @Override
    public Mono<String> revoke(String id) {
        return adminRepository.findById(id)
                .map(admin -> {
                    admin.setEnabled(false);
                    return admin;
                })
                .flatMap(adminRepository::save)
                .flatMap(admin -> Mono.just("Revoke Success"))
                .switchIfEmpty(Mono.just("Revoke Failure"));
    }

    @Override
    public Flux<Admin> findAllByEnabled() {
        return adminRepository.findAll().filter(admin -> !admin.getEnabled());
    }

    @Override
    public Mono<Long> countAdminsEnabledFalse() {
        return adminRepository.findAll().filter(admin -> !admin.getEnabled()).count();
    }

    @Override
    public Flux<AdminDto> searchByName(String keyword) {
        return adminRepository.findAll()
                .filter(admin -> admin.getName().contains(keyword) || admin.getEmail().contains(keyword))
                .filter(Admin::getEnabled)
                .flatMap(admin -> Flux.just(AdminDto.builder()
                        .email(admin.getEmail())
                        .name(admin.getName())
                        .role(admin.getRole())
                        .build()));
    }

}
