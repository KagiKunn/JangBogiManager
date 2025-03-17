package com.kkh.jangbogimanager.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberDto {
	private int id;
	private String username;
	private String password;
	private String name;
	private MultipartFile img;
}
