package com.dentalclinic.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dentalclinic.model.Role;
import com.dentalclinic.model.User;
import com.dentalclinic.model.Doctor;
import com.dentalclinic.repository.RoleRepository;
import com.dentalclinic.repository.UserRepository;
import com.dentalclinic.repository.DoctorRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository; 
    @Autowired
    private PasswordEncoder passwordEncoder; 
    @PostConstruct
    public void init() {
        // Créer les rôles s'ils n'existent pas déjà
        Role adminRole = createRoleIfNotFound("ADMIN");
        Role doctorRole = createRoleIfNotFound("DOCTOR");
        Role secretaryRole = createRoleIfNotFound("SECRETARY");

        // Créer un utilisateur administrateur par défaut
        createUserIfNotFound("admin", "admin123", "admin@dentalclinic.com", adminRole);

        // Créer des utilisateurs médecin par défaut et associer un docteur
        createDoctorIfNotFound("docteur1", "docteur123", "docteur1@dentalclinic.com", doctorRole, "Dr. Bako Laurent", "1234567890", "bako@clinic.com");
        createDoctorIfNotFound("docteur2", "docteur123", "docteur2@dentalclinic.com", doctorRole, "Dr. Robleh Ahmed", "1234567890", "robleh@clinic.com");

        // Créer un utilisateur secrétaire par défaut
        createUserIfNotFound("secretaire", "secretaire123", "secretaire@dentalclinic.com", secretaryRole);
    }

    private Role createRoleIfNotFound(String name) {
        return roleRepository.findByName(name).orElseGet(() -> {
            Role role = new Role();
            role.setName(name);
            return roleRepository.save(role);
        });
    }

    private void createUserIfNotFound(String username, String password, String email, Role role) {
        if (!userRepository.existsByUsername(username)) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password)); // Encoder le mot de passe
            user.setEmail(email);
            user.setRoles(Collections.singleton(role)); // Assigner le rôle
            userRepository.save(user);
        }
    }

    private void createDoctorIfNotFound(String username, String password, String email, Role role, String name, String phoneNumber, String doctorEmail) {
        if (!userRepository.existsByUsername(username)) {
            // Créer l'utilisateur pour le docteur
            User doctorUser = new User();
            doctorUser.setUsername(username);
            doctorUser.setPassword(passwordEncoder.encode(password)); // Encoder le mot de passe
            doctorUser.setEmail(email);
            doctorUser.setRoles(Collections.singleton(role)); // Assigner le rôle
            userRepository.save(doctorUser);

            // Créer un enregistrement dans l'entité Doctor
            if (!doctorRepository.existsByEmail(doctorEmail)) {
                Doctor doctor = new Doctor();
                doctor.setName(name);
                doctor.setPhoneNumber(phoneNumber);
                doctor.setEmail(doctorEmail);
                doctor.setUser(doctorUser); // Associer l'utilisateur au docteur
                doctorRepository.save(doctor);
            }
        }
    }
}
