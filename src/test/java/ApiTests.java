import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
static final String baseURI =  "https://dummyjson.com";
static String accessToken = "";
static String requestBody ="""
            {
               "username": "emilys",
               "password": "emilyspass"
            }
            """;
@Nested
class SicrediTests {


    @Test
    public void ShouldGetTest() {
        RestAssured.baseURI = baseURI;
        given()
                .when()
                .get("/test")
                .then()
                .statusCode(200).log().all()
                .body(
                "status", equalTo("ok"),
                "method", equalTo("GET")
                )
                .body(matchesJsonSchemaInClasspath("schemas/testSchema.json"));
    }

    @Test
        public void ShouldGetUsers() {
            RestAssured.baseURI = baseURI;
            given()
                .when()
                .get("/users")
                .then().log().status()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/usersSchema.json"));




    }
    @BeforeAll
    public static void ShouldGetLogin() {
        RestAssured.baseURI = baseURI;
        accessToken = given()
               .header("Content-Type", "application/json")
               .body(requestBody)
               .when()
               .post("/auth/login")
               .then().log().status()
               .statusCode(200)
               .body(matchesJsonSchemaInClasspath("schemas/loginSchema.json"))
               .extract().path("accessToken");
    }

    @Test
    public void ShouldGetProducts() {
        given()
                .header("Content-Type", "application/json", "Authorization", "Bearer " + accessToken)
                .when()
                .get("/products")
                .then().log().status()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/productsSchema.json"));







    }


}






void main() {
}


