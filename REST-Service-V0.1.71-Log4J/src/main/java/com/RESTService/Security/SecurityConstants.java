package com.RESTService.Security;
//this class is intended to make security configuration configurable
public class SecurityConstants {
	public static final long EXPIRATION_TIME=864000000;
	public static final String TOKEN_PREFIX="Bearer ";
	public static final String HEADER_STRING="Authorization";
	public static final String SIGN_UP_URL="/api/v1/users";
	public static final String Authenticate_URL="/api/v1/auth/authenticate";
	public static final String GRANT_ADMIN_URL="/api/v1/auth/grantadmin";
	public static final String TOKEN_SECRET="j98tt9122i4s8w10";
	public static final int USER_ROLE=3;
	public static final int ADMIN_ROLE=2;
}
