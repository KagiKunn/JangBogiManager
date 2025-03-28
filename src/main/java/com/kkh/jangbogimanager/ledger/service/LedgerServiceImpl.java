package com.kkh.jangbogimanager.ledger.service;

import com.kkh.jangbogimanager.common.CommonService;
import com.kkh.jangbogimanager.ledger.JangbogiItem;
import com.kkh.jangbogimanager.ledger.JangbogiRepository;
import com.kkh.jangbogimanager.ledger.Ledger;
import com.kkh.jangbogimanager.ledger.LedgerRepository;
import com.kkh.jangbogimanager.ledger.dto.JangbogiItemDto;
import com.kkh.jangbogimanager.ledger.dto.JangbogiItemResponseDto;
import com.kkh.jangbogimanager.ledger.dto.LedgerResponseDto;
import com.kkh.jangbogimanager.member.Service.MemberService;
import com.kkh.jangbogimanager.member.entity.Member;
import com.kkh.jangbogimanager.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;


@RequiredArgsConstructor
@Service
public class LedgerServiceImpl implements LedgerService {

	private final LedgerRepository ledgerRepository;
	private final MemberRepository memberRepository;
	private final MemberService memberService;
	private final JangbogiRepository jangbogiRepository;

	@Transactional
	@Override
	public void ledgerRegister(String ledgerName) {
		Member member = memberRepository.findByUsername(memberService.getMemberUsername());

		CommonService commonService = new CommonService();

		Ledger ledger = Ledger.builder()
				.name(ledgerName)
				.invitation(commonService.generateInviteCode())
				.income(0L)
				.expenses(0L)
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
		return ledgers.stream().map(f -> LedgerResponseDto.builder()
				.id(f.getId())
				.name(f.getName())
				.invitation(f.getInvitation())
				.expenses(f.getExpenses())
				.income((long) f.getIncome())
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

	@Override
	public List<JangbogiItemResponseDto> getJangbogiItems(String no) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd HH:mm");
		return jangbogiRepository.findByLedgerId(Long.parseLong(no)).stream()
				.map(j -> JangbogiItemResponseDto.builder()
						.id(j.getId())
						.name(j.getName())
						.category(categoryDistributor(j.getCategory()))
						.memo(j.getMemo()!=null?j.getMemo():"N/A")
						.createAt(j.getCreateAt().toLocalDateTime().format(formatter))
						.ledger(j.getLedger().getId())
						.price(j.getPrice())
						.completeMember(j.getCompleteMember()!=null?j.getCompleteMember().getName() : "N/A")
						.completeAt(j.getCompleteAt()!=null?j.getCompleteAt().toLocalDateTime().format(formatter) : "N/A")
						.createMember(j.getCreateMember()!=null?j.getCreateMember().getName() : "N/A")
						.build()
				).toList();
	}

	@Transactional
	@Override
	public void jangbogiItemRegister(JangbogiItemDto jDto, String lno) {
		Member member = memberRepository.findByUsername(memberService.getMemberUsername());
		Ledger ledger = ledgerRepository.findById(Long.parseLong(lno)).orElse(null);
		JangbogiItem jangbogiItem = JangbogiItem.builder()
				.name(jDto.getName())
				.category(jDto.getCategory())
				.memo(jDto.getMemo())
				.price(0)
				.createAt(Timestamp.valueOf(LocalDateTime.now()))
				.createMember(member)
				.ledger(ledger)
				.build();
		jangbogiRepository.save(jangbogiItem);
	}

	@Transactional
	@Override
	public void jangbogiItemCompleter(JangbogiItemDto jDto, String id) {
		Member member = memberRepository.findByUsername(memberService.getMemberUsername());
		JangbogiItem j = jangbogiRepository.findById(jDto.getId()).orElseThrow(() -> new IllegalArgumentException("해당 아이템이 존재하지 않습니다."));

		JangbogiItem updatedJangbogi = j.toBuilder()
				.completeAt(Timestamp.valueOf(LocalDateTime.now()))
				.price(jDto.getPrice())
				.completeMember(member)
				.build();

		jangbogiRepository.save(updatedJangbogi); // 변경된 객체 저장
	}
	@Override
	public void jangbogiItemDistributor(List<JangbogiItemResponseDto> jDtos, Model model){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd HH:mm");
		List<JangbogiItemResponseDto> filteredDtos = jDtos.stream().filter(j -> LocalDateTime.parse(j.getCreateAt(),formatter).toLocalDate().isEqual(LocalDate.now())).toList();
		List<JangbogiItemResponseDto> preparations = filteredDtos.stream().filter(filteredDto -> filteredDto.getCompleteMember().equals("N/A")).toList();
		List<JangbogiItemResponseDto> complete = filteredDtos.stream().filter(filteredDto -> !filteredDto.getCompleteMember().equals("N/A")).toList();
		System.out.println(preparations);
		System.out.println(complete);
		model.addAttribute("preparations", preparations);
		model.addAttribute("completes", complete);
	}
	@Transactional
	@Override
	public void detailEditor(JangbogiItemDto jDto) {
		JangbogiItem j = jangbogiRepository.findById(jDto.getId()).orElse(null);

		categoryDistributor(jDto.getCategory());
		JangbogiItem jang = Objects.requireNonNull(j).toBuilder()
				.name(jDto.getName())
				.category(jDto.getCategory())
				.memo(jDto.getMemo()!=null?jDto.getMemo():"N/A")
				.price(jDto.getPrice())
				.build();
		jangbogiRepository.save(jang);
	}
	@Transactional
	@Override
	public void detailDeleter(String id) {
		JangbogiItem j = jangbogiRepository.findById(Long.parseLong(id)).orElse(null);
		jangbogiRepository.delete(Objects.requireNonNull(j));
	}

	@Override
	public LedgerResponseDto getLedger(String no) {
		Ledger l = ledgerRepository.findById(Long.parseLong(no)).orElseThrow(() -> new IllegalArgumentException("해당 아이템이 존재하지 않습니다."));
		return LedgerResponseDto.builder()
				.id(l.getId())
				.name(l.getName())
				.income((long) l.getIncome())
				.expenses(l.getExpenses())
				.createAt(l.getCreateAt().toLocalDateTime().toLocalDate().toString().replace("-","."))
				.updateAt(l.getUpdateAt().toLocalDateTime().toLocalDate().toString().replace("-","."))
				.build();
	}

	@Transactional
	@Override
	public void calculateLedger(String no) {
		List<JangbogiItemResponseDto> items = getJangbogiItems(no);
		Long tot = items.stream().mapToLong(JangbogiItemResponseDto::getPrice).sum();
		Ledger ledger = ledgerRepository.findById(Long.parseLong(no)).orElseThrow(()->new RuntimeException("없습니다.")).toBuilder()
				.expenses(tot)
				.build();
		ledgerRepository.save(ledger);
	}

	@Transactional
	@Override
	public void setIncome(Long no, Long income) {
		System.out.println("!!!!!!!!!!!!!!!!"+no);
		System.out.println(income+"!!!!!!!!!!!!!");
		Ledger ledger = ledgerRepository.findById(no).orElseThrow(()->new RuntimeException("err"));
		Ledger add = ledger.toBuilder()
				.income(income)
				.build();
		ledgerRepository.save(add);
	}

	@Transactional
	@Override
	public void ledgerDeleter(Long no) {
		ledgerRepository.deleteById(no);
	}

	public String categoryDistributor(int no) {
		return switch (no) {
			case 1 -> "식비";
			case 2 -> "카페/간식";
			case 3 -> "쇼핑";
			case 4 -> "취미/여가";
			case 5 -> "생활";
			case 6 -> "잡화";
			case 7 -> "주거/통신";
			default -> "기타";
		};
	}
	public int categoryDistributor(String txt) {
		return switch (txt) {
			case "식비" -> 1;
			case "카페/간식" -> 2;
			case "쇼핑" -> 3;
			case "취미/여가" -> 4;
			case "생활" -> 5;
			case "잡화" -> 6;
			case "주거/통신" -> 7;
			default -> 99;
		};
	}
}
