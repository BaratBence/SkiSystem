package com.itsupport.skibackend;

import com.itsupport.skibackend.models.User;
import com.itsupport.skibackend.models.UserRole;
import com.itsupport.skibackend.models.persistence.ElevatorAppRepository;
import com.itsupport.skibackend.models.persistence.UserRepository;
import com.itsupport.skibackend.models.persistence.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static com.itsupport.skibackend.models.EUserRole.ROLE_ADMINISTRATOR;
import static com.itsupport.skibackend.models.EUserRole.ROLE_USER;

@EnableJpaRepositories(basePackageClasses = ElevatorAppRepository.class)
@SpringBootApplication
public class SkiBackendApplication{

    public static void main(String[] args) {
        SpringApplication.run(SkiBackendApplication.class, args);
    }
}
