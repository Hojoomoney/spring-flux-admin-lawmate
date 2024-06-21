package site.lawmate.admin.admin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.admin.admin.model.Admin;
import site.lawmate.admin.admin.model.AdminDto;
import site.lawmate.admin.admin.repository.AdminRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService{

    private final AdminRepository adminRepository;
    @Override
    public Mono<Admin> save(AdminDto adminDto) {

        return adminRepository.save(new Admin(adminDto.getId(), adminDto.getUsername(), adminDto.getPassword(), adminDto.getRole(), adminDto.getEnabled()));
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
    public Mono<Admin> update(AdminDto adminDto) {
        return adminRepository.findById(adminDto.getId())
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
}
