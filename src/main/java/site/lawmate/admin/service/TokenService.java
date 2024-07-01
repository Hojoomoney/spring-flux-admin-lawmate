package site.lawmate.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

    private final String TOKEN_PREFIX = "token:";

    public Mono<Boolean> saveRefreshToken(String adminId, String refreshToken, Long expirationTime) {
        return reactiveRedisTemplate.opsForValue().set(TOKEN_PREFIX + adminId, refreshToken, Duration.ofMillis(expirationTime));
    }

    public Mono<String> getRefreshToken(String adminId) {
        return reactiveRedisTemplate.opsForValue().get(TOKEN_PREFIX + adminId).cast(String.class);
    }

    public Mono<Boolean> deleteRefreshToken(String adminId) {
        return reactiveRedisTemplate.opsForValue().delete(TOKEN_PREFIX + adminId);
    }
}
