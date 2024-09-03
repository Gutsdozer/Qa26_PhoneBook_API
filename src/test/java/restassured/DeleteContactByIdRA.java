package restassured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.ContactDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class DeleteContactByIdRA {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWFyYUBnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTcyNTcyMDM2NSwiaWF0IjoxNzI1MTIwMzY1fQ.XRtwk7kphLc4XlVyBb4MHOVisF9GfOcxTJfK8GhjZJ0";
    String id;

    @BeforeMethod
    public void preCondition(){
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
        ContactDTO contactDTO = ContactDTO.builder()
                .name("Donna")
                .lastName("Down")
                .email("donna@mail.com")
                .phone("12345555556")
                .address("Tel Aviv")
                .description("Donna")
                .build();

        String message = given()
                .body(contactDTO)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .path("message");
        String[] all = message.split(": ");
        id = all[1];
    }

    @Test
    public void deleteContactById() {
        given()
                .header("Authorization", token)
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", equalTo("Contact was deleted!"));
    }

    @Test
    public void deleteContactByIdWrongToken() {
        given()
                .header("Authorization", "ghsf")
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(401);

    }

    @Test
    public void deleteContactByIdAnyFormatError() {
        given()
                .header("Authorization", token)
                .when()
                .delete("contacts/123")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message", equalTo("Contact with id: 123 not found in your contacts!"));
    }

}
