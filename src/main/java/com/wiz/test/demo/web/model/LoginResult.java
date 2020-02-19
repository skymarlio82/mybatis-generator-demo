
package com.wiz.test.demo.web.model;

public class LoginResult {
	
	private boolean status = false;
	private String message = null;
	private String token = null;
	
	public LoginResult() {
		
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}