package org.example.nominatercercorte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NominaTercerCorteApplication {

  public static void main(String[] args) {
    SpringApplication.run(NominaTercerCorteApplication.class, args);
  }

}
