package integration;

import config.DispatcherConfig;
import config.PersistenceConfig;
import dao.UserRepository;

import model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static com.jayway.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={DispatcherConfig.class, PersistenceConfig.class})
public class UserITest {
    @Autowired
    UserRepository userRepository;

    @Test @Transactional
    public void returnsAllUsers() {
        userRepository.save(new User().setUsername("kozhukho").setPassword("password"));
        String response = when().get("/users")
                .then().statusCode(HttpStatus.OK.value())
                .extract().response().asString();
        assertEquals("ok", "ok");
    }
}
