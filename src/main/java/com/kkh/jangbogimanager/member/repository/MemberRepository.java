package com.kkh.jangbogimanager.member.repository;

import com.kkh.jangbogimanager.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findByUsername(String username);
}
