package com.test.restassured.framwork;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.util.List;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

public class LoginTest {
	private JSONObject registerRequestJson;
	
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
		registerRequestJson.put("email","peter@klaven");
		registerRequestJson.put("password","cityslicka");
	}
	
	@Test(groups= {"login"})
	public void testLoginSuccessful() {
		
		String token = given().contentType("application/json").body(registerRequestJson)
				       .when().post("/login").then().statusCode(200).extract()
				       .path("token");
		Assert.assertNotNull(token);
	}
	
	@Test(groups= {"login"})
	public void testFetchUsers() {
		UserRes response = given().param("page", "2").when().get("/users").then().statusCode(200)
		.extract().body().as(UserRes.class);
		
		List<User> users = response.getData();
		Assert.assertEquals(users.get(0).getId(), 4);
	}
	
	@Test(groups= {"login"})
	public void testHasItems() {
		given().queryParam("page", "2").when().get("/users").then()//param
		.assertThat().body("data.id", hasItems(4,5,6));
	}
}
