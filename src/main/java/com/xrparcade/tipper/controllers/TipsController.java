package com.xrparcade.tipper.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.xrparcade.tipper.dto.TipRequest;
import com.xrparcade.tipper.services.InvalidReasonException;
import com.xrparcade.tipper.services.TimerNotResetException;
import com.xrparcade.tipper.services.TipsService;

@RestController
@RequestMapping("/tips")
public class TipsController {

	private final TipsService tipsService;

	public TipsController(TipsService tipsService) {
		this.tipsService = tipsService;
	}

	@PostMapping("/request/by-key/")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void reqeustTip(@RequestBody @Valid TipRequest tipRequest, HttpServletRequest servletRequest)
			throws InvalidReasonException, TimerNotResetException {
		tipsService.requestTip(tipRequest, servletRequest.getRemoteAddr());
	}

}
