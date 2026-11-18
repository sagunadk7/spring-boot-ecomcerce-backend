package com.sagun.ecommerce_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users",
uniqueConstraints = {@UniqueConstraint(name= "uk_users_email",columnNames = "email"),
@UniqueConstraint(name = "uk_users_username", columnNames = "username")})
public class User extends BaseEntity {

    @Column(name = "user_name", nullable = false,length = 50)
    private String username;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "phone_number",nullable = false)
    private int phoneNumber;

    @Column(name = "password_hash",nullable = false)
    private String passwordHash;


}
