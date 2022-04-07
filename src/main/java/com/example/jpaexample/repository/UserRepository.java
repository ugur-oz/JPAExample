package com.example.jpaexample.repository;

import com.example.jpaexample.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameIs(String username);

    List<User> findByUsernameStartingWith(String startwith);

    List<User> findByAttendancesIsEmpty();
}
