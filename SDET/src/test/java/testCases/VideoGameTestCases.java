package testCases;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Assert;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.HashMap;
import java.util.Map;

public class VideoGameTestCases {
	
	//@Test(priority = 1)
	public void GETTESTCASE() {
		given()
		.when()
		.get("http://localhost:8080/app/videogames")
		.then()
		.statusCode(200);
		
	}
	
	@Test(priority = 2)
	public void POSTTESTCASE() {
		
		Map<String, Object> data = new HashMap<String, Object >();
		
		data.put("id","100");
		data.put("name", "string");
		data.put( "releaseDate", "2020-02-28T01:22:24.256Z");
		data.put("reviewScore","5");
		data.put("category","Adventure");
		data.put("rating","Universal");
	
		Response res=		
		given()
			.contentType(ContentType.JSON)
			.body(data)
		.when()
			.post("http://localhost:8080/app/videogames")
		.then()
			.statusCode(200)
			.log().body()
			.extract().response();
		
		String jsonString=res.asString();
		Assert.assertEquals(jsonString.contains("Recod Added Successfully"),true);
	}
	
	@Test(priority=3)
	public void GETVIDEOGAMEINFO()
	{
		given()
		.when()
		.get("http://localhost:8080/app/videogames/100")
		.then()
		.statusCode(200)
		.log().body()
		.body("videoGame.id", equalTo("100"))
		.body("videoGame.name", equalTo("Spider-Man"));		
	}
	
	@Test(priority=4)
	public void UPDATEVIDEOGAMEINFO()
	{
		Map<String, Object> data1 = new HashMap<String, Object >();
		data1.put("id","100");
		data1.put("name", "PACNAM");
		data1.put( "releaseDate", "2020-02-28T01:22:24.256Z");
		data1.put("reviewScore","4");
		data1.put("category","Adventure");
		data1.put("rating","Universal");
		
		given()
			.contentType(ContentType.JSON)
			.body(data1)
			.when()
			.put("http://localhost:8080/app/videogames/100")
			.then()
			.statusCode(200)
			.log().body()
			.body("videoGame.id", equalTo("100"))
			.body("videoGame.name", equalTo("PACMAN"));
	}
	
	@Test(priority=5)
	public void DELETEGAMEINFO()
	{
		Response res = 
		given()
		.when()
		.delete("http://localhost:8080/app/videogames/100")
		.then()
		.statusCode(200)
		.log().body()
		.extract().response();	
		
		String jsonString = res.asString();
		Assert.assertEquals(jsonString.contains("Record Deleted Succesfully"),true);
	}

}
