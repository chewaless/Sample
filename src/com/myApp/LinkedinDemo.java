package com.myApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

public class LinkedinDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String linkedinKey = "77nlpycj04rzn9";    //Your LinkedIn key
		String linkedinSecret = "K87iE57azjh906Q9";//Your LinkedIn Secret
		String callbackURL = "https://localhost/auth/linkedin/callback";
		OAuthConsumer consumer = new DefaultOAuthConsumer(linkedinKey,linkedinSecret);
		  
		OAuthProvider provider = new DefaultOAuthProvider("https://api.linkedin.com/uas/oauth/requestToken",
		                 "https://api.linkedin.com/uas/oauth/accessToken",
		                 "https://api.linkedin.com/uas/oauth/authorize");
		  
		System.out.println("Fetching request token from LinkedIn...");  
		String authUrl;
		try {
		authUrl = provider.retrieveRequestToken(consumer, callbackURL);
		System.out.println("Check the below link and grant this app authorization -You will get Pin Number.Copy it\n" + authUrl                  );
		System.out.println("Enter the PIN code and hit ENTER when you're done:");
		} catch (OAuthMessageSignerException e1) {
		 e1.printStackTrace();
		} catch (OAuthNotAuthorizedException e1) {
		 e1.printStackTrace();
		} catch (OAuthExpectationFailedException e1) {
		 e1.printStackTrace();
		} catch (OAuthCommunicationException e1) {
		 e1.printStackTrace();
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String pin;
		try {
		pin = br.readLine();   
		System.out.println("Fetching access token from LinkedIn...");
		provider.retrieveAccessToken(consumer, pin);
		System.out.println("Access token: " + consumer.getToken());
		System.out.println("Token secret: " + consumer.getTokenSecret());
		URL url = new URL("http://api.linkedin.com/v1/people/~:(id,first-name,last-name,picture-url,headline)");
		           
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		           consumer.sign(request);
		           request.connect();
		System.out.println("Sending request to LinkedIn...");
		String responseBody = convertStreamToString(request.getInputStream());
		System.out.println("Response: " + request.getResponseCode() + " "
		                   + request.getResponseMessage() + "\n" + responseBody);

		} catch (OAuthMessageSignerException e) {   
		  e.printStackTrace();
		} catch (OAuthNotAuthorizedException e) {  
		  e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
		e.printStackTrace();
		} catch (OAuthCommunicationException e) {   
		  e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
		}
		 public static String convertStreamToString(InputStream is) {
		 
		 BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		 StringBuilder sb = new StringBuilder();
		 String line = null;
		  try {
		while ((line = reader.readLine()) != null) {
		                 sb.append(line + "\n");
		       }
		  } catch (IOException e) {
		             e.printStackTrace();
		  } finally {
		  try {
		     is.close();
		 } catch (IOException e) {
		     e.printStackTrace();
		 }
		}
		      return sb.toString();
		}
		
}
