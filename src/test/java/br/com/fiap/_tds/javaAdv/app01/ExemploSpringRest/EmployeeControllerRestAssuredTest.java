package br.com.fiap._tds.javaAdv.app01.ExemploSpringRest;


import br.com.fiap._tds.javaAdv.app01.ExemploSpringRest.domainmodel.Employee;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerRestAssuredTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup(){
        RestAssured.baseURI="http://localhost";
        RestAssured.port = this.port;
        RestAssured.basePath = "";
    }

    @Test
    void testHelloWorld(){
        given()
                .when()
                .get("/api/employees/hello")
                .then()
                .statusCode(200)
                .body(equalTo("Hello World RESTFULL manner!!!"));
    }


    @Test
    void testHelloWorld2(){
        given()
                .when()
                .get("/api/employees/hello2")
                .then()
                .statusCode(200)
                .body(equalTo("Hello World RESTFULL manner22222222!!!"));
    }

    @Test
    void testFindEmployeeById(){
        given()
            .when()
                .get("/api/employees/" + 1L)
                .then()
                .statusCode(200)
                .body("name", equalTo("Orlandao"))
                .body("id", equalTo(1))
                .body("role", equalTo("CHEFE DA PORRA TODA"))
                .body("department", equalTo("GERAL"));
    }

    @Test
    void testFindAllEmployees(){
        given()
        .when()
                .get("/api/employees/")
                .then()
                .statusCode(200)
                //.body("size()", equalTo(5));
                .body("$", hasSize(equalTo(5)));
    }

    @Test
    void testCreateEmployee(){
        this.createSampleEmployee();
    }

    @Test
    void testRemoveEmployeeById(){
        this.createSampleEmployee();

        given()
                .when()
                .delete("/api/employees/" + 6)
                .then()
                .statusCode(204);

        given()
                .when()
                .get("/api/employees/" + 6)
                .then()
                .statusCode(404);

    }

    private static void createSampleEmployee() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"name\": \"John Doe\", \"role\": \"Developer\", \"department\": \"IT\" }")
                .when()
                .post("/api/employees")
                .then()
                .statusCode(201)
                .body("id", equalTo(6))
                .body("name", equalTo("John Doe"))
                .body("role", equalTo("Developer"))
                .body("department", equalTo("IT"));
    }

    @Test
    void testPutEmployee(){
        this.createSampleEmployee();

        given()
                .when()
                .put("/api/employees/"+5)
                .then()
                .statusCode(400)
                .body("id", equalTo(5))
                .body("name", equalTo("Victor"))
                .body("role", equalTo("Developer"))
                .body("departament", equalTo("IT"));
    }

//    @Test
//    void testPatchEmployee(){
//        given()
//                .when()
//                .patch();
//
//    }
}