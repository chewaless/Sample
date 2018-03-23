package com.myApp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;
import facebook4j.conf.Configuration;
import facebook4j.conf.ConfigurationBuilder;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class FacebookIntegration {
	
	public static void main(String[] args) throws FacebookException {
		// Create conf builder and set authorization and access keys
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setDebugEnabled(true);
		configurationBuilder.setOAuthAppId("1549039661879593");
		configurationBuilder.setOAuthAppSecret("eb059d13477ab884e2a454b4370fd576");
		configurationBuilder.setOAuthAccessToken("EAAWA1ZBbKESkBAKte2S0RtVsJkF71zQChEWQH7lvUhLX8oyN8GaGo0DbBxaec7HiLhD8ZBj3FIS0FE4wzSsDQjdpQZCkZBYiUHQZBPY3ZCPEyM217KIiBwr4QZBp18V8LZBCf7V3lk9KgKFnnJLKr3YRJ6QLHSTb1HSowGhCiGFAbAZDZD");
		//configurationBuilder.setOAuthPermissions("email, publish_stream, id, name, first_name, last_name, read_stream , generic");
		configurationBuilder.setUseSSL(true);
		configurationBuilder.setJSONStoreEnabled(true);

		// Create configuration and get Facebook instance
		Configuration configuration = configurationBuilder.build();
		FacebookFactory ff = new FacebookFactory(configuration);
		Facebook Facebook = ff.getInstance();

		AccessToken accessToken=Facebook.getOAuthAccessToken();
		System.out.println("access_token is "+accessToken.getToken());
		System.out.println("expiry is "+accessToken.getExpires());
		/*ResponseList<Post> results1 = Facebook.getFeed("https://www.facebook.com/Zensar/posts/10156263168564138?access_token="+accessToken.getToken());
		for (Post post : results1) {
			System.out.println(post.getMessage());
		}*/
		
		/*FacebookClient facebookClient = new DefaultFacebookClient(accessToken.getToken());
		String str = facebookClient.fetchObject("cocacola", String.class);
		System.out.println("str is "+str);*/
		
		String str =getFacebookPostes(Facebook);
		System.out.println("str is "+str);
	}

	// This method is used to get Facebook posts based on the search string set
	// above
	public static String getFacebookPostes(Facebook Facebook, String searchPost)
			throws FacebookException {
		String searchResult = "Item : " + searchPost + "\n";
		StringBuffer searchMessage = new StringBuffer();
		System.out.println("********************"+Facebook.getPosts(searchPost));
		ResponseList<Post> results = Facebook.getPosts(searchPost);
		for (Post post : results) {
			System.out.println(post.getMessage());
			searchMessage.append(post.getMessage() + "\n");
			for (int j = 0; j < post.getComments().size(); j++) {
				searchMessage.append(post.getComments().get(j).getFrom()
						.getName()
						+ ", ");
				searchMessage.append(post.getComments().get(j).getMessage()
						+ ", ");
				searchMessage.append(post.getComments().get(j).getCreatedTime()
						+ ", ");
				searchMessage.append(post.getComments().get(j).getLikeCount()
						+ "\n");
			}
		}
		String feedString = getFacebookFeed(Facebook, searchPost);
		searchResult = searchResult + searchMessage.toString();
		searchResult = searchResult + feedString;
		return searchResult;
	}

	// This method is used to get Facebook feeds based on the search string set
	// above
	public static String getFacebookFeed(Facebook Facebook, String searchPost)
			throws FacebookException {
		String searchResult = "";
		StringBuffer searchMessage = new StringBuffer();
		ResponseList<Post> results = Facebook.getFeed(searchPost);
		for (Post post : results) {
			System.out.println(post.getMessage());
			searchMessage.append(post.getFrom().getName() + ", ");
			searchMessage.append(post.getMessage() + ", ");
			searchMessage.append(post.getCreatedTime() + "\n");
		}
		searchResult = searchResult + searchMessage.toString();
		return searchResult;
	}
	public static String getFacebookPostes(Facebook facebook) throws FacebookException { // Get posts for a particular search
		ResponseList<Post> results = facebook.getPosts("https://www.facebook.com/Zensar/posts/10156263183174138"); 
		return results.toString(); 
	}

	// This method is used to create JSON object from data string
	public static String stringToJson(String data) {
		JsonConfig cfg = new JsonConfig();
		try {
			JSONObject jsonObject = JSONObject.fromObject(data, cfg);
			System.out.println("JSON = " + jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "JSON Created";
	}
}