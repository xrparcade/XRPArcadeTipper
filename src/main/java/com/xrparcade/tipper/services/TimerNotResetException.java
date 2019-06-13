package com.xrparcade.tipper.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TimerNotResetException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1244907706741321092L;

}
