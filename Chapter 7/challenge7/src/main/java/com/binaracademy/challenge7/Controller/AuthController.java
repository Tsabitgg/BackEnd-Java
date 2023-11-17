package com.binaracademy.challenge7.Controller;

import com.binaracademy.challenge7.Config.JwtUtils;
import com.binaracademy.challenge7.Enumeration.ERole;
import com.binaracademy.challenge7.Model.Request.LoginRequest;
import com.binaracademy.challenge7.Model.Request.SignupRequest;
import com.binaracademy.challenge7.Model.Response.JwtResponse;
import com.binaracademy.challenge7.Model.Response.MessageResponse;
import com.binaracademy.challenge7.Model.Roles;
import com.binaracademy.challenge7.Model.User;
import com.binaracademy.challenge7.Model.UserDetailsImpl;
import com.binaracademy.challenge7.Repository.RoleRepository;
import com.binaracademy.challenge7.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository usersRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserRepository usersRepository,
                          JwtUtils jwtUtils, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.usersRepository = usersRepository;
        this.jwtUtils = jwtUtils;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest login) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUserId(), userDetails.getUsername(),
                userDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        Boolean usernameExist = usersRepository.existsByUsername(signupRequest.getUsername());
        if(Boolean.TRUE.equals(usernameExist)) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        Boolean emailExist = usersRepository.existsByEmail(signupRequest.getEmail());
        if(Boolean.TRUE.equals(emailExist)) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }

        User users = new User(signupRequest.getUsername(), signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Roles> roles = new HashSet<>();

        if(strRoles == null) {
            Roles role = roleRepository.findByRoleName(ERole.ROLE_MERCHANT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(role);
        } else {
            strRoles.forEach(role -> {
                Roles roles1 = roleRepository.findByRoleName(ERole.valueOf(role))
                        .orElseThrow(() -> new RuntimeException("Error: Role " + role + " is not found"));
                roles.add(roles1);
            });
        }
        users.setRoles(roles);
        usersRepository.save(users);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

}