package com.RESTService.Service.Implimintations;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.RESTService.RoleRepository;
import com.RESTService.UserRepository;
import com.RESTService.Dto.UserDto;
import com.RESTService.Entity.RoleEntity;
import com.RESTService.Entity.UserEntity;
import com.RESTService.Models.UserDetailsRequestModel;
import com.RESTService.Security.JwtService;
import com.RESTService.Security.SecurityConstants;
import com.RESTService.Service.UserService;
import com.RESTService.Utils.Utils;

@Service
public class UserServiceImplimintation implements UserService {
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private Utils utils;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDto createUser(UserDto user) throws RuntimeException {
		System.out.println("----------------------<RegisterRequest>----------------------");
		// to check if user email is already exists
		UserEntity userInfo = userRepository.findByEmail(user.getEmail());

		if (userInfo != null)
			throw new RuntimeException("User Already Exists . . . ");

		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);

		// to assign generated user id to user entity
		String userId = utils.generateUserId(30);
		userEntity.setUserId(userId);

		//dates of registration and password expiration 
		LocalDate exp = LocalDate.now();
		long epoch = System.currentTimeMillis()/1000;
		userEntity.setRegistrationDate(epoch);
		userEntity.setExpirationDate(exp.plusWeeks(3));

		// to generate encrypted password and set it to user encrypted Password field
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		RoleEntity userRole = roleRepository.findById(SecurityConstants.USER_ROLE);
		Set<RoleEntity> Role = new HashSet<>();
		Role.add(userRole);
		userEntity.setRole(Role);
		
		
		userRepository.save(userEntity);

		UserDetails userDetails = User.withUsername(userEntity.getEmail()).password(userEntity.getEncryptedPassword())
				.roles(userRole.getRoleName()).build();

		var jwtToken = jwtService.generateToken(userDetails);

		UserDto returnValue = new UserDto();
		returnValue.setToken(jwtToken);
		BeanUtils.copyProperties(userEntity, returnValue);

		System.out.println("New user Name :" + returnValue.getFirstName());
		System.out.println("New user Email :" + returnValue.getEmail());
		System.out.println("New user Authorization :" + returnValue.getEncryptedPassword());
		System.out.println("New user EmailVerificationToken :" + returnValue.getEmailVerificationToken());
		System.out.println("New user Token : " + returnValue.getToken());
		System.out.println("New user Role : " + userDetails.getAuthorities());
		System.out.println("Valid Until : " + userEntity.getExpirationDate());

		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("----------------------------------------------------------|");
		System.out.println(">REQUEST-AT-" + this.getClass().getSimpleName()
				+ ": BY");

		UserEntity user = userRepository.findByEmail(username);
		if(user==null) {
			System.out.println(">USER NOT FOUND ");
			}

		System.out.println("Valid Account : " + user.isAccountNonExpired());
		System.out.println("Expiration date : " + user.getExpirationDate());
		System.out.println("Username : " + user.getFirstName());
		System.out.println("Email : " + user.getEmail());
		System.out.println("Roles : " + user.getAuthorities());

		return new User(user.getEmail(), user.getEncryptedPassword(), true, user.isAccountNonExpired(), true, true,
				user.getAuthorities());
	}

	@Override
	public UserDto getUserInfo(UserDetailsRequestModel userDetails) throws UsernameNotFoundException {
		System.out.println(">REQUEST-AT-" + this.getClass().getSimpleName() + ": GET_USER");
		System.out.println(">GET_USER "+userDetails.getEmail()+": "+System.currentTimeMillis());
		
		UserDto Response = new UserDto();
		
		UserEntity userInfo = userRepository.findByEmail(userDetails.getEmail());

		if (userInfo == null) {
			Response.setEmail("Not Found");
			System.out.println(">USER: "+ userDetails.getEmail()+" -> NOT FOUND");
			return Response;
		}
		
		// protect master user//
		Set<RoleEntity> role = userInfo.getRole();
		java.util.Iterator<RoleEntity> i = role.iterator();
		while (i.hasNext()) {
			if (i.next().getRoleName().equals("MASTER")) {
				return Response;
			}

		}
		BeanUtils.copyProperties(userInfo, Response);
		System.out.println("ID : " + Response.getId());
		System.out.println("FirstName : " + Response.getFirstName());
		System.out.println("LastName : " + Response.getLastName());
		System.out.println("Email : " + Response.getEmail());
		System.out.println("RegistrationDate : " + userInfo.getRegistrationDate());
		System.out.println("ExpirationDate : " + userInfo.getExpirationDate());
		System.out.println("Roles : " + userInfo.getAuthorities());
		System.out.println("Valid Account : " + userInfo.isAccountNonExpired());
		return Response;
	}
}
