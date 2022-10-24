import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import api.endpoints.EndPoints;

public class BaseTest {

    private final static String BASE_URL = EndPoints.BASE_URL;

    @BeforeClass
    static public void setUpBase() {
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .build();

        RestAssured.requestSpecification = requestSpec;
    }
}
