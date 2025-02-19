package com.dentalclinic.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dentalclinic.model.Role;
import com.dentalclinic.model.User;
import com.dentalclinic.repository.RoleRepository;
import com.dentalclinic.repository.UserRepository;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Enregistrer un nouvel utilisateur avec un rôle spécifique
    public void registerUser(User user, String roleName) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        // Créer un Set<Role> et ajouter le rôle
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        // Assigner les rôles à l'utilisateur
        user.setRoles(roles);

        // Encoder le mot de passe
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Sauvegarder l'utilisateur
        userRepository.save(user);
    }

    // Trouver un utilisateur par son nom d'utilisateur
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    // Vérifier si un nom d'utilisateur existe déjà
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    // Vérifier si un email existe déjà
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    // Récupérer tous les utilisateurs
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}