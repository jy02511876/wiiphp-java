package com.util.mail;

import java.util.Properties;

public class MailSenderInfo {
	//发送邮件的服务器IP
	private String mailServerHost;
	//服务器的端口
	private String mailServerPort = "25";
	//发件人
	private String fromAddress;
	//收件人
	private String toAddress;
	//发件人邮箱的用户名
	private String username;
	//发件人邮箱的密码
	private String password;
	//是否需要身份验证
	private boolean validate = false;
	//邮件主题
	private String subject;
	//邮件内容
	private String content;
	//邮件的附件名
	private String[] attachFileNames;
	
	
	public Properties getProperties(){
		Properties p = new Properties();
		p.put("mail.smtp.host", this.mailServerHost);
		p.put("mail.smtp.port", this.mailServerPort);
		p.put("mail.smtp.auth", validate ? "true" : "false");
		return p;
	}
	
	
	public String getMailServerHost(){
		return mailServerHost;
	}
	
	
	public void setMailServerHost(String mailServerHost){
		this.mailServerHost = mailServerHost;
	}
	
	
	public String getMailServerPort(){
		return mailServerPort;
	}
	
	
	public void setMailServerPort(String mailServerPort){
		this.mailServerPort = mailServerPort;
	}
	
	
	public boolean isValidate(){
		return validate;
	}
	
	
	public void setValidate(boolean validate){
		this.validate = validate;
	}
	
	
	public String[] getAttachFileNames(){
		return attachFileNames;
	}
	
	
	public void setAttachFileNames(String[] attachFileNames){
		this.attachFileNames = attachFileNames;
	}
	
	
	public String getFromAddress(){
		return fromAddress;
	}
	
	
	public void setFromAddress(String fromAddress){
		this.fromAddress = fromAddress;
	}
	
	
	public String getToAddress(){
		return toAddress;
	}
	
	
	public void setToAddress(String toAddress){
		this.toAddress = toAddress;
	}
	
	
	public String getUsername(){
		return username;
	}
	
	
	public void setUsername(String username){
		this.username = username;
	}
	
	
	public String getPassword(){
		return password;
	}
	
	
	public void setPassword(String password){
		this.password = password;
	}
	
	
	public String getSubject(){
		return subject;
	}
	
	
	public void setSubject(String subject){
		this.subject = subject;
	}
	
	
	public String getContent(){
		return content;
	}
	
	
	public void setContent(String content){
		this.content = content;
	}
}
