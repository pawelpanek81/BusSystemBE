package pl.bussystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableCaching
@EnableScheduling
public class BussystemApplication {

  public static void main(String[] args) {
    SpringApplication.run(BussystemApplication.class, args);
  }
}
