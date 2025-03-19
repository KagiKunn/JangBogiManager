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
@Getter
@Builder
@Entity
public class IncomeItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private int price;
	//등록일
	private Timestamp priceAt;
	private String memo;


	@JoinColumn(name = "member_id")
	@ManyToOne
	private Member member;
	@JoinColumn(name = "ledger_id")
	@ManyToOne
	private Ledger ledger;
}
