package com.kkh.jangbogimanager.ledger.dto;

import com.kkh.jangbogimanager.ledger.Ledger;
import com.kkh.jangbogimanager.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class JangbogiItemDto {
	private Long id;
	private String name;
	private int price;
	private String memo;
	private Timestamp createAt;
	private Timestamp completeAt;
	private Member createMember;
	private Member completeMember;
	private Ledger ledger;
}
