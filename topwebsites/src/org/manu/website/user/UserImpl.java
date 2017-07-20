package org.manu.website.user;

public class UserImpl implements User {
	private String username;
	private String password;
	
	public UserImpl(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	@Override
	public String getUserName() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

}
