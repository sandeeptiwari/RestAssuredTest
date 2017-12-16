package com.test.restassured.framwork;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
/**
 * Unit test for simple App.
 */
public class AppTest 
{
	private JSONObject registerRequestJson;
	
	/**
	 * Note:- if didn't define always run and run specific 
	 * 		  group this method wouldn't execute.
	 *        So you must have to put alwaysRun = true
	 *        if you want to run specific group, because your test
	 *        depend on setUp and setUpBeforeTest
	 */
	@BeforeSuite(alwaysRun = true)
	public void setUp() {
		baseURI = "https://reqres.in/api";
		//RestAssured.port = 8080;
		//basePath = "/books";
		
	}
	
	/**
	 * Note:- if didn't define always run and run specific 
	 * 		  group this method wouldn't execute.
	 *        So you must have to put alwaysRun = true
	 *        if you want to run specific group, because your test
	 *        depend on setUp and setUpBeforeTest
	 */
	@BeforeTest(alwaysRun = true)
	public void setUpBeforeTest() {
		registerRequestJson = new JSONObject();
		registerRequestJson.put("email","sydney@fife");
		registerRequestJson.put("password","pistol");
	}
	/**
	 * JSON POST using RestAssured
	 * Request body is :
	 * 	{
	 * 		"email": "sydney@fife",
	 * 		"password": "pistol"
	 * 	}
	 * Response Code :: 
	 * 201 is [
	 * 				Created. The request has been fulfilled and resulted in a new resource being created. 
	 * 				The newly created resource can be referenced by the URI(s) returned in the entity of the response,
	 *  			with the most specific URI for the resource given by a Location header field.
	 * 			]
	 */
	@Test(priority = 0, groups= {"register"})
	public void testRegisterUserApiStatusCode() {
		given().contentType("application/json").body(registerRequestJson)
		.when().post("/register").then().assertThat().statusCode(201);
	}
	
	/**
	 * Test response json is vallid json oR not
	 */
	@Test (priority = 1, dependsOnMethods= {"testRegisterUserApiStatusCode"}, groups= {"register1"})
	public void testRegisterUserApiResponseJsonSchemaValidation() {
		given().contentType("application/json").body(registerRequestJson)
		.when().post("/register").then().assertThat().body(matchesJsonSchemaInClasspath("register.json"));
	}
	
	@Test(priority = 2, groups= {"register"})
	public void testRegisterUserApiContainsToke() {
		String responseString  = given().contentType("application/json").body(registerRequestJson).when()
		.post("/register").asString();
		
		JsonPath jsonPath = new JsonPath(responseString);
		String token = jsonPath.get("token");
		Assert.assertNotNull(token);
	}
	
	

}
