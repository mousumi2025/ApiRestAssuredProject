package testCase;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.util.Map;
import utilities.Dataprovider;
import utilities.LoggerLoad;
import RequestBodyData.AddressInfo;
import RequestBodyData.UserInfo;

public class SwaggerAPI extends BaseTest {
	
int UsrID;
String Name;
	
	@Test(priority = 1, dataProvider = "GetUser", dataProviderClass = Dataprovider.class)
	public void GetAllUsersTest(Map<String, String> rowData) {
		
		int code = Double.valueOf(rowData.get("Code")).intValue();
		String LineMsg =rowData.get("Linemsg");
		String Scenario = rowData.get("Scenario");
		String url = BaseURL+SearchAllEp;
		
		LoggerLoad.info("Inside the Get All Users Test  BaseURL  : " +url);
		LoggerLoad.info("Inside the Get All Users Test  Username  : " +Username+ " Password : " +Password);
		
		LoggerLoad.info("GET REQUEST: "+Scenario);
		if ("Valid".equals(Scenario)) {
			given().auth().basic(Username,Password)
			.when().get(BaseURL+SearchAllEp)
			.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
			
		}
		else if("NoAuth".equals(Scenario)) {
				given().auth().basic(eUserName,ePassword)
				.when().get(BaseURL+SearchAllEp)
				.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
				
		}
		else if("EmptyUsr".equals(Scenario)) {	
				given().auth().basic(eUserName,Password)
				.when().get(BaseURL+SearchAllEp)
				.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);	
				
		}
		else if("EmptyPswd".equals(Scenario)) {
				given().auth().basic(Username,ePassword)
				.when().get(BaseURL+SearchAllEp)
				.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);	
				
		}
		
		else if("IncorrectBaseURL".equals(Scenario)) {
			given().auth().basic(Username,Password)
			.when().get(InBaseURL+SearchAllEp)
			.then().statusCode(code).statusLine(LineMsg);
			
		}	
		else {
			LoggerLoad.info("GET REQUEST SKIPPED manually: "+Scenario);
			throw new SkipException("Skipping this test due to a specific condition.");
		}
	}


	@Test(priority = 2, dataProvider = "PostUser", dataProviderClass = Dataprovider.class)
	public void CreateUser(Map<String, String> rowData) {
		
		int code = Double.valueOf(rowData.get("Code")).intValue();
		String LineMsg =rowData.get("Linemsg");
		String Scenario = rowData.get("Scenario");
		String FName = rowData.get("FirstName");
		String LName = rowData.get("LastName");
		String Phone = rowData.get("Phno");			
		String Mail = rowData.get("Email");
		String Plot = rowData.get("Plot");
		String Street = rowData.get("Street");
		String State = rowData.get("State");
		String Country = rowData.get("Country");
		String Zip = rowData.get("Zip");
		
		 // :white_check_mark: Construct nested address JSON
	    JSONObject address = new JSONObject()
	        .put("plotNumber", Plot)
	        .put("street", Street)
	        .put("state", State)
	        .put("country", Country)
	        .put("zipCode", Zip);
	    // :white_check_mark: Construct main JSON object with address nested
	    JSONObject jsonObj = new JSONObject()
	        .put("userFirstName", FName)
	        .put("userLastName", LName)
	        .put("userContactNumber", Phone)
	        .put("userEmailId", Mail)
	        .put("userAddress", address);
		LoggerLoad.info("POST REQUEST: "+Scenario);
		
		 if("Valid".equals(Scenario)){	
			 System.out.println("INSUDE VALID");
			Response response =
			given().auth().basic(Username,Password).header("Content-Type", "application/json").body(jsonObj.toString()).log().all()
			.when().post(BaseURL+CreateEP);
			System.out.println("Response Body:\n" + response.getBody().asString());
			System.out.println("Response Code: " + response.getStatusCode());
			System.out.println("Status Line: " + response.getStatusLine());
			
//
			JsonPath jsonPath = response.jsonPath();
			System.out.println("FName : " +FName);
			System.out.println("jsonPath : " +jsonPath.prettify());
			Assert.assertEquals(jsonPath.getString("userFirstName"), FName);
			System.out.println(jsonPath.getString("userFirstName")+"  Compare  "+FName);
			Assert.assertEquals(jsonPath.getString("userLastName"), LName);
			System.out.println(jsonPath.getString("userLastName")+"  Compare  "+LName);
			Assert.assertEquals(jsonPath.getString("userContactNumber"), Phone);
			System.out.println(jsonPath.getString("userContactNumber")+"  Compare  "+Phone);
			Assert.assertEquals(jsonPath.getString("userEmailId"), Mail);
			System.out.println(jsonPath.getString("userEmailId")+"  Compare  "+Mail);
			Assert.assertEquals(jsonPath.getString("userAddress.plotNumber"), Plot);
			Assert.assertEquals(jsonPath.getString("userAddress.street"), Street);
			Assert.assertEquals(jsonPath.getString("userAddress.state"), State);
			Assert.assertEquals(jsonPath.getString("userAddress.country"), Country);
			Assert.assertEquals(jsonPath.getString("userAddress.zipCode"), Zip);
		    UsrID = Integer.parseInt(jsonPath.getString("userId"));
		    Name = jsonPath.getString("userFirstName");
			System.out.println(jsonPath.getInt("userId"));		
		}
		 else if("NoAuth".equals(Scenario)) {
				given().auth().basic(eUserName,ePassword).header("Content-Type", "application/json").body(jsonObj.toString()).log().all()
				.when().post(BaseURL+CreateEP)
				.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
		}
		else if("EmptyUsr".equals(Scenario)) {
				given().auth().basic(eUserName,Password).header("Content-Type", "application/json").body(jsonObj.toString()).log().all()
				.when().post(BaseURL+CreateEP)
				.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
		}
		else if("EmptyPswd".equals(Scenario)) {
				given().auth().basic(Username,ePassword).header("Content-Type", "application/json").body(jsonObj.toString()).log().all()
				.when().post(BaseURL+CreateEP)
				.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
		}
		else if("IncorrectEndPoint".equals(Scenario)) {
			given().auth().basic(Username,Password).header("Content-Type", "application/json").body(jsonObj.toString()).log().all()
			.when().post(BaseURL+CreateEP+"@invalid")
			.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
		}
		else if("IncorrectBaseURL".equals(Scenario)) {
			given().auth().basic(Username,Password).header("Content-Type", "application/json").body(jsonObj.toString()).log().all()
			.when().post(InBaseURL+CreateEP)
			.then().statusCode(code).statusLine(LineMsg);
		}
								
		else {
			given().auth().basic(Username,Password).header("Content-Type", "application/json").body(jsonObj.toString()).log().all()
			.when().post(BaseURL+CreateEP)
			.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
		}
		
	}
	
	@Test(priority = 3, dataProvider = "GetUser", dataProviderClass = Dataprovider.class)
	public void GetUserByFNameTest(Map<String, String> rowData) {
		int code = Double.valueOf(rowData.get("Code")).intValue();
		String LineMsg =rowData.get("Linemsg");
		String Scenario = rowData.get("Scenario");
		
		LoggerLoad.info("GET REQUEST By Name: "+Scenario);

		if ("Valid".equals(Scenario)) {
			given().auth().basic(Username,Password).pathParam("fname", Name).log().all()
			.when().get(BaseURL+SearchByNamEP)
			.then().assertThat().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
		}
		else if("NoAuth".equals(Scenario)) {
				given().auth().basic(eUserName,ePassword).pathParam("fname", Name).log().all()
				.when().get(BaseURL+SearchByNamEP)
				.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
		}
		else if("EmptyUsr".equals(Scenario)) {
				given().auth().basic(eUserName,Password).pathParam("fname", Name).log().all()
				.when().get(BaseURL+SearchByNamEP)
				.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
		}
		else if("EmptyPswd".equals(Scenario)) {
				given().auth().basic(Username,ePassword).pathParam("fname", Name).log().all()
				.when().get(BaseURL+SearchByNamEP)
				.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
		}		
		else if("IncorrectBaseURL".equals(Scenario)) {
			given().auth().basic(Username,Password).pathParam("fname", Name).log().all()
			.when().get(InBaseURL+SearchByNamEP)
			.then().statusCode(code).statusLine(LineMsg);
		}	
		else if("NonExistingUser".equals(Scenario)) {
			given().auth().basic(Username,Password).pathParam("fname", Name+"API").log().all()
			.when().get(BaseURL+SearchByNamEP)
			.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
		}	
	}
	
	@Test(priority = 4, dataProvider = "GetUser", dataProviderClass = Dataprovider.class)
	public void GetUserByIdTest(Map<String, String> rowData) {
		int code = Double.valueOf(rowData.get("Code")).intValue();
		String LineMsg =rowData.get("Linemsg");
		String Scenario = rowData.get("Scenario");
		
		LoggerLoad.info("GET REQUEST By ID UsrID : " +UsrID);
		
		LoggerLoad.info("GET REQUEST By ID: "+Scenario);
		
		LoggerLoad.info(BaseURL+SearchByidEP);

		if ("Valid".equals(Scenario)) {
			given().auth().basic(Username,Password).pathParam("id", UsrID).log().all()
			.when().get(BaseURL+SearchByidEP)
			.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
			//.then().assertThat().body(matchesJsonSchemaInClasspath("schema.json")).statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
			
		}
		else if("NoAuth".equals(Scenario)) {
				given().auth().basic(eUserName,ePassword).pathParam("id", UsrID).log().all()
				.when().get(BaseURL+SearchByidEP)
				.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
		}
		else if("EmptyUsr".equals(Scenario)) {
				given().auth().basic(eUserName,Password).pathParam("id", UsrID).log().all()
				.when().get(BaseURL+SearchByidEP)
				.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
		}
		else if("EmptyPswd".equals(Scenario)) {
				given().auth().basic(Username,ePassword).pathParam("id", UsrID).log().all()
				.when().get(BaseURL+SearchByidEP)
				.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
		}
		else if("IncorrectEndPoint".equals(Scenario)) {
			given().auth().basic(Username,Password).pathParam("id", UsrID).log().all()
			.when().get(BaseURL+SearchByidEP+"$invalid")
			.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
		}
		else if("IncorrectBaseURL".equals(Scenario)) {
			given().auth().basic(Username,Password).pathParam("id", UsrID).log().all()
			.when().get(InBaseURL+SearchByidEP)
			.then().statusCode(code).statusLine(LineMsg);
		}	
		else if("NonExistingUser".equals(Scenario)) {
			given().auth().basic(Username,Password).pathParam("id", UsrID+1000).log().all()
			.when().get(BaseURL+SearchByidEP)
			.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
		}	
	}
	
	@Test(priority = 5, dataProvider = "UpdateUser", dataProviderClass = Dataprovider.class)
	public void UpdateUser(Map<String, String> rowData) {
		
		
		int code = Double.valueOf(rowData.get("Code")).intValue();
		String LineMsg =rowData.get("Linemsg");
		String Scenario = rowData.get("Scenario");
		String FName = rowData.get("FirstName");
		String LName = rowData.get("LastName");
		String Phone = rowData.get("Phno");			
		String Mail = rowData.get("Email");
		String Plot = rowData.get("Plot");
		String Street = rowData.get("Street");
		String State = rowData.get("State");
		String Country = rowData.get("Country");
		String Zip = rowData.get("Zip");
	
		LoggerLoad.info("PUT REQUEST By ID: "+Scenario);
		
		 // :white_check_mark: Construct nested address JSON
	    JSONObject address = new JSONObject()
	        .put("plotNumber", Plot)
	        .put("street", Street)
	        .put("state", State)
	        .put("country", Country)
	        .put("zipCode", Zip);

	    // :white_check_mark: Construct main JSON object with address nested
	    JSONObject jsonObj = new JSONObject()
	        .put("userFirstName", FName)
	        .put("userLastName", LName)
	        .put("userContactNumber", Phone)
	        .put("userEmailId", Mail)
	        .put("userAddress", address);
		LoggerLoad.info("POST REQUEST: "+Scenario);
		
		 if("Valid".equals(Scenario)){	
			 System.out.println("INSUDE VALID");
			Response response =
	
			given().auth().basic(Username,Password).pathParam("id", UsrID).header("Content-Type", "application/json").body(jsonObj.toString()).log().all()
			.when().put(BaseURL+UpdateEP);
			response.then().statusCode(code).header("Content-Type", Json).statusLine(LineMsg);

			System.out.println("Response Body:\n" + response.getBody().asString());
			System.out.println("Response Code: " + response.getStatusCode());
			System.out.println("Status Line: " + response.getStatusLine());
			

			JsonPath jsonPath = response.jsonPath();
			Assert.assertEquals(jsonPath.getString("userFirstName"), FName);
			System.out.println(jsonPath.getString("userFirstName")+"    "+FName);
			Assert.assertEquals(jsonPath.getString("userLastName"), LName);
			System.out.println(jsonPath.getString("userLastName")+"    "+LName);
			Assert.assertEquals(jsonPath.getString("userContactNumber"), Phone);
			System.out.println(jsonPath.getString("userContactNumber")+"    "+Phone);
			Assert.assertEquals(jsonPath.getString("userEmailId"), Mail);
			System.out.println(jsonPath.getString("userEmailId")+"    "+Mail);
			Assert.assertEquals(jsonPath.getString("userAddress.plotNumber"), Plot);
			Assert.assertEquals(jsonPath.getString("userAddress.street"), Street);
			Assert.assertEquals(jsonPath.getString("userAddress.state"), State);
			Assert.assertEquals(jsonPath.getString("userAddress.country"), Country);
			Assert.assertEquals(jsonPath.getString("userAddress.zipCode"), Zip);
		    UsrID = jsonPath.getInt("userId");
		    Name = jsonPath.getString("userFirstName");
			System.out.println(jsonPath.getInt("userId"));		
			System.out.println(Name);
		}
		else {
			given().auth().basic(Username,Password).pathParam("id", UsrID).header("Content-Type", Json).body(jsonObj).log().all()
			.when().put(BaseURL+UpdateEP)
			.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
		}
	}
	
	
	@Test(priority = 6, dataProvider = "DeleteUser", dataProviderClass = Dataprovider.class)
	public void DeleteUserByIdTest(Map<String, String> rowData) {
		int code = Double.valueOf(rowData.get("Code")).intValue();
		String LineMsg =rowData.get("Linemsg");
		String Scenario = rowData.get("Scenario");
		LoggerLoad.info("DELETE REQUEST By ID: "+Scenario);

		if ("Valid".equals(Scenario)) {
			given().auth().basic(Username,Password).pathParam("id", UsrID).log().all()
			.when().delete(BaseURL+DeleteByIdEP)
			.then().assertThat().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
		}	
		
		else if("NoAuth".equals(Scenario)) {
				given().auth().basic(eUserName,ePassword).pathParam("id", UsrID).log().all()
				.when().delete(BaseURL+DeleteByIdEP)
				.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
		}
		else if("EmptyUsr".equals(Scenario)) {
				given().auth().basic(eUserName,Password).pathParam("id", UsrID).log().all()
				.when().delete(BaseURL+DeleteByIdEP)
				.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
		}
		else if("EmptyPswd".equals(Scenario)) {
				given().auth().basic(Username,ePassword).pathParam("id", UsrID).log().all()
				.when().delete(BaseURL+DeleteByIdEP)
				.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
		}
		else if("IncorrectEndPoint".equals(Scenario)) {
			given().auth().basic(Username,Password).pathParam("id", UsrID).log().all()
			.when().delete(BaseURL+DeleteByIdEP+"$invalid")
			.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
		}
		else if("IncorrectBaseURL".equals(Scenario)) {
			given().auth().basic(Username,Password).pathParam("id", UsrID).log().all()
			.when().delete(InBaseURL+DeleteByIdEP)
			.then().statusCode(code).statusLine(LineMsg);
		}	
		else if("NonExistingUser".equals(Scenario)) {
			given().auth().basic(Username,Password).pathParam("id", UsrID+1000).log().all()
			.when().delete(BaseURL+DeleteByIdEP)
			.then().statusCode(code).statusLine(LineMsg).header("Content-Type", Json);
		}	
		
	}
	
	
}
