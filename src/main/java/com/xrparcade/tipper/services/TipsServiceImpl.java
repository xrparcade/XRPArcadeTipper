package com.xrparcade.tipper.services;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import com.xrparcade.tipper.domain.Reason;
import com.xrparcade.tipper.domain.Tip;
import com.xrparcade.tipper.dto.TipRequest;
import com.xrparcade.tipper.repositories.ReasonsRepository;
import com.xrparcade.tipper.repositories.TipsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TipsServiceImpl implements TipsService {

	private final TipsRepository tipsRepository;
	private final ReasonsRepository reasonsRepository;
	private final XrpTipBotService xrpTipBotService;

	private final Set<String> locks = ConcurrentHashMap.newKeySet();

	public TipsServiceImpl(final TipsRepository tipsRepository, final ReasonsRepository reasonsRepository,
			final XrpTipBotService xrpTipBotService) {
		this.tipsRepository = tipsRepository;
		this.reasonsRepository = reasonsRepository;
		this.xrpTipBotService = xrpTipBotService;
	}

	@Override
	public void requestTip(TipRequest request, String ipAddress) throws InvalidReasonException, TimerNotResetException {
		String handle = request.getHandle().toLowerCase();
		if (!locks.add(handle)) {
			throw new TimerNotResetException();
		}

		try {
			// check if reason given is valid
			Optional<Reason> reason = reasonsRepository.findById(request.getReason());
			if (!reason.isPresent()) {
				throw new InvalidReasonException();
			}

			log.debug("Found reason {} with {} allowed subnets", reason.get().getId(),
					reason.get().getAllowedSubnets().size());

			if (!validAddress(reason.get(), ipAddress)) {
				throw new InvalidKeyException();
			}

			if (!validKey(reason.get(), request.getKey())) {
				throw new InvalidKeyException();
			}

			// check if user has been tipped before this reason, and if so then if the reset
			// timer has passed
			Optional<Tip> lastTip = tipsRepository.findFirstByReasonAndUserOrderByDateDesc(reason.get(), handle);
			if (lastTip.isPresent() && (reason.get().getResetTimer() == 0 || (lastTip.get().getDate().getTime()
					+ reason.get().getResetTimer() * 1000 > System.currentTimeMillis()))) {
				throw new TimerNotResetException();
			}

			// tip user
			if (xrpTipBotService.tip("twitter", request.getHandle(), reason.get().getAmount())) {
				// add tip to db
				Tip tip = new Tip();
				tip.setDate(new Date());
				tip.setReason(reason.get());
				tip.setUser(handle);
				tipsRepository.save(tip);
			}
		} catch (Exception e) {
			throw (e);
		} finally {
			locks.remove(handle);
		}
	}

	private boolean validAddress(Reason reason, String ipAddress) {
		for (String subnetStr : reason.getAllowedSubnets()) {
			try {
				log.debug("Matching ip {} to {}", ipAddress, subnetStr);
				IpAddressMatcher matcher = new IpAddressMatcher(subnetStr);
				if (matcher.matches(ipAddress)) {
					return true;
				}
			} catch (IllegalArgumentException e) {
				log.warn("Could not parse subnet {} for reason {}", subnetStr, reason.getId());
			}
		}

		return false;
	}

	private boolean validKey(Reason reason, String key) {
		return Hashing.sha256().hashString(key, StandardCharsets.UTF_8).toString().equals(reason.getHashedKey());
	}
}
