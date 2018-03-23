package com.myApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.SignatureType;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;


public class Main {

    private static String API_KEY = "77nlpycj04rzn9";
    private static String API_SECRET = "K87iE57azjh906Q9";
    private static String callbackURL = "https://localhost/auth/linkedin/callback";

    public static void main(String[] args) {

      System.out.println("Main Method calling.");
        
        Token accessToken = null;

        OAuthService service = new ServiceBuilder()
                                .provider(LinkedInApi.withScopes("r_basicprofile"))
                                .apiKey(API_KEY)
                                .apiSecret(API_SECRET)
                                .callback(callbackURL)
                                .debug()
                                .build();

        try{
                System.out.println("There is no stored Access token we need to make one");
                //In the constructor the AuthHandler goes through the chain of calls to create an Access Token
                AuthHandler authHandler = new AuthHandler(service);
                accessToken = authHandler.getAccessToken();

        }catch (Exception e){
            System.out.println("Threw an exception when serializing: " + e.getClass() + " :: " + e.getMessage());
        }

        System.out.println();
        System.out.println("********A basic user profile call********");
        String url = "http://api.linkedin.com/v1/people/~";
        OAuthRequest request = new OAuthRequest(Verb.GET, url);
        service.signRequest(accessToken, request);
        Response response = request.send();
        System.out.println(response.getBody());
        System.out.println();System.out.println();

        System.out.println("********Get the profile in JSON********");
        url = "http://api.linkedin.com/v1/people/~";
        request = new OAuthRequest(Verb.GET, url);
        request.addHeader("x-li-format", "json");
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getBody());
        System.out.println();System.out.println();

