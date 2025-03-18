package com.kkh.jangbogimanager.stats;

import com.kkh.jangbogimanager.ledger.dto.JangbogiItemResponseDto;
import com.kkh.jangbogimanager.ledger.service.LedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class StatsRestController {

	private final StatsService statsService;
	private final LedgerService ledgerService;

	@GetMapping("/stats/ledger")
	public List<CalenderDto> getLedger(@RequestParam String ledgerId) {
		List<CalenderDto> c = statsService.getLedgerStats(ledgerId);
		System.out.println(c);
		return c;
	}
}
