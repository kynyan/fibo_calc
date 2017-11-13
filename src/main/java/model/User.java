package model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter @Setter
public class User {
    public static final String SEQUENCE_GENENERATOR = "user_seq_gen";
    public static final String SEQUENCE_NAME = "USER_SEQ";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_GENENERATOR)
    @SequenceGenerator(name = SEQUENCE_GENENERATOR, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
}
