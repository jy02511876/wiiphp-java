package com.util.mail;

import javax.mail.*;


public class MyAuthenticator extends Authenticator {
	String username = null;
	String password = null;
	
	public MyAuthenticator(){
	}
	
	public MyAuthenticator(String username,String password){
		this.username = username;
		this.password = password;
	}
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		// TODO Auto-generated method stub
		return new PasswordAuthentication(username,password);
	}
}
