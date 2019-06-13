package com.xrparcade.tipper.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidReasonException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8583177293020846009L;

}
