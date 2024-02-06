package com.RESTService.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.RESTService.Models.AuthRequestModel;
import com.RESTService.Models.AuthResponseModel;
import com.RESTService.Models.GrantAdminResponseModel;
import com.RESTService.Service.AuthService;


@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

	@Autowired
	private AuthService authService;
	
	@PostMapping("authenticate")
	public ResponseEntity<AuthResponseModel> authenticateUser(@RequestBody AuthRequestModel authRequestModel)throws Exception {
		
			return ResponseEntity.ok(authService.authenticateUser(authRequestModel));
	
	}	
	@PutMapping("grantadmin")
	public ResponseEntity<GrantAdminResponseModel> grantAdmin(@RequestBody AuthRequestModel authRequestModel) {
	 
		return ResponseEntity.ok(authService.grantAdmin(authRequestModel));
		
	}	
}
