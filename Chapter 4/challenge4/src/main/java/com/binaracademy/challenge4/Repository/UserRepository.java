package com.binaracademy.challenge4.Repository;

import com.binaracademy.challenge4.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("SELECT usersId, usersname, emailAddress, password FROM Users")
    List<Users> findByUsername(String username);

}
