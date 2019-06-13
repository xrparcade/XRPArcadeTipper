package com.xrparcade.tipper.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table
@Data
public class Reason {
	@Id
	private String id;

	private String description;

	@Column(nullable = false)
	@Size(max = 64, min = 64)
	private String hashedKey;

	private int resetTimer;

	@Digits(fraction = 6, integer = 2)
	private BigDecimal amount;

	@OneToMany(mappedBy = "reason")
	private List<Tip> tips;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> allowedSubnets;
}
