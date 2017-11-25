package model;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static io.qala.datagen.RandomShortApi.*;
import static model.User.*;
import static org.junit.Assert.assertEquals;

public class UserTest {
    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test public void usernameLengthViolatesBoundaries() {
        String invalidUsername = sample(nullOrEmpty(), alphanumeric(User.USERNAME_UPPER_BOUNDARY + 1));
        User user = User.random().setUsername(invalidUsername);

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        assertEquals(1, constraintViolations.size() );
        assertEquals(USERNAME_NOTE, constraintViolations.iterator().next().getMessage());
    }

    @Test public void usernameIsNotAlphanumeric() {
        User user = User.random().setUsername(unicode(User.USERNAME_LOWER_BOUNDARY, User.USERNAME_UPPER_BOUNDARY));

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        assertEquals(1, constraintViolations.size() );
        assertEquals(ALLOWED_SYMBOLS_NOTE, constraintViolations.iterator().next().getMessage());
    }

    @Test public void passwordLengthViolatesBoundaries() {
        String invalidPassword = sample(nullOrEmpty(), alphanumeric(1, User.PASSWORD_LOWER_BOUNDARY - 1));
        User user = User.random().setPassword(invalidPassword);

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        assertEquals(1, constraintViolations.size() );
        assertEquals(PASSWORD_NOTE, constraintViolations.iterator().next().getMessage());
    }

    @Test public void passwordIsNotAlphanumeric() {
        User user = User.random().setPassword(unicode(User.PASSWORD_LOWER_BOUNDARY, User.PASSWORD_UPPER_BOUNDARY));
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        assertEquals(1, constraintViolations.size() );
        assertEquals(ALLOWED_SYMBOLS_NOTE, constraintViolations.iterator().next().getMessage());
    }
}
