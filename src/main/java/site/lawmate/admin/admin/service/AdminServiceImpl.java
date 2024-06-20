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

        return adminRepository.save(new Admin(adminDto.getId(), adminDto.getUsername(), adminDto.getPassword(), adminDto.getRole()));
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
                .map(admin -> adminDtoToEntity(adminDto))
                .flatMap(adminRepository::save);
    }

    @Override
    public Mono<Void> delete(String id) {
        return adminRepository.deleteById(id);
    }
    public static Admin adminDtoToEntity(AdminDto adminDto) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminDto, admin);
        log.info("admin: {}", admin.getUsername()+ " " + admin.getPassword());
        return admin;
    }
}
