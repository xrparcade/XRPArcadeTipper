package com.xrparcade.tipper.xrptipbot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LoginResponse extends GenericResponse {

	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class LoginResponseData {
		private int uid;
		private String network;
		private String slug;
		private String uuidv4;
	}

	private LoginResponseData data;
}
