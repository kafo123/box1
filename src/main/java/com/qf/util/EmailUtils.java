package com.qf.util;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.Inet4Address;
import java.util.Properties;

/*
 * 1.首先需要获取发送邮件的Session对象
 * 	Session session = Session.getDefaultInstance(Properties prop)
 * 2.使用session对象 获取待发送的邮件信息
 *  MimeMessage mime = new MimeMessage(session)
 * 3.设置发件人 收件人 标题 邮件内容 附件 发送时间等等
 * 4.利用Transport 发送邮件
 * */
public class EmailUtils {
	public static void sendEmail(String title,String content,String receiveAccount){
		String  myAccount = "508650940@qq.com";
		//发件人 邮箱的 SMTP 服务器地址
		//String SMTPHost = "smtp.163.com";//163
		String SMTPHost = "smtp.qq.com";//qq
		//组成 properties
		Properties prop = new Properties();
		prop.setProperty("mail.transport.protocol", "smtp");//设置协议类型
		prop.setProperty("mail.smtp.host", SMTPHost);//定义发件人的邮箱服务器地址
		prop.setProperty("mail.smtp.auth", "true");//设置请求验证
		//qq邮箱需要SSL加密
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		//1.Session对象 创建会话 用于和邮箱服务器进行交互
		Session session = Session.getDefaultInstance(prop);
		//设置debug模式 可以查看详细发送信息 可略
		session.setDebug(true);
		
		//2.创建方法 用来组成一封完整的邮件
		//参数 session(参数配置), myAccount 发送方 , user.getEmail() 接收方
		//使用session对象 获取待发送的邮件信息
		MimeMessage message = new MimeMessage(session);
		//3.设置发件人 收件人 标题 邮件内容 附件 发送时间等等
		try {
			//3.1发件人 from
			message.setFrom(new InternetAddress(myAccount, "小米", "utf-8"));
			//3.2收件人 to 支持可以添加多个收件人 | 抄送 | 密送 如果想要发送给多个人 可以重复下面代码多次
			/*
			 * MimeMessage.RecipientType.TO 发送
			 * MimeMessage.RecipientType.CC 抄送
			 * MimeMessage.RecipientType.BCC 密送
			 * */
			message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveAccount,  "utf-8"));
			//3.3生成邮件主题
			message.setSubject(title,"utf-8");
			String ip = Inet4Address.getLocalHost().getHostAddress();//本机ip地址
			//www.xiaomi.com
			//String url = "http://"+ip+":8080/activate?c=");

			//message.setText(content);  普通文本
			//设置邮件正文 setContent 可以使用html标签
			message.setContent(content,"text/html;charset=utf-8");
			//设置邮件的发送时间 是立即发送
			//message.setSentDate(new Date());
			//保存设置
			message.saveChanges();
			//4.利用Transport 发送邮件
			Transport tran = session.getTransport();
			//连接服务器 确认发送方 是否授权
			tran.connect(myAccount,"yjuxexlsirurbhdc" );
			//发送邮件 将message 对象 传给 Transport 对象 将邮件发送出去
			//参数1 要发的内容 参数2 要给哪些人发
			//message.getAllRecipients() 获取到所有的收件人 | 抄送 | 密送
			tran.sendMessage(message, message.getAllRecipients());
			//关闭连接
			tran.close();
		} catch (Exception e) {
			// TODO Auto-generated catgitch blockgii
			e.printStackTrace();
		}
		
	}

	public  static  void  main(String[]args){
		 //sendEmail("哈喽","你好","184418201@qq.com");
		System.out.println(Base64Utils.decode("CSQZRKEKZGXZTHCLBXFM"));


	}


}
