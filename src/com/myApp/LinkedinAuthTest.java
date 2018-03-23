package com.myApp;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.SignatureType;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
public class LinkedinAuthTest {

	public static void main(String[] args) {

	    new LinkedinAuthTest().getOAuthrequest();

	}
	public void getOAuthrequest(){

		org.scribe.oauth.OAuthService servive=new ServiceBuilder()
	    .provider(LinkedInApi.class)
		//.provider(FitbitApi.SSL.class)
	    .apiKey("77nlpycj04rzn9")
	    .apiSecret("K87iE57azjh906Q9").callback("https://localhost/auth/linkedin/callback")
	    .debug()
	    .build();
	    Token requestToken=servive.getRequestToken();
	   // Verifier verifier= new Verifier(servive.getAuthorizationUrl(requestToken));
	    Verifier verifier= new Verifier("K87iE57azjh906Q9");
	    //Verifier verifier = new Verifier("verifier you got from the user");
	    Token accessToken=servive.getAccessToken(requestToken, verifier);
	    System.out.println(accessToken);
	}
	
}
