import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import static io.restassured.RestAssured.get;

public class RestAssuredAPITests {

	@Test
	public void get_status_code() {
		Response response = get(RestAssuredAPIHelper.BY_SEARCH_URI);
		int statusCode = response.getStatusCode();
		System.out.println("********************\nStatus Code : " + statusCode + "\n********************\n");
		Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
	}

	@Test
	public void get_id_by_search() {
		String result_id = RestAssuredAPIHelper.get_id_from_title();
		System.out.println("********************\nResult : " + result_id + "\n********************\n");
	}

	@Test
	public void check_values() {
		Response response = get(RestAssuredAPIHelper.BY_SEARCH_URI);
		JsonPath path = response.jsonPath();
		String[] resultList = RestAssuredAPIHelper.check_all_values(path, "Search", "Title", "Harry Potter and the Sorcerer's Stone");

		System.out.println("********************\nTitle : " + resultList[0] + "\n********************\n");
		System.out.println("********************\nYear : " + resultList[1] + "\n********************\n");
		System.out.println("********************\nIMDB ID : " + resultList[2] + "\n********************\n");
		System.out.println("********************\nType : " + resultList[3] + "\n********************\n");
		System.out.println("********************\nPoster : " + resultList[4] + "\n********************\n");

		Assert.assertNotNull(resultList[0]);
		Assert.assertNotNull(resultList[1]);
		Assert.assertNotNull(resultList[2]);
		Assert.assertNotNull(resultList[3]);
		Assert.assertNotNull(resultList[4]);
	}

	@Test
	public void get_search_by_id() {
		String result_id = RestAssuredAPIHelper.get_id_from_title();
		Response resp = get(RestAssuredAPIHelper.BY_ID_URI + "i=" + result_id);
		System.out.println("********************\nPath : " + resp.asString() + "\n********************\n");
		Assert.assertNotNull(resp);
	}
}
