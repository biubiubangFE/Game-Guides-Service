package com.mhdss.ggs.exception;

/**
 * 未登录异常 携带当前登录失效接口URL,及登录接口的URL
 */
@SuppressWarnings("serial")
public class NotLoginException extends RuntimeException {

	private String currentURL;
	private String loginURL;

	public NotLoginException(String currentURL, String loginURL) {
		super(currentURL);
		this.currentURL = currentURL;
		this.loginURL = loginURL;
	}

	public NotLoginException(Throwable cause) {
		super(cause);
	}

	public String getCurrentURL() {
		return null == currentURL ? "" : currentURL;
	}

	public void setCurrentURL(String currentURL) {
		this.currentURL = currentURL;
	}

	public String getLoginURL() {
		return null == loginURL ? "" : loginURL;
	}

	public void setLoginURL(String loginURL) {
		this.loginURL = loginURL;
	}

}
