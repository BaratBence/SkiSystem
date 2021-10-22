package com.itsupport.skibackend;

import com.itsupport.skibackend.models.persistence.ElevatorAppRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackageClasses = ElevatorAppRepository.class)
@SpringBootApplication
public class SkiBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkiBackendApplication.class, args);
    }

}
