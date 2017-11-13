package model;

import javax.persistence.Entity;

@Entity
public class User {
    private Long id;
    private String username;
    private String password;
}
