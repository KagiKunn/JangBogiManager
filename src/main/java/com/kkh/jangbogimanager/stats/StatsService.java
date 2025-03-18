package com.kkh.jangbogimanager.stats;

import java.util.List;

public interface StatsService {
	List<CalenderDto> getLedgerStats(String ledgerId);
}
