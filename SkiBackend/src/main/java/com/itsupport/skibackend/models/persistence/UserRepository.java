package com.itsupport.skibackend.models.persistence;

import com.itsupport.skibackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.id = id")
    Optional<User> findByUserUUID(@Param("id")UUID id);

    Optional<User> findById(UUID uuid);
}
