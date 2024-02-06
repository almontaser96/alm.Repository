package com.RESTService;

import org.springframework.data.jpa.repository.JpaRepository;
import com.RESTService.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);
}
	