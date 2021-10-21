package com.itsupport.skibackend.models;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "roles")
public class UserRole {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EUserRole name;

    public UserRole() {

    }

    public UserRole(EUserRole name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public EUserRole getName() {
        return name;
    }

    public void setName(EUserRole name) {
        this.name = name;
    }
}
