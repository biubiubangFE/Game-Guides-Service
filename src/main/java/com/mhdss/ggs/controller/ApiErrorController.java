package com.mhdss.ggs.controller;

import com.mhdss.ggs.exception.NotLoginException;
import com.mhdss.ggs.utils.ErrorCode;
import com.mhdss.ggs.utils.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiErrorController {

	private static final Logger logger = LoggerFactory.getLogger(ApiErrorController.class);

	@ExceptionHandler
	public ResponseData<?> execute(Exception e) {
		logger.error(e.getMessage(), e);

		if (e == null) {
			return ResponseData.error(ErrorCode.UNKNOWN_ERROR);
		}

		if (e instanceof NotLoginException) {
			NotLoginException nle = (NotLoginException) e;
			Map<String, String> map = new HashMap<>(2);
			map.put("currentURL", nle.getCurrentURL());
			map.put("loginURL", nle.getLoginURL());
			return ResponseData.error(ErrorCode.NO_LOGIN, map);
		}

		if (e instanceof HttpMessageNotReadableException) {
			return ResponseData.error(ErrorCode.HTTP_MESSAGE_READ_ERROR);
		}

		if (e instanceof NoHandlerFoundException) {
			return ResponseData.error(ErrorCode.NO_HANDLER_FOUND);
		}

		return ResponseData.error(ErrorCode.UNKNOWN_ERROR);
	}
}
