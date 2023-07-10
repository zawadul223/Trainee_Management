package com.bjit.tms.repository;

import com.bjit.tms.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    public UserEntity findByEmail(String email);
}
