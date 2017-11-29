package integration;

import config.MvcConfig;
import config.PersistenceConfig;
import model.Calculator;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigInteger;

import static com.jayway.restassured.RestAssured.given;
import static io.qala.datagen.RandomShortApi.integer;
import static org.junit.Assert.assertEquals;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={MvcConfig.class, PersistenceConfig.class})
public class CalculatorITest {
    @Test public void returnsFiboNumber() {
        int index = integer(0, Calculator.FIBO_INDEX_UPPER_BOUNDARY);
        BigInteger result = given().param("index", index)
                .get("/calculator")
                .then().statusCode(HttpStatus.OK.value())
                .extract().as(BigInteger.class);
        assertEquals(new Calculator(index).getFiboNumber(), result);
    }
}
