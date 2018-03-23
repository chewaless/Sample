package com.myApp;

import facebook4j.Comment;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.PagableList;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;
import facebook4j.auth.OAuthAuthorization;
import facebook4j.auth.OAuthSupport;
import facebook4j.conf.Configuration;
import facebook4j.conf.ConfigurationBuilder;

public class PostsFromPageExtractor {

/**
 * A simple Facebook4J client which
 * illustrates how to access group feeds / posts / comments.
 * 
 * @param args
 * @throws FacebookException 
 */
public static void main(String[] args) throws FacebookException {
	
	ConfigurationBuilder config = new ConfigurationBuilder();
	config.setOAuthAppId("1549039661879593");
    config.setOAuthAppSecret("eb059d13477ab884e2a454b4370fd576");
    config.setOAuthAccessToken("1549039661879593|2z4wytbh4sRIssPIuungxfi6GqE");
    config.setUseSSL(true);
    config.setJSONStoreEnabled(true);
    Configuration configuration=config.build();
    facebook4j.Facebook facebook = new FacebookFactory(configuration).getInstance();
    System.out.println("facebook:"+facebook.toString());
    System.out.println("facebook:likes:"+facebook.links());
    
    AccessToken accessToken = null;
    try{
        OAuthSupport oAuthSupport = new OAuthAuthorization(configuration);
        accessToken = oAuthSupport.getOAuthAppAccessToken();
       // facebook.setOAuthAccessToken(new AccessToken(accessToken,null));
        System.out.println("facebook:accessToken:"+accessToken);
    }catch (FacebookException e) {
    	e.printStackTrace();
    }
    
   // setConnection(facebook);
	/*

    // Generate facebook instance.
    Facebook facebook = new FacebookFactory().getInstance();
    // Use default values for oauth app id.
    facebook.setOAuthAppId("1549039661879593", "eb059d13477ab884e2a454b4370fd576");
    // Get an access token from: 
    // https://developers.facebook.com/tools/explorer
    // Copy and paste it below.
    String accessTokenString = "d430c4338c604811d4f8ba44159c2912";
    AccessToken at = new AccessToken(accessTokenString);
    // Set access token.
    facebook.setOAuthAccessToken(at);

    // We're done.
    // Access group feeds.
    // You can get the group ID from:
    // https://developers.facebook.com/tools/explorer

    // Set limit to 25 feeds.  */
    ResponseList<Post> feeds = facebook.getFeed("https://www.facebook.com/Zensar/posts/10156263168564138");

        // For all 25 feeds...
        for (int i = 0; i < feeds.size(); i++) {
            // Get post.
            Post post = feeds.get(i);
            // Get (string) message.
            String message = post.getMessage();
                            // Print out the message.
            System.out.println(message);

            // Get more stuff...
            PagableList<Comment> comments = post.getComments();
            String date = post.getCreatedTime().toString();
            String name = post.getFrom().getName();
            String id = post.getId();
        }           
    }
}
