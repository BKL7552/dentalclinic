package com.dentalclinic.repository;

import com.dentalclinic.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Trouver un rôle par son nom
    Optional<Role> findByName(String name);
}