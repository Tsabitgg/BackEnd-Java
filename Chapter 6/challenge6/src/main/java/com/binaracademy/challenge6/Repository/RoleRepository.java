package com.binaracademy.challenge6.Repository;

import com.binaracademy.challenge6.Enumeration.ERole;
import com.binaracademy.challenge6.Model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {

    Optional<Roles> findByRoleName(ERole name);

}