package com.example.filepractice2.repository;

import com.example.filepractice2.model.entity.User;
import com.example.filepractice2.model.response.ProfileResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findUserById(Long id);

    @Query("select u.id as id, u.username as username, " +
            "p.dateOfBirth as dateOfBirth, p.avatarUrl as avatarUrl, p.email as email " +
            "from User as u join Profile as p on u.id = p.userId " +
            "where u.username = :username and u.password = :password")
    ProfileResponse findProfileByUser(@Param("username") String username,
                                      @Param("password") String password);
}
