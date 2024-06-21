package site.lawmate.admin.visitor.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class VisitorRepository {

    private final ReactiveStringRedisTemplate redisTemplate;

    public Mono<Long> incrementVisitorCount(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public Mono<String> getVisitorCount(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}
