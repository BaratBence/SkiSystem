package com.itsupport.skibackend;

import com.itsupport.skibackend.models.User;
import com.itsupport.skibackend.models.UserRole;
import com.itsupport.skibackend.models.persistence.UserRepository;
import com.itsupport.skibackend.models.persistence.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static com.itsupport.skibackend.models.EUserRole.ROLE_ADMINISTRATOR;
import static com.itsupport.skibackend.models.EUserRole.ROLE_USER;

@Component
public class RunAfterStart {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @EventListener(ApplicationReadyEvent.class)
    public void createDefaultUser() {
        roleRepository.save(new UserRole(ROLE_ADMINISTRATOR));
        roleRepository.save(new UserRole(ROLE_USER));
        Set<UserRole> roles = new HashSet<>();
        roles.add(roleRepository.findByName(ROLE_ADMINISTRATOR).get());

        User user = new User("admin", encoder.encode("admin"));
        user.setRoles(roles);
        userRepository.save(user);
    }
}
