package com.example.user;

public interface UserServ {

    UserDto findByLogin(String login);
    void create(UserDto user);
    boolean sendEmailConfirmation(String login);
    boolean codeExists(String code);
    boolean confirmEmail(String code);
    boolean loginExists(String login);
    boolean emailExists(String email);


}
