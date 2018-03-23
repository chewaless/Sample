package com.myApp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import facebook4j.Facebook; 
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.conf.Configuration;
import facebook4j.conf.ConfigurationBuilder; 
public class FacebookImpl { 
	public static void main(String[] args) throws FacebookException { 
	// Make the configuration builder 
	ConfigurationBuilder confBuilder = new ConfigurationBuilder(); 
	confBuilder.setDebugEnabled(true); // Set application id, secret key and access token 
	confBuilder.setOAuthAppId("1549039661879593"); 
	confBuilder.setOAuthAppSecret("eb059d13477ab884e2a454b4370fd576"); 
	confBuilder.setOAuthAccessToken("1549039661879593|2z4wytbh4sRIssPIuungxfi6GqE"); // Set permission 
	confBuilder.setOAuthPermissions("email,publish_stream, id, name, first_name, last_name, generic"); 
	confBuilder.setUseSSL(true); confBuilder.setJSONStoreEnabled(true); // Create configuration object 
	Configuration configuration = confBuilder.build(); // Create facebook instance 
	FacebookFactory ff = new FacebookFactory(configuration); 
	Facebook facebook = ff.getInstance(); 
		String results = getFacebookPostes(facebook); 
		System.out.println("results : "+results);
		String responce = stringToJson(results); // Create file and write to the file 
		System.out.println("responce : "+responce);
		
	}
public static String getFacebookPostes(Facebook facebook) throws FacebookException { // Get posts for a particular search 
	ResponseList<Post> results = facebook.getPosts("10156263168564138"); 
	return results.toString(); 
	} 
public static String stringToJson(String data) { // Create JSON object 
	JSONObject jsonObject = JSONObject.fromObject(data); 
	JSONArray message = (JSONArray) jsonObject.get("message"); 
	System.out.println("Message : "+message);
	return "Done";
	} 
}

