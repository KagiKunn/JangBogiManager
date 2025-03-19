package com.kkh.jangbogimanager.stats;

import com.kkh.jangbogimanager.ledger.dto.JangbogiItemResponseDto;
import com.kkh.jangbogimanager.ledger.service.LedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
	@PostMapping("/stats/detail")
	public List<JangbogiItemResponseDto> detail(@RequestBody Map<String, Object> data, Model model) {
		return statsService.getDateExpenses((String) data.get("ledgerId"), (String) data.get("date"));
	}
}
