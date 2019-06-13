package com.xrparcade.tipper.xrptipbot;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class AccountBalanceResponse extends GenericResponse {

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class AccountBalanceResponseData {
		private String uid;
		private String network;
		private String slug;
		private String uuidv4;
		private Map<String, BigDecimal> balance;
	}

	private AccountBalanceResponseData data;
}
