
package com.wiz.test.demo.web.form;

public class LoginForm {

	private int companyCode = 0;
	private int personNumber = 0;
	private String password = null;
	
	public LoginForm() {
		
	}

	public int getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(int companyCode) {
		this.companyCode = companyCode;
	}

	public int getPersonNumber() {
		return personNumber;
	}

	public void setPersonNumber(int personNumber) {
		this.personNumber = personNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}