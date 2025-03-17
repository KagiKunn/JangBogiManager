package com.kkh.jangbogimanager.ledger;

import com.kkh.jangbogimanager.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LedgerRepository extends JpaRepository<Ledger, Long> {

	List<Ledger> findByMember(Member member);
}
