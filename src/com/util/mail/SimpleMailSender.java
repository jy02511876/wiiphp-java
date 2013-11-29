package com.util.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SimpleMailSender {
	public boolean sendTextMail(MailSenderInfo mailInfo){
		//判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		//如果需要身份认证，则创建一个密码验证器
		if(mailInfo.isValidate())
			authenticator = new MyAuthenticator(mailInfo.getUsername(),mailInfo.getPassword());
		
		//根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro,authenticator);
		try {
			//根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			//创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			//设置邮件消息的发送者
			mailMessage.setFrom(from);
			//创建邮件发送者地址
			Address to = new InternetAddress(mailInfo.getToAddress());
			//设置邮件消息的发送者
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			//创建邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			//设置邮件的发送时间
			mailMessage.setSentDate(new Date());
			//设置邮件的内容
			mailMessage.setText(mailInfo.getContent());
			//发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	public static boolean sentHtmlMail(MailSenderInfo mailInfo){
		//判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		//如果需要身份认证，则创建一个密码验证器
		if(mailInfo.isValidate())
			authenticator = new MyAuthenticator(mailInfo.getUsername(),mailInfo.getPassword());
				
		//根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro,authenticator);
		try {
			//根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			//创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			//设置邮件消息的发送者
			mailMessage.setFrom(from);
			//创建邮件发送者地址
			Address to = new InternetAddress(mailInfo.getToAddress());
			//设置邮件消息的发送者
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			//创建邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			//设置邮件的发送时间
			mailMessage.setSentDate(new Date());
			//MimeMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			//创建一个包含html内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			//设置HTML内容
			html.setContent(mailInfo.getContent(),"text/html;chartset=utf-8");
			mainPart.addBodyPart(html);
			//将MimeMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart);
			//发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	public static void main(String[] args){
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.qq.com");
		mailInfo.setValidate(true);
		mailInfo.setUsername("42397814@qq.com");
		mailInfo.setPassword("kin_3788-ZHOU");
		mailInfo.setFromAddress("42397814@qq.com");
		mailInfo.setToAddress("mf02511876@163.com");
		mailInfo.setSubject("邮件标题");
		mailInfo.setContent("<b>邮件内容</b>");
		
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);
		//SimpleMailSender.sentHtmlMail(mailInfo);
		System.out.println("finished");
	}
}
