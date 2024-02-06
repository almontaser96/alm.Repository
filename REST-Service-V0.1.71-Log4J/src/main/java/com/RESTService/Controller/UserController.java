package com.RESTService.Controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RESTService.Dto.UserDto;
import com.RESTService.Models.UserDetailsRequestModel;
import com.RESTService.Models.UserDetailsResponseModel;
import com.RESTService.Service.UserService;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping
	public ResponseEntity<UserDto> getUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
     	return ResponseEntity.ok(userService.getUserInfo(userDetails));

	}

	@PostMapping
	public UserDetailsResponseModel createUser(@RequestBody UserDetailsRequestModel userDetails){
		UserDetailsResponseModel returnValue = new UserDetailsResponseModel();
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);

			UserDto createdUserDto = userService.createUser(userDto);
			BeanUtils.copyProperties(createdUserDto, returnValue);
			return (returnValue);
}

	@DeleteMapping
	public String deleteUser() {
		return ("Delete Mapping of user conroller : UP");
	}

	@PutMapping
	public String editUser() {
		return ("Put Mapping of user conroller : UP");
	}
}
