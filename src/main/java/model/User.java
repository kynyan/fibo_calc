package model;

import dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter @Setter
@Accessors(chain = true)
@NoArgsConstructor
public class User {
    public static final String SEQUENCE_GENENERATOR = "user_seq_gen";
    public static final String SEQUENCE_NAME = "USER_SEQ";

    public User(UserDto dto) {
        this.username = dto.getUsername();
        this.password = dto.getPassword();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_GENENERATOR)
    @SequenceGenerator(name = SEQUENCE_GENENERATOR, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
}
