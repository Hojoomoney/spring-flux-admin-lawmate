package site.lawmate.admin.mail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import site.lawmate.admin.mail.service.MailService;

import java.util.List;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/send")
    public Mono<ResponseEntity<String>> sendMail(@RequestParam("to") String to,
                                                 @RequestParam("subject") String subject,
                                                 @RequestParam("text") String text) {
        return mailService.sendMail(to, subject, text)
                .thenReturn(ResponseEntity.ok("Mail sent successfully"))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to send mail: " + e.getMessage())));
    }

    @PostMapping("/send-bulk")
    public Mono<ResponseEntity<String>> sendBulkMail(@RequestBody List<String> recipients,
                                                     @RequestParam("subject") String subject,
                                                     @RequestParam("text") String text) {

        return Flux.fromIterable(recipients) //Flux.fromIterable()는 Iterable 객체를 사용하여 Flux 생성
                .flatMap(recipient -> mailService.sendMail(recipient, subject, text))
                .then(Mono.just(ResponseEntity.ok("Mail sent successfully")))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to send mail: " + e.getMessage())));
    }

}
