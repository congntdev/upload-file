package com.example.filepractice2.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "user")
@Entity
@Data
@Accessors(chain = true)
public class User extends BaseEntity{

    private String username;
    private String password;
}
