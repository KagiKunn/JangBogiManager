package com.kkh.jangbogimanager.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	@GetMapping("/ledger/{no}")
	public String ledger(@PathVariable int no, Model model) {
		model.addAttribute("no", no);
		return "stats/ledger";
	}
}
