package com.itsupport.skibackend.models;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Log")
public class Log {

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @NotBlank
    @Size(max = 20)
    private String username;
    @NotBlank
    private String calltype;
    @NotBlank
    private String endpoint;

    @NotBlank
    private String result;

    @NotBlank
    private String time;

    public Log() {

    }

    public Log(@NotBlank @Size(max = 20) String username, @NotBlank String calltype, @NotBlank String endpoint, @NotBlank String result, @NotBlank String time) {
        this.username = username;
        this.calltype = calltype;
        this.endpoint = endpoint;
        this.result = result;
        this.time = time;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCalltype() {
        return calltype;
    }

    public void setCalltype(String calltype) {
        this.calltype = calltype;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
