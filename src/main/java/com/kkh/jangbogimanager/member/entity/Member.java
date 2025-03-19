package com.kkh.jangbogimanager.member.entity;

import com.kkh.jangbogimanager.ledger.IncomeItem;
import com.kkh.jangbogimanager.ledger.Ledger;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String password;

	private String name;

	private String img;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Ledger> ledgers = new ArrayList<>();

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<IncomeItem> incomeItem = new ArrayList<>();
}
