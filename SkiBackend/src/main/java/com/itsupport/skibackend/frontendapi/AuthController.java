package com.itsupport.skibackend.frontendapi;

import com.itsupport.skibackend.models.Log;
import com.itsupport.skibackend.models.payload.JwtResponse;
import com.itsupport.skibackend.models.payload.LoginRequest;
import com.itsupport.skibackend.models.payload.SignUpRequest;
import com.itsupport.skibackend.models.EUserRole;
import com.itsupport.skibackend.models.User;
import com.itsupport.skibackend.models.UserRole;
import com.itsupport.skibackend.models.persistence.LogRepository;
import com.itsupport.skibackend.models.persistence.UserRepository;
import com.itsupport.skibackend.models.persistence.UserRoleRepository;
import com.itsupport.skibackend.security.jwt.JwtUtils;
import com.itsupport.skibackend.security.services.UserDetailsImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository roleRepository;

    @Autowired
    LogRepository logRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody @NotNull LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        logRepository.save(new Log(userDetails.getUsername(),"Post", "/api/users/login","Success", java.time.LocalDateTime.now().toString()));

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }
    
    @PutMapping
    @RequestMapping(value = "/{id}/update")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<User> updateUserAsAdmin(@Valid @RequestBody @NotNull User userNew,@Valid @PathVariable UUID id) {
        Optional<User> user = userRepository.findById(id);
        Optional<User> existingUser = userRepository.findByUsername(userNew.getUsername());
        if(user.isPresent()) {
            if((existingUser.isPresent() && existingUser.get().getId().equals(id)) || (existingUser.isEmpty()))
            {
                user.get().setUsername(userNew.getUsername());
                user.get().setPassword(encoder.encode(userNew.getPassword()));
                logRepository.save(new Log(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(),"Put", "/api/users/{"+ id +"}/update","Success", java.time.LocalDateTime.now().toString()));
                return ResponseEntity.ok(userRepository.save(user.get()));
            }
            else  {
                logRepository.save(new Log(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(),"Put", "/api/users/{"+ id +"}/update","Failed(no such user or username not unique)", java.time.LocalDateTime.now().toString()));
                return ResponseEntity.badRequest().build();
            }
        }
        else {
            logRepository.save(new Log(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(),"Put", "/api/users/{"+ id +"}/update","Failed(no such user with the given id)", java.time.LocalDateTime.now().toString()));
            return  ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    @RequestMapping(value = "/update")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR') || hasRole('ROLE_USER')")
    public ResponseEntity<JwtResponse> updateUser(@Valid @RequestBody @NotNull User userNew) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> existingUser = userRepository.findByUsername(userNew.getUsername());
        if(existingUser.isPresent() &&  existingUser.get().getId().equals(userDetails.getId())) {
            Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
            user.get().setPassword(encoder.encode(userNew.getPassword()));
            userRepository.save(user.get());
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            logRepository.save(new Log(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(),"Put", "/api/users/update","Success", java.time.LocalDateTime.now().toString()));
            return ResponseEntity.ok(new JwtResponse("", user.get().getId(), user.get().getUsername(), roles));
        }
        else if(existingUser.isEmpty()) {
            Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
            user.get().setUsername(userNew.getUsername());
            user.get().setPassword(encoder.encode(userNew.getPassword()));
            userRepository.save(user.get());
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.get().getUsername(), userNew.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            logRepository.save(new Log(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(),"Put", "/api/users/update","Success", java.time.LocalDateTime.now().toString()));
            return ResponseEntity.ok(new JwtResponse(jwt, user.get().getId(), user.get().getUsername(), roles));
        }
        else {
            logRepository.save(new Log(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(),"Post", "/api/users/update","Failed(bad parameters were given)", java.time.LocalDateTime.now().toString()));
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    @RequestMapping(value = "/addUser")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<User> createUserByAdmin(@Valid @NotNull @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) return ResponseEntity.badRequest().build();

        User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<UserRole> roles = new HashSet<>();

        if (strRoles == null) {
            UserRole userRole = roleRepository.findByName(EUserRole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        UserRole modRole = roleRepository.findByName(EUserRole.ROLE_ADMINISTRATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        UserRole userRole = roleRepository.findByName(EUserRole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        logRepository.save(new Log(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(),"Post", "/api/users/addUser","Success", java.time.LocalDateTime.now().toString()));
        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

}
