package com.srp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import com.srp.service.CoreService;
import com.srp.service.GasAuditService;

@RestController
@RequestMapping("/santander")
public class LoginController {

	@Autowired
	private GasAuditService gasService;

	@Autowired
	private CoreService coreService;

	@GetMapping("/login")
	public String login(@RequestParam String pId, @RequestParam String securityNumber) {
		String response = null;
		int status = 0;
		long startTime=System.currentTimeMillis();
		try {
			System.out.println("Test Controller ::"+Thread.currentThread().getName());
			status = coreService.login(pId, securityNumber);
			response = "received Response from Core";
		} catch (HttpStatusCodeException e) {
			long endTime=System.currentTimeMillis();
			//handleError call and gasAudit call
			
			System.out.println("Time Taken ::"+(endTime-startTime));
			response = gasService.auditToGass(pId, status, "011", "LOGON");
			System.out.println(response);
			
		}
		return response;
	}

}
