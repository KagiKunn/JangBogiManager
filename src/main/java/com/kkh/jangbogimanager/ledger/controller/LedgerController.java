package com.kkh.jangbogimanager.ledger.controller;

import com.kkh.jangbogimanager.ledger.dto.JangbogiItemDto;
import com.kkh.jangbogimanager.ledger.service.LedgerService;
import com.kkh.jangbogimanager.member.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
		return "/ledger/detail";
	}

	@GetMapping("/detail/{no}")
	public String ledgerDetail(@PathVariable String no, Model model) {
		ledgerService.jangbogiItemDistributor(ledgerService.getJangbogiItems(no),model);
		ledgerService.calculateLedger(no);
		model.addAttribute("ledger",ledgerService.getLedger(no));
		model.addAttribute("no", no);
		return "/ledger/detail";
	}

	@GetMapping("/newitem/{no}")
	public String newItem(@PathVariable String no, Model model) {
		return "/ledger/newitem";
	}
	@PostMapping("/newitem")
	public String registLedgerItem(@ModelAttribute JangbogiItemDto jDto, @RequestParam String lno, Model model) {
		ledgerService.jangbogiItemRegister(jDto, lno);
		return "redirect:/ledger/home";
	}

	@PostMapping("/jangbogiComplete/{id}")
	public String jangbogiComplete(@PathVariable String id, @ModelAttribute JangbogiItemDto jDto, Model model) {
		System.out.println(jDto);
		System.out.println(id);
		ledgerService.jangbogiItemCompleter(jDto,id);
		return "/ledger/detail";
	}

	@GetMapping("/editdetail/{no}")
	public String editDetail(@PathVariable String no, Model model) {
		ledgerService.jangbogiItemDistributor(ledgerService.getJangbogiItems(no),model);
		model.addAttribute("no", no);
		return "/ledger/editdetail";
	}
	@PostMapping("/editdetail/{no}")
	public String detailEditor(@PathVariable String no, @ModelAttribute JangbogiItemDto jDto) {
		ledgerService.detailEditor(jDto);
		return "redirect:/ledger/detail/"+no;
	}
	@PostMapping("/deletedetail")
	public String detailDeleter(@RequestBody Map<String, String> req) {
		ledgerService.detailDeleter(req.get("id"));
		return "redirect:/ledger/detail/"+req.get("no");
	}

	@PostMapping("/income")
	public String incomeRegister(@RequestBody Map<String, Long> req){
		return "/ledger/detail"+req.get("no");
	}
}
