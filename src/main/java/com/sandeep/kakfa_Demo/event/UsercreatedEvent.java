package com.sandeep.kakfa_Demo.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsercreatedEvent {
	
	private Long Userid;
	
	private String firstname;
	
	private String lastname;
	
	private String email;
	 

}
