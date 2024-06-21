package qa.automation.exceltdd;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class ExcelTest {

    @Test(dataProvider = "userFromSheet1", dataProviderClass = ExcelDataProviders.class)
    public void testExcel(String param1, String param2) {
        System.out.println("Пользователь "+ param2 + " имеет айди:" + param1);
    }

    @Test(dataProvider = "userFromSheet1", dataProviderClass = ExcelDataProviders.class)
    public void testExcel(String... params) {
        System.out.println("Пользователь "+ params[1] + " имеет айди:" + params[0]);
    }

    @Test(dataProvider = "userFromCustomSheet", dataProviderClass = ExcelDataProviders.class)
    public void testExcel2(String param1, String param2) {
        System.out.println(param1 + ", " + param2);
    }

    @Test(dataProvider = "usersFromApi", dataProviderClass = ExcelDataProviders.class)
    public void testExcelFromApi(String... params) {
        //param[0] = id
        int id = (int) Double.parseDouble(params[0]);
        Response response = given()
                .contentType(ContentType.JSON)
                .get("https://reqres.in/api/users/" + id)
                .then().log().body()
                .extract().response();
        JsonPath jsonPath = response.jsonPath();
        assertEquals(jsonPath.getInt("data.id"), id);
        assertEquals(jsonPath.getString("data.email"),params[1]);
        assertEquals(jsonPath.getString("data.first_name"),params[2]);
        assertEquals(jsonPath.getString("data.last_name"),params[3]);
        assertEquals(jsonPath.getString("data.avatar"),params[4]);
    }
}











