package com.kkh.jangbogimanager.member.repository;

import com.kkh.jangbogimanager.member.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
	List<Role> findByUsername(String username);
}
