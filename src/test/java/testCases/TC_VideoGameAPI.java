package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;

public class TC_VideoGameAPI {
	
	@Test(priority=1)
	public void Test_getAllVideoGame() {
		
		given()
		
		.when()
		  .get("http://localhost:8080/app/videogames")
		  
		 .then()
		   .statusCode(200);	
	}
	
	@Test(priority=2)
	public void Test_addNewVideoGame() {
		HashMap data = new HashMap();
		data.put("id", "101");
		data.put("name", "IronMan");
		data.put("releaseDate", "2022-10-26T11:55:28.510Z");
		data.put("reviewScore", "5");
		data.put("category", "Adventure");
		data.put("rating", "Universal");
		
		Response res=
		given()
		 .contentType("application/json")
		 .body(data)
		 
		 .when()
		   .post("http://localhost:8080/app/videogames")
		   
		  .then()
		   .statusCode(200)
		   .log().body()
		   .extract().response();
		   
		   String jsonPath=res.asString();
		   Assert.assertEquals(jsonPath.contains("Record Added Successfully"), true);	
		
	}
	
	@Test(priority=3)
	public void Test_getVideoGame() {
		
		given()
		
		.when()
		  .get("http://localhost:8080/app/videogames/101")
		  
		  .then()
		    .statusCode(200)
		    .log().body()
		    .body("VideoGame.id", equalTo("101"))
		    .body("VideoGame.name", equalTo("IronMan"));
	}
	
	@Test(priority=4)
	public void Test_updateVideoGame() {
		HashMap data = new HashMap();
		data.put("id", "101");
		data.put("name", "Doremon");
		data.put("releaseDate", "2022-10-26T11:55:28.510Z");
		data.put("reviewScore", "3");
		data.put("category", "Adventure");
		data.put("rating", "Universal");
		
      given()
       .contentType("application/json")
       .body(data)
       
       .when()
        .put("http://localhost:8080/app/videogames/101")
        
        .then()
	    .statusCode(200)
	    .log().body()
	    .body("VideoGame.id", equalTo("101"))
	    .body("VideoGame.name", equalTo("Doremon"));
		
	}
	
	@Test(priority=5)
	public void Test_deleteVideoGame() throws InterruptedException {
		
		Response res=
		given()
		
		.when()
		 .delete("http://localhost:8080/app/videogames/101")
		 
		.then()
		 .statusCode(200)
		 .log().body()
		 .extract().response();
		
		Thread.sleep(3000);
		
		String jsonPath = res.asString();
		Assert.assertEquals(jsonPath.contains("Record Deleted Successfully"), true);
	}

}
