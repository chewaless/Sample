package com.myApp;

public class OAuthCookie {
 private String Oauth_token;
 private String Oauth_token_secret;
 private String oauth_parameters_absent;
 private String oauth_problem;
 
 
public String getOauth_parameters_absent() {
	return oauth_parameters_absent;
}
public void setOauth_parameters_absent(String oauth_parameters_absent) {
	this.oauth_parameters_absent = oauth_parameters_absent;
}
public String getOauth_problem() {
	return oauth_problem;
}
public void setOauth_problem(String oauth_problem) {
	this.oauth_problem = oauth_problem;
}
public String getOauth_token() {
	return Oauth_token;
}
public void setOauth_token(String oauth_token) {
	Oauth_token = oauth_token;
}
public String getOauth_token_secret() {
	return Oauth_token_secret;
}
public void setOauth_token_secret(String oauth_token_secret) {
	Oauth_token_secret = oauth_token_secret;
}
 
 
}
