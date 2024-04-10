package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lib.Assertions;
import lib.BaseCaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

public class UserAuthTest extends BaseCaseTest{
    String cookie;
    String header;
    Integer userIdOnAuth;

    @BeforeEach
    public void loginUser () {
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
    }

    @Test
    public void authorizationTest() {

        Response responseCheckAuth = RestAssured
                .given()
                .header("x-csrf-token", this.header)
                .cookie("auth_sid", this.cookie)
                .get("https://playground.learnqa.ru/api/user/auth")
                .andReturn();

        Assertions.asserJsonByName(responseCheckAuth, "user_id", this.userIdOnAuth);

    }
    @ParameterizedTest
    @ValueSource(strings = {"cookie", "headers"})
    public void testNegativeAuthUser(String condition){


        RequestSpecification spec = RestAssured.given();
        spec.baseUri("https://playground.learnqa.ru/api/user/auth");

        if (condition.equals("cookie")) {
            spec.cookie("auth_sid", this.cookie);
        } else if (condition.equals("headers")) {
            spec.header("x-csrf-token", this.header);
        } else {
            throw new IllegalArgumentException("Conditional value is know: " + condition);
        }

        Response responseForCheck = spec.get().andReturn();
        Assertions.asserJsonByName(responseForCheck, "user_id", 0);
    }
}
