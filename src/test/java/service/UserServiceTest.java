package service;

import config.PersistenceConfig;
import config.SecurityConfig;
import dto.UserDto;
import model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, SecurityConfig.class})
public class UserServiceTest {
    @Autowired
    private UserServiceInterface userService;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Test
    public void mustSaveUserWithEncodedPasswordToDb() {
        User user = new User(UserDto.random());
        String password = user.getPassword();
        userService.save(user);
        User fromDb = userService.findUser(user.getUsername());
        assertEquals(user.getUsername(), fromDb.getUsername());
        assertTrue(encoder.matches(password, fromDb.getPassword()));
    }
}
