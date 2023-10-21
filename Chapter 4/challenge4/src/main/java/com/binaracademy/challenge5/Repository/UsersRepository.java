package com.binaracademy.challenge5.Repository;

import com.binaracademy.challenge5.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

}

