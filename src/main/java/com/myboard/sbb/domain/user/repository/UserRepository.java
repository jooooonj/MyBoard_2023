package com.myboard.sbb.domain.user.repository;

import com.myboard.sbb.domain.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    Optional<SiteUser> findByUsername(String username);

    Optional<SiteUser> findByUserId(String userId);
}
