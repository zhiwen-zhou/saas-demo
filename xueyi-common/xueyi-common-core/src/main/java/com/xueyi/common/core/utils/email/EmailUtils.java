package com.xueyi.common.core.utils.email;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class EmailUtils {

    public static synchronized JavaMailSenderImpl getJavaMailSender() {
        /**
         * spring.mail.host=smtp.qq.com spring.mail.username=用户名 //发送方的邮箱
         * spring.mail.password=密码 //对于qq邮箱而言 密码指的就是发送方的授权码
         * spring.mail.properties.mail.smtp.auth=true
         * spring.mail.properties.mail.smtp.starttls.enable=true
         * spring.mail.properties.mail.smtp.starttls.required=true
         * ————————————————
         * 版权声明：本文为CSDN博主「瑶山」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
         * 原文链接：https://blog.csdn.net/qq_44695727/article/details/89449313
         */
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.qq.com");
        javaMailSender.setPassword("lmrnrvejggnnbijc");//lmrnrvejggnnbijc
        javaMailSender.setUsername("448063211@qq.com");
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.starttls.required", "true");
        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }

    public static void main(String[] args) {
        JavaMailSenderImpl javaMailSender = getJavaMailSender();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("448063211@qq.com");
        message.setTo("chiman0123@163.com");
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容");
        javaMailSender.send(message);
    }

}
