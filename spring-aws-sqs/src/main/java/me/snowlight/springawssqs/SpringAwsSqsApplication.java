package me.snowlight.springawssqs;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.awspring.cloud.messaging.core.TopicMessageChannel;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SpringBootApplication
public class SpringAwsSqsApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringAwsSqsApplication.class, args);
	}

	@Bean
	public ApplicationRunner applicationRunner(
					AmazonSQSAsync amazonSQSAsync,
					@Value("${cloud.aws.sqs.queue-name}") String queueName) {
		return (args) -> {
			String payload = "Hello, World";
			Map<String, Object> headers = new HashMap<>();
			headers.put(TopicMessageChannel.MESSAGE_GROUP_ID_HEADER, "first-id");
			var  queueMessagingTemplate = new QueueMessagingTemplate(amazonSQSAsync);
			queueMessagingTemplate.convertAndSend(queueName, payload, headers);
		};
	}

	@SqsListener(
			value = "${cloud.aws.sqs.queue-name}",
			deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	public void receive(String payload) {
		System.out.println(payload);
	}
}
