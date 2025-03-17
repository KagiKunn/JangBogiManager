package com.kkh.jangbogimanager.ledger.service;

import com.kkh.jangbogimanager.ledger.dto.LedgerResponseDto;

import java.util.List;

public interface LedgerService {

	void ledgerRegister(String ledgerName);

	List<LedgerResponseDto> getMyLedgers();

	List<LedgerResponseDto> getParticipationLedgers();
}
