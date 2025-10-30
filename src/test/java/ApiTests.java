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

}






void main() {
}


