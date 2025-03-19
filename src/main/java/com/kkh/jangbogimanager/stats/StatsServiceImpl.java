package com.kkh.jangbogimanager.stats;

import com.kkh.jangbogimanager.ledger.JangbogiItem;
import com.kkh.jangbogimanager.ledger.dto.JangbogiItemResponseDto;
import com.kkh.jangbogimanager.ledger.service.LedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StatsServiceImpl implements StatsService {

	private final LedgerService ledgerService;

	@Override
	public List<CalenderDto> getLedgerStats(String ledgerId) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd HH:mm");

		// 가격이 0보다 큰 항목만 필터링
		List<JangbogiItemResponseDto> jangs = ledgerService.getJangbogiItems(ledgerId).stream()
				.filter(j -> j.getPrice() > 0)
				.toList();

		// completeAt을 기준으로 그룹화하고 가격을 합산
		Map<LocalDate, Integer> priceSumByDate = jangs.stream()
				.collect(Collectors.groupingBy(
						j -> LocalDateTime.parse(j.getCompleteAt(), formatter).toLocalDate(),  // completeAt을 LocalDate로 변환
						Collectors.summingInt(JangbogiItemResponseDto::getPrice)  // 해당 날짜의 price 값을 합산
				));

		// 날짜별로 하나의 CalenderDto 객체만 생성 (중복 방지)
		List<CalenderDto> calenderDtos = new ArrayList<>(priceSumByDate.entrySet().stream()
				.map(entry -> {
					LocalDate date = entry.getKey();
					int priceSum = entry.getValue(); // 날짜별 가격 합산 값

					// 해당 날짜에 대한 첫 번째 항목의 completeAt 값을 가져옴
					JangbogiItemResponseDto firstItem = jangs.stream()
							.filter(j -> LocalDateTime.parse(j.getCompleteAt(), formatter).toLocalDate().isEqual(date))
							.findFirst()
							.orElseThrow(() -> new IllegalArgumentException("해당 날짜에 대한 항목이 없습니다."));

					LocalDateTime completeAt = LocalDateTime.parse(firstItem.getCompleteAt(), formatter);

					return CalenderDto.builder()
							.title("-" + priceSum + "원") // 날짜와 가격 합산
							.price(priceSum)  // 합산된 가격
							.start(completeAt) // completeAt을 start로 사용
							.end(completeAt)   // completeAt을 end로 사용
							.build();
				})
				.collect(Collectors.toMap(
						c -> c.getStart().toLocalDate(),  // `start`의 LocalDate 기준으로 키 설정
						c -> c,
						(existing, replacement) -> existing)) // 중복된 날짜가 있으면 기존 값을 유지
				.values());  // 중복 제거 후 리스트로 변환

		return calenderDtos;
	}

	@Override
	public List<JangbogiItemResponseDto> getDateExpenses(String ledgerId, String date) {
		LocalDate day = LocalDate.parse(date);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd HH:mm");
		return ledgerService.getJangbogiItems(ledgerId).stream()
				.filter(j -> j.getCompleteAt() != null && !j.getCompleteAt().equals("N/A"))
				.filter(j -> LocalDateTime.parse(j.getCompleteAt(), formatter).toLocalDate().isEqual(day)).toList();
	}

}
