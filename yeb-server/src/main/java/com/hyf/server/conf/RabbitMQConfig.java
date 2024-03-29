package com.hyf.server.conf;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hyf.server.pojo.MailConstants;
import com.hyf.server.pojo.MailLog;
import com.hyf.server.service.IMailLogService;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author：洪yf
 * @date：2022/4/6
 * RabbitMQ配置
 */
@Configuration
public class RabbitMQConfig {
    public static final org.slf4j.Logger Logger = LoggerFactory.getLogger(RabbitMQConfig.class);
    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;
    @Autowired
    @Qualifier(value = "IMailLogService")
    private IMailLogService mailLogService;
    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        /**
         * 消息确认回调,确认消息是否到达broker
         * data:消息唯一标识
         * ack：确认结果
         * cause：失败原因
         *
         */
        rabbitTemplate.setConfirmCallback((data,ack,cause)->{
            String msgId = data.getId();
            if (ack){
                //消息确认成功
                Logger.info("{}=====>消息发送成功",msgId);
                //更新数据库中记录
                mailLogService.update(new UpdateWrapper<MailLog>
                        ().set("status",1).eq("msgId",msgId));
            }else {
                Logger.info("{}=====>消息发送失败",msgId);
            }
        });
        /**
         * 消息失败回调，比如router不到queue时回调
         * msg:消息主题
         * repCode:响应码
         * repText:响应描述
         * exchange:交换机
         * routingKey:路由键
         */

        rabbitTemplate.setReturnCallback((msg,repCode,repText,exchange,routingKey)->{
            Logger.info("{}=====>消息发送到queue时失败",msg.getBody());
        });
        return rabbitTemplate;
    }

    @Bean
    public Queue queue(){
        return new Queue(MailConstants.MAIL_QUEUE_NAME,true);
    }
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(MailConstants.MAIL_EXCHANGE_NAME);
    }
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(directExchange()).with(MailConstants.MAIL_ROUTING_KEY_NAME);
    }
}