package com.myApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.WebServiceClient;

import org.apache.http.HttpResponse;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;

import oauth.signpost.*;
import oauth.signpost.basic.*;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import oauth.signpost.signature.AuthorizationHeaderSigningStrategy;
import oauth.signpost.signature.HmacSha1MessageSigner;

public class OAuthService {

	private final String requestTokenURL="https://api.linkedin.com/uas/oauth/requestToken";
	private final String accessTokenURL="https://api.linkedin.com/uas/oauth/accessToken";
	private final String authorizeURL="https://www.linkedin.com/oauth/v2/authorization";
	//private final String authorizeURL="https://api.linkedin.com/uas/oauth/authorize";
	//private final String authorizationDefaultURL="https://www.linkedin.com/oauth/v2/authorization";
	
	private String consumerKey = "77nlpycj04rzn9";
	private String consumerSecret = "K87iE57azjh906Q9";
	private String callbackURL = "https://localhost/auth/linkedin/callback";
	//private String callbackURL = "https://cmostage.zensar.com";
	
	//@Autowired
	private OAuthProvider oauthProvider;
	
	//@Autowired
	private OAuthConsumer oauthConsumer;
	
	private String oauthToken;
	private boolean hasAccessToken=false;
	
	public OAuthService(){
		oauthProvider= new DefaultOAuthProvider(requestTokenURL, accessTokenURL, authorizeURL);
		oauthProvider.setOAuth10a(true);
		oauthConsumer= new DefaultOAuthConsumer(consumerKey, consumerSecret); 
	}
	public OAuthService(String consumerKey, String consumerSecret)
	{
		this.setConsumerKey(consumerKey);
		this.setConsumerSecret(consumerSecret);
		this.callbackURL=OAuth.OUT_OF_BAND;
		oauthProvider= new DefaultOAuthProvider(requestTokenURL, accessTokenURL, authorizeURL);
		oauthProvider.setOAuth10a(true);
		oauthConsumer= new DefaultOAuthConsumer(consumerKey, consumerSecret); 
	}
	
	public OAuthService(String consumerKey, String consumerSecret, String callbackURL)
	{
		this.setConsumerKey(consumerKey);
		this.setConsumerSecret(consumerSecret);
		this.callbackURL=callbackURL;
		oauthProvider= new DefaultOAuthProvider(requestTokenURL, accessTokenURL, authorizeURL);
		oauthProvider.setOAuth10a(true);
		oauthConsumer= new DefaultOAuthConsumer(consumerKey, consumerSecret);
		oauthConsumer=getOAuthConsumer(oauthConsumer);
	}
	protected OAuthConsumer getOAuthConsumer(OAuthConsumer oauthConsumer) {
		DefaultOAuthConsumer consumer = new DefaultOAuthConsumer(oauthConsumer.getConsumerKey(), oauthConsumer.getConsumerSecret());
		consumer.setMessageSigner(new HmacSha1MessageSigner());
		consumer.setSigningStrategy(new AuthorizationHeaderSigningStrategy());
		return consumer;
	}
	public String getAuthorizationURL() 
	{
		String authorizationURL=null;
		
		try 
		{
			authorizationURL = oauthProvider.retrieveRequestToken(oauthConsumer, callbackURL);
			
			/*RestTemplate restTemplate = new RestTemplate();
		    HttpHeaders headers = new HttpHeaders();
		    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		    Map<String, String> params = new HashMap<String, String>();
		    params.put("response_type", "code");
		    params.put("client_id", "77nlpycj04rzn9");
		    params.put("redirect_uri", "https://www.zensar.com/company/token?step=authorized");
		    params.put("state", "987654321");
		    params.put("scope", "r_basicprofile");
		    ResponseEntity<String> result = restTemplate.exchange(authorizationDefaultURL, HttpMethod.GET, entity, String.class, params);
		     
		    System.out.println(result);*/
		    
			System.out.println("result:"+authorizationURL);
			/*HttpResponse tokenResponse = getWebServiceClient("www.linkedin.com")
	                .withPath("/oauth/v2/accessToken")
	                .request()
	                .contentType("application/x-www-form-urlencoded")
	                .body(getFormEncodedBodyFrom(createPostData(_config.getClientId(), _config.getClientSecret(),
	                        requestModel.getCode(), requestModel.getRequestUrl())))
	                .method("POST")
	                .response();
	        int statusCode = tokenResponse.statusCode();*/
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 

		int oauthTokenPosition=authorizationURL.lastIndexOf("oauth_token=")+"oauth_token=".length();
		System.out.println("oauthTokenPosition:"+oauthTokenPosition);
		setOauthToken(authorizationURL.substring(oauthTokenPosition));
		System.out.println("oauthToken:"+oauthToken);
        System.out.println("Token secret: " + oauthConsumer.getTokenSecret());
        
		System.out.println("********Get the profile in JSON********");
		try {
			
	   // URL url = new URL("https://www.linkedin.com/nhome/updates?topic=6381827841461354497?oauth2_access_token="+ oauthConsumer.getToken());
        URL url = new URL("https://www.linkedin.com/feed/update/urn:li:activity:6380019119005134848?oauth2_access_token="+ oauthToken);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
			oauthConsumer.sign(request);
			System.out.println("Sending request to LinkedIn...");
		       request.connect();
		       String responseBody = convertStreamToString(request.getInputStream());
		        System.out.println("Response: " + request.getResponseCode() + " "
		                + request.getResponseMessage() + "\n\n" + responseBody);
		} catch (OAuthMessageSignerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return authorizationURL;
	}
	 public static void main(String[] args)
	  {
		 OAuthService as=new OAuthService();
		 as.getAuthorizationURL();
			
	  }
	 public static String convertStreamToString(InputStream is) {
	       	        
	      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	      StringBuilder sb = new StringBuilder();
	        String line = null;
	       try {
	            while ((line = reader.readLine()) != null) {
	               sb.append(line + "\n");
	           }
	        } 
	catch (IOException e) {
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

	public void getAccessToken(String verficationCode)
	{
		try 
		{
			oauthProvider.retrieveAccessToken(oauthConsumer, verficationCode);
			hasAccessToken=true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void signRequest(HttpURLConnection request)
	{
		try 
		{
			oauthConsumer.sign(request);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public boolean isReady()
	{
		return hasAccessToken;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setOauthToken(String oauthToken) {
		this.oauthToken = oauthToken;
	}

	public String getOauthToken() {
		return oauthToken;
	}
}