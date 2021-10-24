package com.itsupport.skibackend.models.persistence;

import com.itsupport.skibackend.models.ElevatorApplicationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ElevatorAppRepository extends JpaRepository<ElevatorApplicationModel, UUID> {
}
