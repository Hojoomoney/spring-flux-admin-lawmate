package site.lawmate.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.admin.domain.dto.MailDto;
import site.lawmate.admin.service.MailService;

import java.util.List;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/send")
    public Mono<ResponseEntity<String>> sendMail(@RequestBody MailDto mailDto) {
        return mailService.sendMail(mailDto.getTo(), mailDto.getSubject(), mailDto.getText())
                .thenReturn(ResponseEntity.ok("Mail sent successfully"))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to send mail: " + e.getMessage())));
    }

    @PostMapping("/send-bulk")
    public Mono<ResponseEntity<String>> sendBulkMail(@RequestBody MailDto mailDto) {

        return Flux.fromIterable(mailDto.getRecipients()) //Flux.fromIterable()는 Iterable 객체를 사용하여 Flux 생성
                .flatMap(recipient -> mailService.sendMail(recipient, mailDto.getSubject(), mailDto.getText()))
                .then(Mono.just(ResponseEntity.ok("Mail sent successfully")))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to send mail: " + e.getMessage())));
    }

}
