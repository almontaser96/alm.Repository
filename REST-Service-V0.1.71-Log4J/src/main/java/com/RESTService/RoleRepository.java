package com.RESTService;

import org.springframework.data.jpa.repository.JpaRepository;
import com.RESTService.Entity.RoleEntity;

	public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
		RoleEntity findById(long id);
	}
		

