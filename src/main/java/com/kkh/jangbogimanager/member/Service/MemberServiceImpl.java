package com.kkh.jangbogimanager.member.Service;

import com.kkh.jangbogimanager.member.dto.MemberDto;
import com.kkh.jangbogimanager.member.dto.MemberResponseDto;
import com.kkh.jangbogimanager.member.entity.Member;
import com.kkh.jangbogimanager.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

	@Value("${file.upload-path}")
	private String uploadPath;

	private final MemberRepository memberRepository;

	@Transactional
	@Override
	public void signup(MemberDto memberDto) {
		UUID uuid = UUID.randomUUID();
		String imgName = uuid + "_" + memberDto.getImg().getOriginalFilename();
		Path path = Paths.get(uploadPath + imgName);
		try{
			Files.write(path, memberDto.getImg().getBytes());
		} catch (IOException e) {
			System.out.println("!!!! imgFile is wrong !!!!");
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Member member = Member.builder()
				.username(memberDto.getUsername())
				.password(encoder.encode(memberDto.getPassword()))
				.name(memberDto.getName())
				.img(imgName)
				.build();

		memberRepository.save(member);
	}

	public MemberResponseDto getMember(){
		Member m = memberRepository.findByUsername(getMemberUsername());
		return MemberResponseDto.builder()
				.id(m.getId())
				.username(m.getUsername())
				.name(m.getName())
				.img(m.getImg())
				.build();
	}



	public String getMemberUsername(){
		return (((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
	}
}
