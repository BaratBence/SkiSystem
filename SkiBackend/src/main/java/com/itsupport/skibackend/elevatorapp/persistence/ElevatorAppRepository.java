package com.itsupport.skibackend.elevatorapp.persistence;

import com.itsupport.skibackend.elevatorapp.ElevatorApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ElevatorAppRepository extends JpaRepository<ElevatorApplication, UUID> {
}
