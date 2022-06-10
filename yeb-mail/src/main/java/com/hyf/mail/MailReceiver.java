package com.hyf.mail;

import com.hyf.server.pojo.Employee;
import com.hyf.server.pojo.MailConstants;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;

/**
 * @author：洪yf
 * @date：2022/4/6
 */
@Component
public class MailReceiver {
    private static final Logger logger= LoggerFactory.getLogger(MailReceiver.class);

    @Autowired
    private JavaMailSender javaMailSender;
    //邮件配置类
    @Autowired
    private MailProperties mailProperties;
    //模板引擎
    @Autowired
    private TemplateEngine templateEngine;
    //redis
    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitListener(queues = MailConstants.MAIL_QUEUE_NAME)
    public void handler(Message message, Channel channel){
        Employee employee = (Employee) message.getPayload();
        MessageHeaders headers = message.getHeaders();
        //消息序号
        long tag = (long)headers.get(AmqpHeaders.DELIVERY_TAG);
        String msgId = (String)headers.get("spring_returned_message_correlation");
        HashOperations hashOperations = redisTemplate.opsForHash();
        try {
            if (hashOperations.entries("mail_log").containsKey(msgId)) {
                //redis中包含key，说明消息已经被消费
                logger.info("消息已经被消费=====>{}", msgId);
                /**
                 * 手动确认消息
                 * tag:消息序号
                 * multiple:是否多条
                 */
                channel.basicAck(tag, false);
                return;
        }
        //创建信息
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        //发件人
        helper.setFrom(mailProperties.getUsername());
        //收件人
        helper.setTo(employee.getEmail());
        //主题
        helper.setSubject("入职欢迎邮件");
        //发送日期
        helper.setSentDate(new Date());
        //邮件内容
        Context context= new Context();
        context.setVariable("name",employee.getName());
        context.setVariable("posName",employee.getPosition().getName());
        context.setVariable("joblevelName",employee.getJoblevel().getName());
        context.setVariable("departmentName",employee.getDepartment().getName());
        //templateEngine会从context拿相应的数据。（从context拿到相应内容，放到mail）
        String mail = templateEngine.process("mail", context);
        helper.setText(mail,true);
        //发送邮件
        javaMailSender.send(msg);
        logger.info("邮件发送成功");
        hashOperations.put("mail_log",msgId,"OK");
        //手动确认消息
        channel.basicAck(tag,false);
        } catch (Exception e) {
            try {
                /**
                 * 手动确认消息
                 * tag:消息序号
                 * multiple:是否多条
                 * requeue:是否回退到队列
                 */
                channel.basicNack(tag,false,true);
            }catch (IOException exception){
                logger.error("消息确认失败=====>{}", exception.getMessage());
            }
            logger.error("邮件发送失败=====>{}", e.getMessage());
        }
    }
}
