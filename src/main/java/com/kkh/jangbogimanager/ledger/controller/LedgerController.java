package com.kkh.jangbogimanager.ledger.controller;

import com.kkh.jangbogimanager.ledger.LedgerItem;
import com.kkh.jangbogimanager.ledger.service.LedgerService;
import com.kkh.jangbogimanager.member.Service.MemberService;
import com.kkh.jangbogimanager.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/ledger")
@Controller
public class LedgerController {

	private final LedgerService ledgerService;
	private final MemberService memberService;

	@GetMapping("/home")
	public String ledgerHome(Model model) {
		model.addAttribute("member", memberService.getMember());
		model.addAttribute("myLedgers", ledgerService.getMyLedgers());
		model.addAttribute("PartyLedger", ledgerService.getParticipationLedgers());
		return "/ledger/home";
	}

	@GetMapping("/new")
	public String newLedger() {
		return "/ledger/new";
	}

	@PostMapping("/registry")
	public String ledgerRegistry(@RequestParam String ledgerName) {
		ledgerService.ledgerRegister(ledgerName);
		return "redirect:/ledger/home";
	}

	@GetMapping("/detail/{no}")
	public String ledgerDetail(@PathVariable String no, Model model) {
		model.addAttribute("no", no);
		return "/ledger/detail";
	}

	@GetMapping("/newitem/{no}")
	public String newItem(@PathVariable String no, Model model) {
		return "/ledger/newitem";
	}
	@PostMapping("/newitem")
	public String registLedgerItem(@ModelAttribute LedgerItem ledgerItem, Model model) {
		return "redirect:/ledger/home";
	}

}
