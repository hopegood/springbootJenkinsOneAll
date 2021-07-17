package com.diandian.modal;

public class SysUser {

	
	private String username;
	private String password;
	private String salt;
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public SysUser(String username, String password){
		this.username = username;
		this.password = password;
	}
}
