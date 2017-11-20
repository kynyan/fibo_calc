package model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;
//import javax.validation.constraints.Size;

import static io.qala.datagen.RandomShortApi.alphanumeric;

@Entity
@Table(name = "user")
@Getter @Setter @Accessors(chain = true)
public class User {
    public static final int USERNAME_LOWER_BOUNDARY = 1;
    public static final int PASSWORD_LOWER_BOUNDARY = 5;
    public static final int USERNAME_UPPER_BOUNDARY = 10;
    public static final int PASSWORD_UPPER_BOUNDARY = 15;
    public static final String ALLOWED_SYMBOLS_NOTE = "Only alphanumeric symbols are allowed";
    public static final String USERNAME_NOTE = "Should be between 1 and 10 symbols";
    public static final String PASSWORD_NOTE = "Should be between 5 and 15 symbols";
    public static final String SEQUENCE_GENENERATOR = "user_seq_gen";
    public static final String SEQUENCE_NAME = "USER_SEQ";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_GENENERATOR)
    @SequenceGenerator(name = SEQUENCE_GENENERATOR, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @Column(name = "username")
//    @NotNull(message = USERNAME_NOTE)
//    @Size(min = USERNAME_LOWER_BOUNDARY, max = USERNAME_UPPER_BOUNDARY, message = USERNAME_NOTE)
//    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = ALLOWED_SYMBOLS_NOTE)
    private String username;

    @Column(name = "password")
//    @NotNull(message = PASSWORD_NOTE)
//    @Size(min = PASSWORD_LOWER_BOUNDARY, max = PASSWORD_UPPER_BOUNDARY, message = PASSWORD_NOTE)
//    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = ALLOWED_SYMBOLS_NOTE)
    private String password;

    public static User random() {
        return new User()
                .setUsername(alphanumeric(USERNAME_LOWER_BOUNDARY, USERNAME_UPPER_BOUNDARY))
                .setPassword(alphanumeric(PASSWORD_LOWER_BOUNDARY, PASSWORD_UPPER_BOUNDARY));
    }
}
