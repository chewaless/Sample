package com.myApp;

import java.io.IOException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LinkedInUserService {

	 public static String OAUTH2_ACCESS_TOKEN = "xoauth_oauth2_access_token";
	 public static String X_LI_FORMAT = "x-li-format";
	 private static String consumerKey = "77nlpycj04rzn9";
	 private static String consumerSecret = "K87iE57azjh906Q9";
	 private String callbackURL = "https://cmostage.zensar.com";
	 public static String ACCESS_TOKEN_ENDPOINT = "https://api.linkedin.com/uas/oauth/accessToken";
	 public static String PROFILE_URL = "http://api.linkedin.com/v1/people/~:(id,first-name,last-name,headline)";
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		org.scribe.oauth.OAuthService service = new ServiceBuilder()
                .apiKey(consumerKey)
                .apiSecret(consumerSecret)
                .provider(LinkedInApi.withScopes("r_basicprofile r_network r_emailaddress rw_company_admin"))
                .build();

        OAuthRequest oAuthRequest = new OAuthRequest(Verb.POST, ACCESS_TOKEN_ENDPOINT);

        oAuthRequest.addHeader(X_LI_FORMAT, "json");
       // oAuthRequest.addBodyParameter(OAUTH2_ACCESS_TOKEN, token.getToken());

        // Use an empty 1.0a access_token
        Token emptyToken = new Token("", "");
        
        service.signRequest(emptyToken, oAuthRequest);
        Response oAuthRequestResp = oAuthRequest.send();
        System.out.println("Raw oAuthRequestResp.getBody():" + oAuthRequestResp.getBody());
        OAuthCookie oAuthCookie = plainTextToOAuthCookie(oAuthRequestResp.getBody());
        System.out.println("Parsed OAuthCookie:" + oAuthCookie);
        //Profile fetch
        Token fetchRequestToken = new Token(oAuthCookie.getOauth_token(), oAuthCookie.getOauth_token_secret());
        //
        OAuthRequest anotherOauthRequest = new OAuthRequest(Verb.GET, PROFILE_URL);
        anotherOauthRequest.addHeader(X_LI_FORMAT, "json");//format as JSON
        service.signRequest(fetchRequestToken, anotherOauthRequest);
        
        Response response = anotherOauthRequest.send();
        System.out.println("FetchReqeustResp.getBody():" + response.getBody());
        //
       /* LiUserProfile liUserProfile = null;
        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        try {
            liUserProfile = mapper.readValue(response.getBody(), LiUserProfile.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Final mapped LiUserProfile:" + liUserProfile);*/

	}
	
	private byte[] shaSign(String baseString, String secret) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA1");
            mac.init(secretKey);
            return mac.doFinal(baseString.getBytes());
        } catch (Exception e) {
            throw new IllegalStateException("Error while generating the HMAC-SHA1 signature", e);
        }
      }
	
	private static OAuthCookie plainTextToOAuthCookie(String plainText) {
        //oauth_token=78--b367d0e9-a312-4fc8-814f-c20444c9f4bd&oauth_token_secret=b89bd23c-459f-4698-a6ba-5e76cd5d8d07&oauth_expires_in=4742507&oauth_authorization_expires_in=4742507
        String jsonStr = "{";
        String[] plainTexts = plainText.split("&");
        for (int i = 0; i < plainTexts.length; i++) {
            String[] textElements = plainTexts[i].split("=");
            if (i > 0) {
                jsonStr += ",'" + textElements[0] + "'";
            } else {
                jsonStr += "'" + textElements[0] + "'";
            }
            //
            jsonStr += ":";
            jsonStr += "'" + textElements[1] + "'";
        }
        jsonStr += "}";

        OAuthCookie oAuthCookie = null;
        ObjectMapper mapper = new ObjectMapper();
       // mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        try {
            oAuthCookie = mapper.readValue(jsonStr, OAuthCookie.class);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Translated oAuthCookie:" + oAuthCookie.toString());

        return oAuthCookie;
    }

}
