package com.xrparcade.tipper.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class TipRequest {

	@NotBlank
	private String handle;

	@NotBlank
	private String reason;
	
	@NotBlank
	private String key;
}
