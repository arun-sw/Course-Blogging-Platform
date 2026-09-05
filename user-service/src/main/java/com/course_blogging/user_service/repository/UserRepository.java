package com.course_blogging.user_service.repository;

import com.course_blogging.user_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByemail(String email);
    boolean existsByEmail(String email);
}
