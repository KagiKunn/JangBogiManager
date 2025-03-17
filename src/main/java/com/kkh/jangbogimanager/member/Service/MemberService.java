package com.kkh.jangbogimanager.member.Service;

import com.kkh.jangbogimanager.member.dto.MemberDto;
import com.kkh.jangbogimanager.member.dto.MemberResponseDto;
import com.kkh.jangbogimanager.member.entity.Member;

public interface MemberService {
	void signup(MemberDto memberDto);

	MemberResponseDto getMember();
	String getMemberUsername();
}
