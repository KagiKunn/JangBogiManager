package com.kkh.jangbogimanager.ledger;

import com.kkh.jangbogimanager.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Entity
public class JangbogiItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private int price;
	private String memo;
	private int category;
	private Timestamp createAt;
	private Timestamp completeAt;

	@ManyToOne
	@JoinColumn(name = "create_member")
	private Member createMember;

	@ManyToOne
	@JoinColumn(name = "complete_member")
	private Member completeMember;

	@ManyToOne
	@JoinColumn(name = "ledger_id")
	private Ledger ledger;
}
