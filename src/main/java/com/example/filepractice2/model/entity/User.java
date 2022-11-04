package com.example.filepractice2.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Table(name = "user")
@Entity
@Data
@Accessors(chain = true)
public class User extends BaseEntity implements Serializable {

    private String username;
    private String password;
}
