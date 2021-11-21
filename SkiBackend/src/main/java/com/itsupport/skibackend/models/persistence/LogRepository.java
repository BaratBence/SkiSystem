package com.itsupport.skibackend.models.persistence;

import com.itsupport.skibackend.models.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.*;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findByUsername(String username);

    Optional<Log> findById(UUID uuid);
}
