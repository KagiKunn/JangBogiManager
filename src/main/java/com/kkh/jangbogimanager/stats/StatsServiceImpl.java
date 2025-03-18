package com.kkh.jangbogimanager.stats;

import com.kkh.jangbogimanager.ledger.JangbogiItem;
import com.kkh.jangbogimanager.ledger.dto.JangbogiItemResponseDto;
import com.kkh.jangbogimanager.ledger.service.LedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StatsServiceImpl implements StatsService {

	private	final LedgerService ledgerService;

	@Override
	public List<CalenderDto> getLedgerStats(String ledgerId) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd HH:mm");
		List<JangbogiItemResponseDto> jangs = ledgerService.getJangbogiItems(ledgerId).stream()
				.filter(j -> j.getPrice()>0).toList();
		List<CalenderDto> calenderDtos = jangs.stream().map(j->CalenderDto.builder()
				.id("jangbogi"+j.getId())
				.title((j.getPrice())+"원")
				.name(j.getName())
				.price(j.getPrice())
				.completeMember(j.getCompleteMember())
				.createMember(j.getCreateMember())
				.start(LocalDateTime.parse(j.getCompleteAt(),formatter))
				.end(LocalDateTime.parse(j.getCompleteAt(),formatter))
				.build()
		).toList();
		return calenderDtos;
	}
}
