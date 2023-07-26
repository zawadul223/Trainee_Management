package com.bjit.tms.repository.user_repositories;

import com.bjit.tms.entity.user_entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    public UserEntity findByEmail(String email);
    public UserEntity findIdByEmail(String email);
}
