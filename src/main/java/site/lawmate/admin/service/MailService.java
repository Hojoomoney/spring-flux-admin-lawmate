package site.lawmate.admin.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public Mono<Void> sendMail(String to, String subject, String text) {
        return Mono.fromRunnable(() -> { //Mono.fromRunnable()는 Runnable 객체를 사용하여 비동기 작업을 수행
                try {
                    MimeMessage message = mailSender.createMimeMessage();
                    // MimeMessage는 이메일을 보내기 위한 메시지 객체
                    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8"); // 멀티파티 메시지란 여러 종류의 데이터를 하나의 메시지에 담아 전송하는 방식
                    // MimeMessageHelper는 멀티파트 메시지를 생성하는 데 사용
                    helper.setTo(to);
                    helper.setSubject(subject);
                    helper.setText(text, true); // true로 설정하면 HTML 형식으로 메일을 보낼 수 있음
                    mailSender.send(message);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
        });
    }

}
