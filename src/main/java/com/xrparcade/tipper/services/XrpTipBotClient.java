package com.xrparcade.tipper.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.xrparcade.tipper.xrptipbot.AccountBalanceResponse;
import com.xrparcade.tipper.xrptipbot.AuthenticatedRequest;
import com.xrparcade.tipper.xrptipbot.LoginRequest;
import com.xrparcade.tipper.xrptipbot.LoginResponse;
import com.xrparcade.tipper.xrptipbot.TipRequest;
import com.xrparcade.tipper.xrptipbot.TipResponse;
import com.xrparcade.tipper.xrptipbot.XrpTipBotConfig;

@FeignClient(value = "xrptipbot", url = "https://www.xrptipbot.com/app/api/", configuration = XrpTipBotConfig.class)
public interface XrpTipBotClient {

	@PostMapping("/action:login/")
	public LoginResponse login(@RequestBody LoginRequest loginRequest);

	@PostMapping("/action:balance/")
	public AccountBalanceResponse getBalance(@RequestBody AuthenticatedRequest balanceRequest);

	@PostMapping("/action:tip")
	public TipResponse sendTip(@RequestBody TipRequest tipRequest);
}
