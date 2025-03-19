package com.kkh.jangbogimanager.ledger.service;

import com.kkh.jangbogimanager.ledger.dto.JangbogiItemDto;
import com.kkh.jangbogimanager.ledger.dto.JangbogiItemResponseDto;
import com.kkh.jangbogimanager.ledger.dto.LedgerResponseDto;
import org.springframework.ui.Model;

import java.util.List;

public interface LedgerService {

	void ledgerRegister(String ledgerName);

	List<LedgerResponseDto> getMyLedgers();

	List<LedgerResponseDto> getParticipationLedgers();

	List<JangbogiItemResponseDto> getJangbogiItems(String no);

	void jangbogiItemRegister(JangbogiItemDto jDto, String lno);

	void jangbogiItemCompleter(JangbogiItemDto jDto, String id);

	void jangbogiItemDistributor(List<JangbogiItemResponseDto> jDtos, Model model);

	void detailEditor(JangbogiItemDto jDto);

	void detailDeleter(String id);

	LedgerResponseDto getLedger(String no);

	void calculateLedger(String no);
}
