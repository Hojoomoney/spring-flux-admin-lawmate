package site.lawmate.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.admin.domain.dto.LawyerStatsDto;
import site.lawmate.admin.domain.model.Lawyer;
import site.lawmate.admin.service.ManageService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/lawyer/stats")
public class ManageController {

    private final ManageService manageService;

    @GetMapping("/total")
    public Mono<LawyerStatsDto> countLawyers() {
        return manageService.countLawyersAll();
    }

    @GetMapping("/authFalse")
    public Flux<Lawyer> getLawyersAuthFalse() {
        return manageService.getLawyersAuthFalse();
    }

    @Scheduled(cron = "0 0 0 * * *")
    @PostMapping("/save")
    public void saveLawyerStats(){
        manageService.saveLawyerStats();
    }

    @GetMapping("/all")
    public Flux<LawyerStatsDto> getLawyerStats(){
        return manageService.getLawyerStats();
    }
}
