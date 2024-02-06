package com.RESTService.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.RESTService.Dto.UserDto;
import com.RESTService.Models.UserDetailsRequestModel;


public interface UserService extends UserDetailsService {
	UserDto createUser(UserDto user);
	UserDto getUserInfo(UserDetailsRequestModel userDetails);
}
