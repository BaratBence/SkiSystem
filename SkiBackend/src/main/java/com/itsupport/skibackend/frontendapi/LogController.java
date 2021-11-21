package com.itsupport.skibackend.frontendapi;

import com.itsupport.skibackend.models.Log;
import com.itsupport.skibackend.models.persistence.LogRepository;
import com.itsupport.skibackend.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;
import java.util.*;

@RestController
@RequestMapping("/api/log")
public class LogController {

    @Autowired
    private LogRepository logRepository;

    @GetMapping("/id/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Log> getLogById(@PathVariable UUID id) {
        Optional<Log> log = logRepository.findById(id);
        if(log.isPresent()) {
            logRepository.save(new Log(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(),"Get", "/api/id/log/{" + id.toString() +"}","Success", java.time.LocalDateTime.now().toString()));
            return ResponseEntity.ok(log.get());
        }
        else {
            logRepository.save(new Log(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(),"Get", "/api/id/log/{" + id.toString() +"}","Failed no such id", java.time.LocalDateTime.now().toString()));
            ResponseEntity.badRequest().build();
        }
        return log.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/user/{user}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<Log>> getLogById(@PathVariable String username) {
        List<Log> log = logRepository.findByUsername(username);
        if(log.isEmpty()) {
            logRepository.save(new Log(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(),"Get", "/api/user/log/{" + username +"}","Failed no such username or user action", java.time.LocalDateTime.now().toString()));
            return ResponseEntity.badRequest().build();
        }
        else {
            logRepository.save(new Log(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(),"Get", "/api/user/log/{" + username +"}","Failed no such username or user action", java.time.LocalDateTime.now().toString()));
            return ResponseEntity.ok(log);
        }

    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<Log>> getAll() {
        List<Log> log = logRepository.findAll();
        logRepository.save(new Log(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(),"Get", "/api/log","Success", java.time.LocalDateTime.now().toString()));
        return ResponseEntity.ok(log);
    }

}
