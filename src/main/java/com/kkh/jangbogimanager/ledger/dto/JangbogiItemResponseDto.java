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
public class JangbogiItemResponseDto {
	private Long id;
	private String name;
	private int price;
	private String memo;
	private String category;
	private String createAt;
	private String completeAt;
	private String createMember;
	private String completeMember;
	private Long ledger;
}
