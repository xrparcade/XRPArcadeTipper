package com.xrparcade.tipper.xrptipbot;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TipRequest extends AuthenticatedRequest {
	private BigDecimal amount;
	private String to;
}
