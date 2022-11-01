package com.example.filepractice2.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Table(name = "profile")
@Entity
@Data
@Accessors(chain = true)
public class ProfileEntity extends BaseEntity{

    @Column(name = "full_name")
    private String name;

    @Column
    private LocalDate dateOfBirth;
    private String avatarUrl;
    private String email;
    private Long userId;
}
