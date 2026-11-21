package com.sagun.ecommerce_backend.entity;

import com.sagun.ecommerce_backend.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="user_roles",joinColumns = @JoinColumn(name="user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false,length = 20)
    private List<Role> roles = new ArrayList<>();

}
