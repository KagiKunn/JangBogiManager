package com.kkh.jangbogimanager.ledger.dto;

import com.kkh.jangbogimanager.member.entity.Member;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LedgerResponseDto {
	private Long id;
	private String name;
	private String invitation;
	private Long income;
	private Long expenses;
	private String createAt;
	private String updateAt;
	private Member member;

}
