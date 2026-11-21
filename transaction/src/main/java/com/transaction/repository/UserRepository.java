package com.transaction.repository;

import com.transaction.entity.User;
import lombok.Locked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail (String email);
}
