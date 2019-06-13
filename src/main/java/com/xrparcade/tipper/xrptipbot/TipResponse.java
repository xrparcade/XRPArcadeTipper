package com.xrparcade.tipper.xrptipbot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TipResponse extends GenericResponse {

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class TipResponseData {
		private int code;
		private String network;
		private String slug;
		private AccountBalanceResponse.AccountBalanceResponseData balance;
	}

	private TipResponseData data;
}
