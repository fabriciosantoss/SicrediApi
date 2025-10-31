import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class ApiTests {

    static final String baseURI = "https://dummyjson.com";
    static String accessToken = "";
    static String requestBody = """
            {
               "username": "emilys",
               "password": "emilyspass"
            }
            """;
    static String InvalidRequestBody = """
            {
               "username": "emilys",
               "password": "invalidPassword"
            }
            """;
    static String newProduct = """
        {
            "title": "Perfume Oil",
            "description": "Mega Discount, Impression of A...",
            "price": 13,
            "discountPercentage": 8.4,
            "rating": 4.26,
            "stock": 65,
            "brand": "Impression of Acqua Di Gio",
            "category": "fragrances",
            "thumbnail": "https://i.dummyjson.com/data/products/11/thumnail.jpg"
        }
        """;

    @Nested
    class SicrediTests {

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
        public void ShouldGetAnInvalidLogin() {
            RestAssured.baseURI = baseURI;
            given()
                    .header("Content-Type", "application/json")
                    .body(InvalidRequestBody)
                    .when()
                    .post("/auth/login")
                    .then().log().status()
                    .statusCode(400)
                    .body("message", equalTo("Invalid credentials"));

        }

        @Test
        public void ShouldGetTest() {
            RestAssured.baseURI = baseURI;
            given()
                    .when()
                    .get("/test")
                    .then()
                    .statusCode(200)
                    .log().body()
                    .body("status", equalTo("ok"))
                    .body("method", equalTo("GET"))
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

        @Test
        public void ShouldGetProducts() {
            given()
                    .header("Content-Type", "application/json")
                    .when()
                    .get("/products")
                    .then().log().status()
                    .statusCode(200)
                    .body(matchesJsonSchemaInClasspath("schemas/productsSchema.json"));
        }

        @Test
        public void ShouldAddProduct() {
            RestAssured.baseURI = baseURI;
            given()
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + accessToken)
                    .body(newProduct)
                    .when()
                    .post("/products/add")
                    .then().log().status()
                    .statusCode(201)
                    .body(matchesJsonSchemaInClasspath("schemas/newProductSchema.json"));
        }

        @Test
        public void ShouldGetProductsWithAuth() {
            given()
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + accessToken)
                    .when()
                    .get("/auth/products")
                    .then().log().status()
                    .statusCode(200)
                    .body(matchesJsonSchemaInClasspath("schemas/productsSchema.json"));
        }

        @Test
        public void ShouldGetAnInvalidTokenMessage() {
            given()
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + ".eyJpZCI6MSwidXNlcm5hbWUiOiJlbWlseXMiLCJlbWFpbCI6ImVtaWx5LmpvaG5zb25AeC5kdW1teWpzb24uY29tIiwiZmlyc3ROYW1lIjoiRW1pbHkiLCJsYXN0TmFtZSI6IkpvaG5zb24iLCJnZW5kZXIiOiJmZW1hbGUiLCJpbWFnZSI6Imh0dHBzOi8vZHVtbXlqc29uLmNvbS9pY29uL2VtaWx5cy8xMjgiLCJpYXQiOjE3NjE3ODUxMjksImV4cCI6MTc2MTc4ODcyOX0.PtfZBIllQ2JaL07q0mb8Q9_qhUZOcKNA8GlkYWLqrus")
                    .when()
                    .get("/auth/products")
                    .then().log().status()
                    .statusCode(403)
                    .body("message", equalTo("invalid token"));
        }

        @Test
        public void ShouldNotGetProductsWithAuth() {
            given()
                    .header("Content-Type", "application/json")
                    .when()
                    .get("/auth/products")
                    .then().log().status()
                    .statusCode(401)
                    .body("message", equalTo("Access Token is required"));
        }

        @Test
        public void ShouldGetProductById() {
            given()
                    .header("Content-Type", "application/json")
                    .when()
                    .get("/products/55")
                    .then().log().status()
                    .statusCode(200)
                    .body(matchesJsonSchemaInClasspath("schemas/productByIdSchema.json"))
                    .body("title", equalTo("Egg Slicer"));
        }

        @Test
        public void ShouldGetInvalidProductById() {
            given()
                    .header("Content-Type", "application/json")
                    .when()
                    .get("/products/999")
                    .then().log().status()
                    .statusCode(404)
                    .body("message", equalTo("Product with id '999' not found"));
        }
    }
}