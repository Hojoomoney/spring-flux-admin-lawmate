package site.lawmate.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import site.lawmate.admin.repository.VisitorRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorRepository visitorRepository;
    private final Sinks.Many<String> visitorCountSink = Sinks.many().multicast().onBackpressureBuffer();
    // Sinks.many().multicast().onBackpressureBuffer()를 사용하면 다수의 Subscriber 가 동시에 구독할 수 있음
    // Sinks란 Publisher 와 Subscriber 사이의 통신을 위한 중간 매개체
    // Sinks.many() 는 여러값을 방출할 수 있는 Sinks 생성
    // multicast()는 여러 Subscriber 가 동시에 구독할 수 있도록 함
    // onBackpressureBuffer()는 Subscriber 가 처리할 수 없는 데이터가 발생했을 때 버퍼에 저장
    // 버퍼가 가득 차면 Subscriber 가 처리할 수 있을 때까지 대기


    public Mono<Long> incrementVisitorCount() {
        String key = getCurrentDateKey();
        return visitorRepository.incrementVisitorCount(key)
                .doOnNext(count -> visitorCountSink.tryEmitNext(count.toString()));
        // doOnNext()는 Publisher 가 값을 방출할 때마다 특정 동작을 수행
        // tryEmitNext()는 Sinks.Many 에 값을 방출
    }

    public Mono<String> getVisitorCount() {
        String key = getCurrentDateKey();
        return visitorRepository.getVisitorCount(key);
    }

    public Flux<String> getVisitorCountStream() {
        return visitorCountSink.asFlux();
    }

    public Mono<Map<String,Long>> getVisitorCountByLast7Days() {
        // 방문자 수를 저장할 Map 객체 생성
        Map<String,Long> dayMap = new HashMap<>();
        return Flux.range(0, 7)
                .map(i -> LocalDate.now().minusDays(i))
                .flatMapSequential(date -> {
                    String dateKey = "visitorCount:" + date;
                    return visitorRepository.getVisitorCount(dateKey)
                            .map(count -> {
                                dayMap.put(date.toString(), Long.parseLong(count));
                                return dayMap;
                            });
                })
                .then(Mono.just(dayMap));
    }


    public Mono<Long> getVisitorCountByMonth(String year, String month) {
        return visitorRepository.getVisitorCountByMonth(year, month);
    }

    public Mono<Map<String,Long>> getVisitorCountYearByMonth(String year) {
        Map<String,Long> monthMap = new HashMap<>();
        return Flux.range(1, 12)
                .flatMapSequential(month -> { //flatMapSequential()은 Flux 가 방출하는 값에 대해 순차적으로 Mono 를 생성
                    String monthKey = String.format("%02d", month);
                    return visitorRepository.getVisitorCountByMonth(year, monthKey)
                            .map(count -> {
                                monthMap.put(monthKey, count);
                                return monthMap;
                            });
                })
                .then(Mono.just(monthMap));
    }


    private String getCurrentDateKey() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return "visitorCount:" + today.format(formatter);
    }
}
