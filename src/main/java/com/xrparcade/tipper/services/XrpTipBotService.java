package com.xrparcade.tipper.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.xrparcade.tipper.xrptipbot.AccountBalanceResponse;
import com.xrparcade.tipper.xrptipbot.AuthenticatedRequest;
import com.xrparcade.tipper.xrptipbot.GenericXrpTipBotException;
import com.xrparcade.tipper.xrptipbot.LoginRequest;
import com.xrparcade.tipper.xrptipbot.TipRequest;
import com.xrparcade.tipper.xrptipbot.TipResponse;
import com.xrparcade.tipper.xrptipbot.XrpTipBotConfig;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class XrpTipBotService {
	private final XrpTipBotClient client;
	private final XrpTipBotConfig config;

	public XrpTipBotService(final XrpTipBotClient client, final XrpTipBotConfig config) {
		this.client = client;
		this.config = config;

		// authenticate with tipbot. if this fails service constructor should fail
		// disabling app from booting
		// ignore if xrptipbot responds token already in use as login should only be
		// called on first run of the app
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setToken(config.getToken());
		loginRequest.setPlatform(System.getProperty("java.version"));
		loginRequest.setModel("XRPArcade Tipper");
		client.login(loginRequest);

		// check remaining balance
		log.info("Remaining balance {}", getReaminingBalance());
	}

	public boolean tip(String network, String slug, BigDecimal amount) {
		TipRequest request = new TipRequest();
		request.setToken(config.getToken());
		request.setTo("xrptipbot://" + network + "/" + slug);
		request.setAmount(amount);

		TipResponse response = client.sendTip(request);
		if (response.isError() || !"TIP_OK".equals(response.getMessage())) {
			log.error("Tip to {}/{} failed with error {}", network, slug, response.getMessage());
			return false;
		}

		log.info("Sent {} to {}/{}. Remaining balance {}", amount, network, slug,
				response.getData().getBalance().getBalance().get("XRP"));

		return true;
	}

	public BigDecimal getReaminingBalance() {
		AuthenticatedRequest authenticatedRequest = new AuthenticatedRequest();
		authenticatedRequest.setToken(config.getToken());
		AccountBalanceResponse accountBalanceResponse = client.getBalance(authenticatedRequest);
		if (accountBalanceResponse.isError()) {
			log.error("Could not fetch account balance {}", accountBalanceResponse);
			throw new GenericXrpTipBotException(accountBalanceResponse.getMessage());
		}
		return accountBalanceResponse.getData().getBalance().get("XRP");
	}
}
