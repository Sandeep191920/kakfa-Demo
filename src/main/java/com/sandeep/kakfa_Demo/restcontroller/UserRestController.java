package com.sandeep.kakfa_Demo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sandeep.kakfa_Demo.entity.Userentity;
import com.sandeep.kakfa_Demo.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/users/v1")
public class UserRestController {
	
	@Autowired
	public UserService userservice;
	
	@PostMapping("/create")
	public ResponseEntity<Long> createUser(@Valid @RequestBody Userentity user){
		
		log.info("UserRestController::createUser->invoked");
		
		Long saveuser = userservice.saveuser(user);
		
		log.info("UserRestController::createUser->Received Response");
		
		return ResponseEntity.status(HttpStatus.CREATED).body(saveuser);
		
	}

}
