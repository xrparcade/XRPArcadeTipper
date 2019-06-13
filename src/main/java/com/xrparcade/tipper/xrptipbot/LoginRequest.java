package com.xrparcade.tipper.xrptipbot;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginRequest extends AuthenticatedRequest {
	private String platform;
	private String model;
}
