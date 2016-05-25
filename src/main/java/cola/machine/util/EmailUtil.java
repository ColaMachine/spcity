package cola.machine.util;

import cola.machine.util.mail.MailSenderInfo;
import cola.machine.util.mail.SimpleMailSender;

import javax.mail.MessagingException;

/**
 * Created by dozen.zhang on 2016/5/13.
 */
public class EmailUtil {
    public static void send(String email,String content) throws MessagingException {
        if(StringUtil.isNotEmpty(email)) {

            // 发送激活邮件
            MailSenderInfo mailInfo = new MailSenderInfo();
            mailInfo.setMailServerHost("smtp.163.com");
            mailInfo.setMailServerPort("25");
            mailInfo.setValidate(true);
            mailInfo.setUserName("likegadfly");
            mailInfo.setPassword("wangyi216568");// 您的邮箱密码
            mailInfo.setFromAddress("likegadfly@163.com");
            mailInfo.setToAddress(email);
            mailInfo.setSubject("帐号激活");
            //mailInfo.setContent("请点击下面的链接进行激活</br><a href=''>http://127.0.0.1:8080/calendar/active.htm?activeid="
            //	+ active.getActiveid() + "</a>");
            mailInfo.setContent(content);
            // 这个类主要来发送邮件
            SimpleMailSender sms = new SimpleMailSender();
            // sms.sendTextMail(mailInfo);//发送文体格式
            try {
                sms.sendHtmlMail(mailInfo);// 发送html格式
            } catch (MessagingException e) {
                e.printStackTrace();
                throw e;
            }
        }

    }
}
