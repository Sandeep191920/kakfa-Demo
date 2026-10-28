package com.sandeep.kakfa_Demo.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.sandeep.kakfa_Demo.UserRepo;
import com.sandeep.kakfa_Demo.entity.Userentity;
import com.sandeep.kakfa_Demo.event.UsercreatedEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceimpl implements UserService {

	private UserRepo repo;

	private KafkaTemplate<String, Object> kafkatemplate;

	public UserServiceimpl(UserRepo repo, KafkaTemplate<String, Object> kafkatemplate) {
		super();
		this.repo = repo;
		this.kafkatemplate = kafkatemplate;
	}

	@Override
	public Long saveuser(Userentity user) {
		
		log.info("UserServiceimpl::saveuser->Invoked");
		
		String msgid = UUID.randomUUID().toString();
		
		Userentity saveduser = repo.save(user);
		
		log.info("UserServiceimpl::saveuser -> User stored in DB");
		
		UsercreatedEvent usercreatedEvent = new UsercreatedEvent(
				saveduser.getId(),
				saveduser.getFirstname(),
				saveduser.getLastname(),
				saveduser.getEmail());
		
		String userid = String.valueOf(usercreatedEvent.getUserid());
		
		ProducerRecord<String, Object> producerrecord = new ProducerRecord<>("user-created-events-topic-1",userid,usercreatedEvent);
		
		producerrecord.headers().add("msgid",msgid.getBytes());
		
		CompletableFuture<SendResult<String, Object>> future = kafkatemplate.send(producerrecord);
		
		future.whenComplete((result,e)->{
			if(e!=null) {
				log.info("Error while sending user created events",e);
			}
			else {
				log.info("event-meta-Data",result.getRecordMetadata());
				log.info("event-meta-Data",result.getRecordMetadata().partition());
				log.info("event-meta-Data",result.getRecordMetadata().topic());
				log.info("event-meta-Data",result.getRecordMetadata().offset());
			}
		});

	return usercreatedEvent.getUserid();
}

}
