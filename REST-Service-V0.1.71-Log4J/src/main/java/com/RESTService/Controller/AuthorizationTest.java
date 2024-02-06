package com.RESTService.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("test")
public class AuthorizationTest {
	
		@GetMapping("admin")
		public String getAdmin() {
			return ("Successfully Accessed [ADMIN] URI.");
		}
		
		@GetMapping("master")
		public String getMaster() {
			return ("Successfully Accessed [MASTER] URI.");
		}
		
		@GetMapping("user")
		public String gtUser() {
			return ("Successfully Accessed [USER] URI.");
		}
	
}
