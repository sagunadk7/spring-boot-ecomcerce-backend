package com.sagun.ecommerce_backend.entity;

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



}
