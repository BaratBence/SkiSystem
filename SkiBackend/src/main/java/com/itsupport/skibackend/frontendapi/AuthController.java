package com.itsupport.skibackend.frontendapi;

import com.itsupport.skibackend.communication.payload.JwtResponse;
import com.itsupport.skibackend.communication.payload.LoginRequest;
import com.itsupport.skibackend.communication.payload.SignUpRequest;
import com.itsupport.skibackend.models.EUserRole;
import com.itsupport.skibackend.models.User;
import com.itsupport.skibackend.models.UserRole;
import com.itsupport.skibackend.models.persistence.UserRepository;
import com.itsupport.skibackend.models.persistence.UserRoleRepository;
import com.itsupport.skibackend.security.jwt.JwtUtils;
import com.itsupport.skibackend.security.services.UserDetailsImpl;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    /*
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<UserRole> roles = new HashSet<>();

        if (strRoles == null) {
            UserRole userRole = roleRepository.findByName(EUserRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        UserRole adminRole = roleRepository.findByName(EUserRole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        UserRole modRole = roleRepository.findByName(EUserRole.ROLE_MODERATOR)
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
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    */

    @PutMapping
    @RequestMapping(value = "/{id}/update")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<User> updateUserAsAdmin(@Valid @RequestBody @NotNull User userNew,@Valid @PathVariable UUID id) {
        Optional<User> user = userRepository.findById(id);
        Optional<User> existingUser = userRepository.findByUsername(userNew.getUsername());
        if(user.isPresent()) {
            if((existingUser.isPresent() && existingUser.get().getUsername().equals(userNew.getUsername())) || (existingUser.isEmpty()))
            {
                user.get().setUsername(userNew.getUsername());
                user.get().setPassword(encoder.encode(userNew.getPassword()));
                return ResponseEntity.ok(userRepository.save(user.get()));
            }
            else return ResponseEntity.badRequest().build();
        }
        else return  ResponseEntity.badRequest().build();
    }

    @PutMapping
    @RequestMapping(value = "/update")
    @PreAuthorize("hasRole('ADMINISTRATOR') || hasRole('ROLE_USER')")
    public ResponseEntity<JwtResponse> updateUser(@Valid @RequestBody @NotNull User userNew) {
        Optional<User> existingUser = userRepository.findByUsername(userNew.getUsername());
        //TODO: SHOULD BE IN TO DIFFERENT BRACNHES (TOKEN IS REQUIRED)
        if((existingUser.isPresent() && existingUser.get().getUsername().equals(userNew.getUsername())) || (existingUser.isEmpty())) {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
            user.get().setUsername(userNew.getUsername());
            user.get().setPassword(encoder.encode(userNew.getPassword()));
            User updatedUser = userRepository.save(user.get());
            //TODO: previous token add to blacklist
            //TODO: ERROR updatedUser's password does not match
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(updatedUser.getUsername(), updatedUser.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            List<String> roles = UserDetailsImpl.build(updatedUser).getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new JwtResponse(jwt, updatedUser.getId(), updatedUser.getUsername(), roles));
        }
        else return ResponseEntity.badRequest().build();
    }

    @PutMapping
    @RequestMapping(value = "/{id}/logoff")
    @PreAuthorize("hasRole('ADMINISTRATOR') || hasRole('USER')")
    public void logOff(@PathVariable UUID id) {
        //TODO: add to blackList
    }

    @PostMapping
    @RequestMapping(value = "/addUser")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<User> createUserByAdmin(@RequestBody SignUpRequest signUpRequest) {
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
        return ResponseEntity.ok(userRepository.save(user));

    }
}
