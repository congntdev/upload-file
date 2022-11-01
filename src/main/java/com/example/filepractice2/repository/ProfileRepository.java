package com.example.filepractice2.repository;

import com.example.filepractice2.model.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    Optional<ProfileEntity> findProfileById(Long id);

    List<ProfileEntity> fillAllProfile();
}
