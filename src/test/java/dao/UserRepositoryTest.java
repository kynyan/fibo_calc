package dao;

import config.PersistenceConfig;
import model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

import static io.qala.datagen.RandomShortApi.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(classes = PersistenceConfig.class)
public class UserRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test public void mustSaveUserToDb() {
        User user = User.random();
        User added = userRepository.save(user);
        flushAndClear();
        User fromDb = userRepository.findById(added.getId()).get();
        assertEquals(user.getUsername(), fromDb.getUsername());
        assertEquals(user.getPassword(), fromDb.getPassword());
    }

    @Test public void throwsError_IfUsernameConstraintsViolated() {
        String invalidUsername = sample(nullOrEmpty(), alphanumeric(User.USERNAME_UPPER_BOUNDARY + 1));
        User user = User.random().setUsername(invalidUsername);
        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> userRepository.saveAndFlush(user))
                .withMessageContaining(User.USERNAME_NOTE);
        user.setUsername(unicode(User.USERNAME_LOWER_BOUNDARY, User.USERNAME_UPPER_BOUNDARY));
        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> userRepository.saveAndFlush(user))
                .withMessageContaining(User.ALLOWED_SYMBOLS_NOTE);
    }

    @Test public void throwsError_IfPasswordConstraintsViolated() {
        String invalidPassword = sample(nullOrEmpty(), alphanumeric(1, User.PASSWORD_LOWER_BOUNDARY - 1)
                , alphanumeric(User.PASSWORD_UPPER_BOUNDARY + 1));
        User user = User.random().setPassword(invalidPassword);
        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> userRepository.saveAndFlush(user))
                .withMessageContaining(User.PASSWORD_NOTE);
        user.setPassword(unicode(User.PASSWORD_LOWER_BOUNDARY, User.PASSWORD_UPPER_BOUNDARY));
        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> userRepository.saveAndFlush(user))
                .withMessageContaining(User.ALLOWED_SYMBOLS_NOTE);
    }

    private void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }

}
