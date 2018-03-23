package com.myApp;

import java.util.Scanner;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.apis.LinkedInApi20;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import java.io.IOException;
public abstract class LinkedIn20Example {
private static final String NETWORK_NAME = "LinkedIn";
private static final String PROTECTED_RESOURCE_URL = "http://ift.tt/1IMslDz";
public static void main(String... args) throws IOException {
final String clientId = "77nlpycj04rzn9";
final String clientSecret = "K87iE57azjh906Q9";
final OAuth20Service service = new ServiceBuilder()
.apiKey(clientId).apiSecret(clientSecret)
.scope("r_basicprofile") // replace with desired scope
.callback("https://localhost/auth/linkedin/callback")
.state("DCEEFWF454Us5dffef424")
.build(LinkedInApi20.instance());
System.out.println("Fetching the Authorization URL...");
final String authorizationUrl = service.getAuthorizationUrl();
System.out.println("Got the Authorization URL!");
System.out.println("Now go and authorize ScribeJava here:");
System.out.println(authorizationUrl);
System.out.println("And paste the authorization code here");
System.out.print(">>");
System.out.println();
System.out.println("Trading the Request Token for an Access Token...");
final OAuth2AccessToken accessToken = service.getAccessToken("client_credentials");
System.out.println("Got the Access Token!");
System.out.println("(if your curious it looks like this: " + accessToken
+ ", 'rawResponse'='" + accessToken.getRawResponse() + "')");
System.out.println();
 
// Now let's go and ask for a protected resource!
System.out.println("Now we're going to access a protected resource...");
while (true) {
System.out.println("Paste profile query for fetch (firstName, lastName, etc) or 'exit' to stop example");
System.out.print(">>");
//final String query = in.nextLine();
System.out.println();
 
final OAuthRequest request = new OAuthRequest(Verb.GET, String.format(PROTECTED_RESOURCE_URL),
service);
request.addHeader("x-li-format", "json");
request.addHeader("Accept-Language", "ru-RU");
service.signRequest(accessToken, request);
final Response response = request.send();
System.out.println();
System.out.println(response.getCode());
System.out.println(response.getBody());
 
System.out.println();
}
}
}