package com.itsupport.skibackend.models.persistence;

import com.itsupport.skibackend.models.ElevatorApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ElevatorAppRepository extends JpaRepository<ElevatorApplication, UUID> {
}
