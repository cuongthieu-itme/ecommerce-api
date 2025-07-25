package com.ecommerce.service;

import com.ecommerce.model.Role;
import com.ecommerce.model.RoleName;
import com.ecommerce.model.User;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public User registerUser(String name, String email, String password, String roleString) {
        RoleName roleName;

        try {
            roleName = RoleName.valueOf(roleString.toUpperCase()); // remove "ROLE_" prefix from here
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Invalid role provided: " + roleString);
        }

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Error: Role not found"));

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encodePassword(password));
        user.setRoles(Collections.singleton(role));

        return userRepository.save(user);
    }
 
 
    public Long getUserIdFromPrincipal(Principal principal) {
        String email = principal.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();
    }

}


