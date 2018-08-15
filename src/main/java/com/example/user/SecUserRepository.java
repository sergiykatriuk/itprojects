package com.example.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SecUserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT CASE WHEN COUNT(u)>0 THEN TRUE ELSE FALSE END FROM UserEntity u where u.login=:login")
    boolean loginExists(@Param("login") String login);

    @Query("SELECT CASE WHEN COUNT(u)>0 THEN TRUE ELSE FALSE END FROM UserEntity u where u.emailConfirmCode=:code")
    boolean codeExists(@Param("code") String code);

    @Query("SELECT CASE WHEN COUNT(u)>0 THEN TRUE ELSE FALSE END FROM UserEntity u where u.email=:email or u.emailNew=:email")
    boolean emailExists(@Param("email") String email);

    @Query("SELECT u FROM UserEntity u where u.login=:login")
    UserEntity findByLogin(@Param("login") String login);

    UserEntity findByEmailConfirmCode(String code);

}
