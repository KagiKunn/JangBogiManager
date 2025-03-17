package com.kkh.jangbogimanager.ledger.dto;

import com.kkh.jangbogimanager.member.entity.Member;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class LedgerDto {
	private String id;
	private String name;
	private String invitation;
	private int income;
	private int expenses;
	private Timestamp createAt;
	private Timestamp updateAt;
	private Member member;
}
