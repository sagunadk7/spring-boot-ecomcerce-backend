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
uniqueConstraints = {@UniqueConstraint(name= "uk_users_phone_number",columnNames = "phone_number"),
@UniqueConstraint(name = "uk_users_username", columnNames = "username")})
public class User extends BaseEntity {

    @Column(name = "user_name",length = 50)
    private String username;

    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="user_roles",joinColumns = @JoinColumn(name="user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false,length = 20)
    private List<Role> roles = List.of(Role.USER);

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private LoginCode loginCode;

}
