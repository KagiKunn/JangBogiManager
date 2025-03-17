package com.kkh.jangbogimanager.member;

import com.kkh.jangbogimanager.member.entity.Member;
import com.kkh.jangbogimanager.member.entity.Role;
import com.kkh.jangbogimanager.member.repository.MemberRepository;
import com.kkh.jangbogimanager.member.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomMemberService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private final RoleRepository roleRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("🔍 loadUserByUsername : " + username);
		Member member = memberRepository.findByUsername(username);

		List<Role> roles = roleRepository.findByUsername(username);

		List<GrantedAuthority> auth = roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getRole()))
				.collect(Collectors.toList());

		return new User(member.getUsername(), member.getPassword(),auth);
	}
}
