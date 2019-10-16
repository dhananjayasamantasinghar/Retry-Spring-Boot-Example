package com.srp.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class GasAuditService {

	public String auditToGass(String pId, int gasStatus, String fNumber, String transactionName) {
		return "WS:OK :: GasAuditService called for  Pid :" + pId + " and gasStatus = failed :: Time:"
				+ LocalDateTime.now();
	}

}
