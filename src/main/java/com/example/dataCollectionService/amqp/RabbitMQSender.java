package com.example.dataCollectionService.amqp;

import java.nio.charset.StandardCharsets;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.dataCollectionService.controller.Employee;
import com.example.dataCollectionService.crypto.utils.ProtoUtils;

@Service
public class RabbitMQSender {
	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Value("${employee.service.rabbitmq.exchange}")
	private String exchange;

	@Value("${employee.service.rabbitmq.routingkey}")
	private String routingkey;

	public void send(Employee employee, String fileType, String requestType) throws Exception {
		Message message = prepareEcryptedMessage(employee, fileType, requestType);
		rabbitTemplate.convertAndSend(exchange, routingkey, message);
	}

	private Message prepareEcryptedMessage(Employee employee, String fileType, String requestType) throws Exception {
		MessageProperties mp = new MessageProperties();
		mp.setHeader("content_type", "application/x-protobuf");
		mp.setHeader("__TypeId__", "java.lang.String");
		mp.setHeader("fileType", fileType);
		mp.setHeader("RequestType", requestType);

		return new Message(ProtoUtils.encryptProto(employee).getBytes(StandardCharsets.UTF_8), mp);
	}
}
