package com.itsupport.skibackend.models.persistence;

import com.itsupport.skibackend.models.EUserRole;
import com.itsupport.skibackend.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByName(EUserRole name);
}
