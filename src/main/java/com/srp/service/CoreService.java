package com.srp.service;

import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

@Service
public class CoreService {

	@Retryable(value = {HttpStatusCodeException.class },
			maxAttempts = 4,backoff = @Backoff(0), exclude = HttpClientErrorException.class)
	public int login(String pId, String securityNumber) {
		System.out.println("Trying to call Core Service!! :: "+Thread.currentThread().getName());
		return getStatus(pId);
	}

	/**
	 * This method with throw HttpStatusCodeException based on pId
	 * 
	 * HttpClientErrorException child of HttpsStatusCodeException and only entertain
	 * client error (4_xx) not the server error. 
	 * 
	 * HttpServerErrorException child of
	 * HttpsStatusCodeException and only entertain server error (5_xx) not the
	 * client error.
	 * 
	 * @param pId
	 * @return
	 */
	private int getStatus(String pId) {

		// Some core logic for response
		if ("100".equals(pId)) {
			System.out.println("GATEWAY_TIMEOUT Occured!!");
			throw new HttpServerErrorException(HttpStatus.GATEWAY_TIMEOUT); // 504
		}
		if ("101".equals(pId)) {
			System.out.println("INTERNAL_SERVER_ERROR Occured!!");
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);// 500
		}

		if ("102".equals(pId)) {
			System.out.println("BAD_REQUEST!!");
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST); // 400
		}
		if ("103".equals(pId)) {
			System.out.println("UNAUTHORIZED!!");
			throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED); // 401
		}
		if ("104".equals(pId)) {
			System.out.println("FORBIDDEN!!");
			throw new HttpClientErrorException(HttpStatus.FORBIDDEN); // 403
		}
		return 0;
	}

}
