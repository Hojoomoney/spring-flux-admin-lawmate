package site.lawmate.admin.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
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

    public Mono<Long> getVisitorCountByDate(String date) {
        return redisTemplate.opsForValue().get("visitorCount:" + date)
                .map(Long::parseLong);
    }


    public Mono<Long> getVisitorCountByMonth(String year, String month) {
        return redisTemplate.keys("visitorCount:" + year + "-" + month + "*")
                .flatMap(redisTemplate.opsForValue()::get)
                .map(Long::parseLong)
                .reduce(0L, Long::sum); // reduce()는 Flux 에서 방출된 모든 값을 하나로 합침 (0L은 초기값)
    }



}
