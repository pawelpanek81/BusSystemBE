package pl.bussystem.config.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ClockBean {

  @Bean
  Clock clock() {
    return Clock.system(Clock.systemUTC().getZone());
  }
}
