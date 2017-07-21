package org.ml.topsites.test.manager;

import java.nio.charset.Charset;
import java.util.Base64;

import org.junit.Assert;
import org.junit.Test;
import org.ml.topsites.website.dao.ConnectionManager;
import org.ml.topsites.website.user.AuthenticationService;

public class AuthenticationServiceTest {
	private AuthenticationService service;
	private long time;
	
	public AuthenticationServiceTest(){
		service = new AuthenticationService();
		time = System.currentTimeMillis();
	}
	
	@Test
	public void testValidUser(){
		String validUser="mayank_"+time;
		String password = "mayank_manulife";
		
		String insertSQL = 
				"insert into users (user_name, user_password) values "
						+ " ('"+validUser+"',UNHEX(SHA2('mayank_manulife',256)))";
		try{
			ConnectionManager.getConnection()
				.prepareStatement(insertSQL).executeUpdate();
		
			Assert.assertEquals(service.authenticate(getBase64Credentials(validUser, password)), 
					true);
		}catch(Exception e){
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testInvalidPassword(){
		String validUser="mayank_"+time;
		String password = "mayank";
		try{
			Assert.assertEquals(service.authenticate(getBase64Credentials(validUser, password)), 
					false);
		}catch(Exception e){
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testInvalidUser(){
		String invalidUser="mayank_"+(time+1);
		String password = "mayank_manulife";
		
		try{
			Assert.assertEquals(service.authenticate(getBase64Credentials(invalidUser, password)), 
					false);
		}catch(Exception e){
			Assert.fail(e.getMessage());
		}
	}
	
	private String getBase64Credentials(String user, String password){
		String base64Credentials = AuthenticationService.BASIC_KEY;
		String credentials = 
				new String(Base64.getEncoder().encode((user+":"+password).getBytes()),
						Charset.forName("UTF-8"));
		base64Credentials = base64Credentials + " " + credentials;
		
		return base64Credentials;
	}

}