package site.lawmate.admin.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

        @Bean
        public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory) {
            ReactiveRedisTemplate<String, Object> template = new ReactiveRedisTemplate<>(connectionFactory, RedisSerializationContext.<String, Object>newSerializationContext(new StringRedisSerializer())
                    .value(new GenericJackson2JsonRedisSerializer()).build());
            return template;
        }
}
