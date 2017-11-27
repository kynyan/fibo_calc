package dto;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static dto.UserDto.*;
import static io.qala.datagen.RandomShortApi.*;
import static org.junit.Assert.assertEquals;

public class UserDtoTest {
    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test public void usernameLengthViolatesBoundaries() {
        String invalidUsername = sample(nullOrEmpty(), alphanumeric(USERNAME_UPPER_BOUNDARY + 1));
        UserDto user = UserDto.random().setUsername(invalidUsername);

        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(user);

        assertEquals(1, constraintViolations.size() );
        assertEquals(USERNAME_NOTE, constraintViolations.iterator().next().getMessage());
    }

    @Test public void usernameIsNotAlphanumeric() {
        UserDto user = UserDto.random().setUsername(unicode(USERNAME_LOWER_BOUNDARY, USERNAME_UPPER_BOUNDARY));

        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(user);

        assertEquals(1, constraintViolations.size() );
        assertEquals(ALLOWED_SYMBOLS_NOTE, constraintViolations.iterator().next().getMessage());
    }

    @Test public void passwordLengthViolatesBoundaries() {
        String invalidPassword = sample(nullOrEmpty(), alphanumeric(1, PASSWORD_LOWER_BOUNDARY - 1));
        UserDto user = UserDto.random().setPassword(invalidPassword);

        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(user);

        assertEquals(1, constraintViolations.size() );
        assertEquals(PASSWORD_NOTE, constraintViolations.iterator().next().getMessage());
    }

    @Test public void passwordIsNotAlphanumeric() {
        UserDto user = UserDto.random().setPassword(unicode(PASSWORD_LOWER_BOUNDARY, PASSWORD_UPPER_BOUNDARY));
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(user);

        assertEquals(1, constraintViolations.size() );
        assertEquals(ALLOWED_SYMBOLS_NOTE, constraintViolations.iterator().next().getMessage());
    }
}
