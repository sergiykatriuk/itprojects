package com.example.user;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user", schema = "eng", catalog = "",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "email_new"})})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(nullable = false, length = 20)
    @Size(max = 20)
    private String login;
    @Size(max = 72)
    @Column(nullable = false, length = 72)
    private String password;
    @Size(max = 45)
    @Column(nullable = false, length = 45)
    private String name;
    @Size(max = 45)
    @Column(nullable = false, length = 45, unique = true)
    private String email;
    @Size(max = 45)
    @Column(name = "email_new", length = 45, unique = true)
    private String emailNew;
    @Size(max = 20)
    @Column(name = "email_confirm_code", length = 20, unique = true)
    private String emailConfirmCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailNew() {
        return emailNew;
    }

    public void setEmailNew(String emailNew) {
        this.emailNew = emailNew;
    }

    public String getEmailConfirmCode() {
        return emailConfirmCode;
    }

    public void setEmailConfirmCode(String emailConfirmCode) {
        this.emailConfirmCode = emailConfirmCode;
    }
}
