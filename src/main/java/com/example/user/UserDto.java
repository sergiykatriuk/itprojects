package com.example.user;

import java.io.Serializable;

public class UserDto implements Serializable{

    private String login;
    private String password;
    private String name;
    private String email;
    private String emailNew;
    private String emailConfirmCode;

    public String getLogin() {
        return login;
    }

//    public UserDto() {
//    }

//    public UserDto(UserEntity userEntity) {
//
//        login = userEntity.getLogin();
//        name = userEntity.getName();
//        email = userEntity.getEmail();
//        emailNew = userEntity.getEmailNew();
//
//    }

    public String getEmailConfirmCode() {
        return emailConfirmCode;
    }

    public void setEmailConfirmCode(String emailConfirmCode) {
        this.emailConfirmCode = emailConfirmCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailNew() {
        return emailNew;
    }

    public void setEmailNew(String emailNew) {
        this.emailNew = emailNew;
    }
}
