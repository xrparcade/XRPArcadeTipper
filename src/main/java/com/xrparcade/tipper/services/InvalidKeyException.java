package com.xrparcade.tipper.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidKeyException extends RuntimeException {

	private static final long serialVersionUID = -1139978680552675254L;

}
