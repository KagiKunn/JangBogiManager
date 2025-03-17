package com.kkh.jangbogimanager.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/stats")
@Controller
public class StatsController {
	private final StatsService statsService;

	@GetMapping("/home")
	public String home() {

		return "stats/home";
	}
}
