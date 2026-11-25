package com.sagun.ecommerce_backend.repository;

import com.sagun.ecommerce_backend.entity.LoginCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginCodeRepository extends JpaRepository<LoginCode, Long> {
}
