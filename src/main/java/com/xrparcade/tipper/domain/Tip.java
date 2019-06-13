package com.xrparcade.tipper.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(indexes = { @Index(name = "IDX_tips_reason_user", columnList = "reason_id,user", unique = false) })
@Data
public class Tip {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private Reason reason;

	@Column(nullable = false)
	@Size(min = 3)
	private String user;

	@Column(nullable = false)
	private Date date;

}