        System.out.println("********Get the profile in JSON using query parameter********");
        url = "http://api.linkedin.com/v1/people/~";
        request = new OAuthRequest(Verb.GET, url);
        request.addQuerystringParameter("format", "json");
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getBody());
        System.out.println();System.out.println();


        System.out.println("********Get my connections - going into a resource********");
        url = "http://api.linkedin.com/v1/people/~/connections";
        request = new OAuthRequest(Verb.GET, url);
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getBody());
        System.out.println();System.out.println();


        System.out.println("********Get only 10 connections - using parameters********");
        url = "http://api.linkedin.com/v1/people/~/connections";
        request = new OAuthRequest(Verb.GET, url);
        request.addQuerystringParameter("count", "10");
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getBody());
        System.out.println();System.out.println();


        System.out.println("********GET network updates that are CONN and SHAR********");

        url = "http://api.linkedin.com/v1/people/~/network/updates";
        request = new OAuthRequest(Verb.GET, url);
        request.addQuerystringParameter("type","SHAR");
        request.addQuerystringParameter("type","CONN");
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getBody());
        System.out.println();System.out.println();


        System.out.println("********People Search using facets and Encoding input parameters i.e. UTF8********");

        //url = "http://api.linkedin.com/v1/people-search?title=D%C3%A9veloppeur&facets=location,industry&facet=location,fr,0";
       url = "http://api.linkedin.com/v1/people-search:(people:(first-name,last-name,headline),facets:(code,buckets))";
        request = new OAuthRequest(Verb.GET, url);
        request.addQuerystringParameter("title", "Développeur");
        request.addQuerystringParameter("facet", "industry,4");
        request.addQuerystringParameter("facets", "location,industry");
        System.out.println(request.getUrl());
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getBody());
        System.out.println();System.out.println();

        /////////////////field selectors
        System.out.println("********A basic user profile call with field selectors********");
        //The ~ means yourself - so this should return the basic default information for your profile in XML format
        //https://developer.linkedin.com/documents/field-selectors
        url = "http://api.linkedin.com/v1/people/~:(first-name,last-name,positions)";
        request = new OAuthRequest(Verb.GET, url);
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getHeaders().toString());
        System.out.println(response.getBody());
        System.out.println();System.out.println();


        System.out.println("********A basic user profile call with field selectors going into a subresource********");
        //The ~ means yourself - so this should return the basic default information for your profile in XML format
        //https://developer.linkedin.com/documents/field-selectors
        url = "http://api.linkedin.com/v1/people/~:(first-name,last-name,positions:(company:(name)))";
        request = new OAuthRequest(Verb.GET, url);
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getHeaders().toString());
        System.out.println(response.getBody());
        System.out.println();System.out.println();


        System.out.println("********A basic user profile call into a subresource return data in JSON********");
        //The ~ means yourself - so this should return the basic default information for your profile
        //https://developer.linkedin.com/documents/field-selectors
        url = "https://api.linkedin.com/v1/people/~/connections:(first-name,last-name,headline)?format=json";
        request = new OAuthRequest(Verb.GET, url);
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getHeaders().toString());
        System.out.println(response.getBody());
        System.out.println();System.out.println();

        System.out.println("********A more complicated example using postings into groups********");
        //https://developer.linkedin.com/documents/field-selectors
        //https://developer.linkedin.com/documents/groups-api
        url = "http://api.linkedin.com/v1/groups/3297124/posts:(id,category,creator:(id,first-name,last-name),title,summary,creation-timestamp,site-group-post-url,comments,likes)";
        request = new OAuthRequest(Verb.GET, url);
        service.signRequest(accessToken, request);
        response = request.send();
        System.out.println(response.getHeaders().toString());
        System.out.println(response.getBody());
        System.out.println();System.out.println();



        System.out.println();
        System.out.println("********A basic user profile call and response dissected********");
        //This sample is mostly to help you debug and understand some of the scaffolding around the request-response cycle
        //https://developer.linkedin.com/documents/debugging-api-calls
        url = "https://api.linkedin.com/v1/people/~";
        request = new OAuthRequest(Verb.GET, url);
        service.signRequest(accessToken, request);
        response = request.send();
        //get all the headers
        System.out.println("Request headers: " + request.getHeaders().toString());
        System.out.println("Response headers: " + response.getHeaders().toString());
        //url requested
        System.out.println("Original location is: " + request.getHeaders().get("content-location"));
        //Date of response
        System.out.println("The datetime of the response is: " + response.getHeader("Date"));
        //the format of the response
        System.out.println("Format is: " + response.getHeader("x-li-format"));
        //Content-type of the response
        System.out.println("Content type is: " + response.getHeader("Content-Type") + "\n\n");

        //get the HTTP response code - such as 200 or 404
        int responseNumber = response.getCode();

        if(responseNumber >= 199 && responseNumber < 300){
            System.out.println("HOORAY IT WORKED!!");
            System.out.println(response.getBody());
        } else if (responseNumber >= 500 && responseNumber < 600){
            //you could actually raise an exception here in your own code
            System.out.println("Ruh Roh application error of type 500: " + responseNumber);
            System.out.println(response.getBody());
        } else if (responseNumber == 403){
            System.out.println("A 403 was returned which usually means you have reached a throttle limit");
        } else if (responseNumber == 401){
            System.out.println("A 401 was returned which is a Oauth signature error");
            System.out.println(response.getBody());
        } else if (responseNumber == 405){
            System.out.println("A 405 response was received. Usually this means you used the wrong HTTP method (GET when you should POST, etc).");
        }else {
            System.out.println("We got a different response that we should add to the list: " + responseNumber + " and report it in the forums");
            System.out.println(response.getBody());
        }
        System.out.println();System.out.println();


        System.out.println("********A basic error logging function********");
        // Now demonstrate how to make a logging function which provides us the info we need to
        // properly help debug issues. Please use the logged block from here when requesting
        // help in the forums.
        url = "https://api.linkedin.com/v1/people/FOOBARBAZ";
        request = new OAuthRequest(Verb.GET, url);
        service.signRequest(accessToken, request);
        response = request.send();

        responseNumber = response.getCode();

        if(responseNumber < 200 || responseNumber >= 300){
            logDiagnostics(request, response);
        } else {
            System.out.println("You were supposed to submit a bad request");
        }

        System.out.println("******Finished******");

    }

    private static void logDiagnostics(OAuthRequest request, Response response){
        System.out.println("\n\n[********************LinkedIn API Diagnostics**************************]\n");
        System.out.println("Key: |-> " + API_KEY + " <-|");
        System.out.println("\n|-> [******Sent*****] <-|");
        System.out.println("Headers: |-> " + request.getHeaders().toString() + " <-|");
        System.out.println("URL: |-> " + request.getUrl() + " <-|");
        System.out.println("Query Params: |-> " + request.getQueryStringParams().toString() + " <-|");
        System.out.println("Body Contents: |-> " + request.getBodyContents() + " <-|");
        System.out.println("\n|-> [*****Received*****] <-|");
        System.out.println("Headers: |-> " + response.getHeaders().toString() + " <-|");
        System.out.println("Body: |-> " + response.getBody() + " <-|");
        System.out.println("\n[******************End LinkedIn API Diagnostics************************]\n\n");
    }


}
