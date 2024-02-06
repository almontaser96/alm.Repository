package com.RESTService.Service;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.RESTService.RoleRepository;
import com.RESTService.UserRepository;
import com.RESTService.Entity.RoleEntity;
import com.RESTService.Entity.UserEntity;
import com.RESTService.Models.AuthRequestModel;
import com.RESTService.Models.AuthResponseModel;
import com.RESTService.Models.GrantAdminResponseModel;
import com.RESTService.Security.JwtService;
import com.RESTService.Security.SecurityConstants;


@Service
public class AuthService {
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
    AuthenticationManager authenticationManager;
	final Logger logger= LogManager.getLogger(AuthService.class);
	public AuthResponseModel authenticateUser(AuthRequestModel authRequestModel) throws Exception{
		System.out.println("----------------------------------------------------------|");
		System.out.println(">AUTHENTICATE "+authRequestModel.getEmail()+": "+System.currentTimeMillis());
		logger.info("Authenticate Request -> Username: "+authRequestModel.getEmail());
		logger.info(authRequestModel);
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequestModel.getEmail(), authRequestModel.getPassword()));
		logger.info("Authenticate Response -> PASSED");
		System.out.println("-----------------< AUTHENTICATION PASSED >----------------|");
		
		UserEntity user = userRepository.findByEmail(authRequestModel.getEmail());
	
		/*
		Set<RoleEntity> role = user.getRole();
		List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
		java.util.Iterator<RoleEntity> i=role.iterator();
		while(i.hasNext()) {
		roles.add(new SimpleGrantedAuthority("ROLE_"+i.next().getRoleName()));			
		 }
		*/
		
		UserDetails userDetails = new User(user.getEmail(), user.getEncryptedPassword(), true, user.isAccountNonExpired(), true, true,
				user.getAuthorities());
		String jwt = jwtService.generateToken(userDetails);
		AuthResponseModel authResponseModel = new AuthResponseModel();
		authResponseModel.setToken(jwt);
		return authResponseModel;
	}

	public GrantAdminResponseModel grantAdmin(AuthRequestModel authRequestModel) {
		System.out.println(">GrantAdmin "+authRequestModel.getEmail()+": "+System.currentTimeMillis());
		System.out.println(">REQUEST-AT-" + this.getClass().getSimpleName() + ": GRANT_ADMIN");
		
		GrantAdminResponseModel Response = new GrantAdminResponseModel();
		
		UserEntity user = userRepository.findByEmail(authRequestModel.getEmail());
		if (user==null) {
			Response.setUsername(authRequestModel.getEmail());
			Response.setMessage("Failed to grant non existing user admin role.");
			System.out.println(">USER: "+ authRequestModel.getEmail()+" -> NOT FOUND");
			return Response;
		};
		
		RoleEntity adminRole = roleRepository.findById(SecurityConstants.ADMIN_ROLE);
		
		Set<RoleEntity> roles = user.getRole();
		roles.add(adminRole);
		
		user.setRole(roles);
		userRepository.save(user);
		
		
		Response.setUsername(user.getEmail());
		Response.setMessage("Admin Role Granted Successfully.");
		
		System.out.println("Valid Account : " + user.isAccountNonExpired());
		System.out.println("Registratin Date :" + user.getRegistrationDate());
		System.out.println("Granted User Name :" + user.getFirstName());
		System.out.println("Granted User Email :" + user.getEmail());
		System.out.println("Granted User Authorization :" + user.getEncryptedPassword());
		System.out.println("Granted User Roles : " + user.getAuthorities());
		System.out.println("Expiration Date : " + user.getExpirationDate());
		System.out.println("Response : Success");
		return Response;
	}

}
