package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseCaseTest;
import lib.DataGenerator;
import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.Map;

public class UserDeleteTest extends BaseCaseTest {

    String cookie;
    String header;
    Integer userIdOnAuth;

    @Test
    public void testDeleteLockUser() {
        Map<String, String> authorizationDate = new HashMap<>();
        authorizationDate.put("email", "vinkotov@example.com");
        authorizationDate.put("password", "1234");

        Response responseAuthorization = RestAssured
                .given()
                .body(authorizationDate)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        this.cookie = this.getCookie(responseAuthorization, "auth_sid");
        this.header = this.getHeader(responseAuthorization, "x-csrf-token");
        this.userIdOnAuth = this.getIntFromJson(responseAuthorization, "user_id");

        Response responseCheckAuth = RestAssured
                .given()
                .header("x-csrf-token", this.header)
                .cookie("auth_sid", this.cookie)
                .delete("https://playground.learnqa.ru/api/user/" + userIdOnAuth)
                .andReturn();

        System.out.println(responseCheckAuth.asString());
        Assertions.assertResponseTextEquals(responseCheckAuth, "{\"error\":\"Please, do not delete test users with ID 1, 2, 3, 4 or 5.\"}");
    }

    @Test
    public void testDeleteCurrentUser() {
        //Generate User
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();

        String userId = responseCreateAuth.getString("id");

        //Login
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = RestAssured
                .given()
                .body(authData)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        //Delete
        Response responseDeleteUser = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .delete("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();

        //Get
        Response responseUserData = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseGetAuth, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseGetAuth, "auth_sid"))
                .get("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();

        System.out.println(responseUserData.asString());
        Assertions.assertResponseTextEquals(responseUserData, "User not found");
    }

    @Test
    public void testDeleteUserWithAnotherLogin() {
        //Generate User
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();

        String userId = responseCreateAuth.getString("id");

        //Login with another user
        Map<String, String> authorizationDate = new HashMap<>();
        authorizationDate.put("email", "vinkotov@example.com");
        authorizationDate.put("password", "1234");

        Response responseAuthorization = RestAssured
                .given()
                .body(authorizationDate)
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        //Delete
        Response responseDeleteUser = RestAssured
                .given()
                .header("x-csrf-token", this.getHeader(responseAuthorization, "x-csrf-token"))
                .cookie("auth_sid", this.getCookie(responseAuthorization, "auth_sid"))
                .delete("https://playground.learnqa.ru/api/user/" + userId)
                .andReturn();

        System.out.println(responseDeleteUser.asString());
        Assertions.assertResponseTextEquals(responseDeleteUser, "{\"error\":\"Please, do not delete test users with ID 1, 2, 3, 4 or 5.\"}");
    }
}
