import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import static io.restassured.RestAssured.get;

public class RestAssuredAPIHelper {

	final static String ROOT_URI = "http://www.omdbapi.com/";
	final static String API_KEY = "?apikey=96ea553&";
	final static String BY_SEARCH_EXT = "s=Harry+Potter";
	final static String BY_SEARCH_URI = ROOT_URI + API_KEY + BY_SEARCH_EXT;
	final static String BY_ID_URI = ROOT_URI + API_KEY;

	public static String get_id_from_title() {
		Response response = get(BY_SEARCH_URI);
		JsonPath path = response.jsonPath();

		List<HashMap<String, Object>> data = path.getList("Search");
		for (HashMap<String, Object> singleObject : data) {
			if (singleObject.get("Title").equals("Harry Potter and the Sorcerer's Stone")) {
				return (String) singleObject.get("imdbID");
			}
		}
		throw new NoSuchElementException(String.format("Can't find : %s", "Harry Potter and the Sorcerer's Stone"));
	}

	public static String[] check_all_values(JsonPath path, String dataName, String dataType, String expectedValue) {
		List<HashMap<String, Object>> data = path.getList(dataName);
		for (HashMap<String, Object> singleObject : data) {
			if (singleObject.get(dataType).equals(expectedValue)) {
				String title = (String) singleObject.get("Title");
				String year = (String) singleObject.get("Year");
				String imdb = (String) singleObject.get("imdbID");
				String type = (String) singleObject.get("Type");
				String poster = (String) singleObject.get("Poster");

				return new String[]{title, year, imdb, type, poster};
			}
		}
		throw new NoSuchElementException(String.format("Can't find : %s", expectedValue));
	}
}
