package com.xrparcade.tipper.services;

import com.xrparcade.tipper.dto.TipRequest;

public interface TipsService {
	public void requestTip(TipRequest request, String ipAddress) throws InvalidReasonException, TimerNotResetException;
}
