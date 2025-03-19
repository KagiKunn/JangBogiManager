package com.kkh.jangbogimanager.stats;

import com.kkh.jangbogimanager.ledger.dto.JangbogiItemResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface StatsService {
	List<CalenderDto> getLedgerStats(String ledgerId);

	List<JangbogiItemResponseDto> getDateExpenses(String ledgerId, String date);
}
