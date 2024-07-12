import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class AssertGenderTest{

    @Test
    public void testGenderVerification() {
        verifyGender("Isabella", "female");
        verifyGender("Olivia", "female");
        verifyGender("Ethan", "male");
        verifyGender("Christopher", "male");
        verifyGender("Sophia", "female");
    }

    private void verifyGender(String name, String expectedGender) {
        RestAssured.baseURI = "https://api.genderize.io";

        Response response = given()
                .queryParam("name", name)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().response();

        String gender = response.path("gender");
        
        // Assertion
        assertNotNull(gender, "Gender should not be null");
        assertEquals(expectedGender, gender, "Expected gender for " + name + " is " + expectedGender + " but got " + gender);
        
        // Print success message
        System.out.println("Assertion successful: Expected gender for " + name + " is " + expectedGender + " and got " + gender);
    }
}
