package com.kkh.jangbogimanager.ledger;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JangbogiRepository extends JpaRepository<JangbogiItem,Long> {
	List<JangbogiItem> findByLedgerId(long l);
}
