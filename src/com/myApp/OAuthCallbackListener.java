package com.myApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.commons.lang.StringUtils;

public class OAuthCallbackListener {

	public static void main(String[] args) {

		final String TOKEN_ENDPOINT ="https://graph.facebook.com/oauth/access_token";
        final String GRANT_TYPE = "authorization_code";
        final String REDIRECT_URI = "https://www.zensar.com/";
        final String CLIENT_ID = "1549039661879593";
        final String CLIENT_SECRET = "eb059d13477ab884e2a454b4370fd576";
       String  authorizationCode="d430c4338c604811d4f8ba44159c2912";
        
      /* String newUrl = "https://graph.facebook.com/oauth/access_token?client_id="
				+ CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&client_secret=" 
				+ CLIENT_SECRET + "&code=" + authorizationCode;
       */
       
	try {
		URL url = new URL("https://graph.facebook.com/oauth/access_token?client_id="
			+ CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&client_secret=" 
			+ CLIENT_SECRET + "&code=" + authorizationCode);
		
		 HttpURLConnection request = (HttpURLConnection) url.openConnection();
		 request.connect();
		// request.getInputStream();
		 System.out.println("request:"+request);
		 System.out.println("request.getInputStream():"+request.getResponseCode());
		 
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
       
		/*HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpGet httpget = new HttpGet(newUrl);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = httpclient.execute(httpget, responseHandler);
			String token = StringUtils.removeEnd
					(StringUtils.removeStart(responseBody, "access_token="), "&expires=5180795");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}*/

    } 

}
