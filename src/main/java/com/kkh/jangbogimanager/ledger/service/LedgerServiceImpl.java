package com.kkh.jangbogimanager.ledger.service;

import com.kkh.jangbogimanager.common.CommonService;
import com.kkh.jangbogimanager.ledger.Ledger;
import com.kkh.jangbogimanager.ledger.LedgerRepository;
import com.kkh.jangbogimanager.ledger.dto.LedgerResponseDto;
import com.kkh.jangbogimanager.member.Service.MemberService;
import com.kkh.jangbogimanager.member.entity.Member;
import com.kkh.jangbogimanager.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RequiredArgsConstructor
@Service
public class LedgerServiceImpl implements LedgerService {

	private final LedgerRepository ledgerRepository;
	private final MemberRepository memberRepository;
	private final MemberService memberService;

	@Transactional
	@Override
	public void ledgerRegister(String ledgerName) {
		Member member = memberRepository.findByUsername(memberService.getMemberUsername());

		CommonService commonService = new CommonService();

		Ledger ledger = Ledger.builder()
				.name(ledgerName)
				.invitation(commonService.generateInviteCode())
				.income(0)
				.expenses(0)
				.createAt(Timestamp.valueOf(LocalDateTime.now()))
				.updateAt(Timestamp.valueOf(LocalDateTime.now()))
				.member(member)
				.build();
		ledgerRepository.save(ledger);
	}

	@Override
	public List<LedgerResponseDto> getMyLedgers() {
		List<Ledger> ledgers = ledgerRepository.findByMember(memberRepository.findByUsername(memberService.getMemberUsername()));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd HH:mm");
		return ledgers.stream().map(f-> LedgerResponseDto.builder()
					.id(f.getId())
					.name(f.getName())
					.invitation(f.getInvitation())
					.expenses(f.getExpenses())
					.income(f.getIncome())
					.createAt(f.getCreateAt().toLocalDateTime().format(formatter))
					.updateAt(f.getUpdateAt().toLocalDateTime().format(formatter))
					.member(f.getMember())
					.build()
		).toList();
	}

	@Override
	public List<LedgerResponseDto> getParticipationLedgers() {
		return List.of();
	}
}
