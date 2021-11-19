package com.itstyle.seckill.queue.activemq;

import javax.jms.Destination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQSender {
	
	@Autowired
	private JmsMessagingTemplate jmsTemplate;

	public void sendChannelMess(Destination destination, final String message){
		jmsTemplate.convertAndSend(destination, message);
	}

}
