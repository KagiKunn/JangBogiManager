package com.kkh.jangbogimanager.stats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CalenderDto {
	//식별자+테이블id
	private String id;
	//여기에 가격 합
	private String title;
	private int price;
	private String name;
	private LocalDateTime start;
	private LocalDateTime end;
	private String createMember;
	private String completeMember;

}
