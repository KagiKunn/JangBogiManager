package com.kkh.jangbogimanager.ledger;

import com.kkh.jangbogimanager.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Entity
public class Ledger {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String invitation;
	private int income;
	private Long expenses;
	private Timestamp createAt;
	private Timestamp updateAt;

	@JoinColumn(name = "member_id")
	@ManyToOne
	private Member member;

	@OneToMany(mappedBy = "ledger", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<IncomeItem> IncomeItem = new ArrayList<>();

	@PrePersist
	public void prePersist() {
		// 현재 시간에서 초와 나노초를 0으로 설정
		this.createAt = Timestamp.valueOf(LocalDateTime.now().withSecond(0).withNano(0));
		this.updateAt = Timestamp.valueOf(LocalDateTime.now().withSecond(0).withNano(0));
	}
}
