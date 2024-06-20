package site.lawmate.admin.visitor.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.admin.visitor.service.VisitorService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class VisitorController {

    private final VisitorService visitorService;

    @PostMapping("/visit")
    public Mono<Long> visit() {
        return visitorService.incrementVisitorCount();
    }

    @GetMapping(value = "/visitors", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getVisitorCountStream() {
        return visitorService.getVisitorCountStream();
    }
}
