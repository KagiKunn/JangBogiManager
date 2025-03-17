package com.kkh.jangbogimanager.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberResponseDto {
	private Long id;
	private String username;
	private String name;
	private String img;
}
