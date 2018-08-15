package com.example.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class UserServImpl implements UserServ {

    @Autowired
    private SecUserRepository secUserRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MailSender mailSender;


    @Override
    public UserDto findByLogin(String login) {

        UserEntity userEntity = secUserRepository.findByLogin(login);
        return modelMapper.map(userEntity,UserDto.class);
    }

    @Override
    public void create(UserDto user) {

        UserEntity userEntity = modelMapper.map(user,UserEntity.class);
        secUserRepository.save(userEntity);
    }

    @Override
    public boolean confirmEmail(String code) {

        UserEntity userEntity = secUserRepository.findByEmailConfirmCode(code);
        if (userEntity==null) {
            return false;
        } else {
            userEntity.setEmail(userEntity.getEmailNew());
            userEntity.setEmailNew("");
            userEntity.setEmailConfirmCode("");
            secUserRepository.save(userEntity);
            return true;
        }
    }

    @Override
    public boolean loginExists(String login) {

        return secUserRepository.loginExists(login);
    }

    @Override
    public boolean emailExists(String email) {

        return secUserRepository.emailExists(email);
    }

    @Override
    public boolean sendEmailConfirmation(String login) {

        String code;
        while (secUserRepository.codeExists(
                code = Tools.generateCode(20))
                ) {
        }
        UserEntity user = secUserRepository.findByLogin(login);
        user.setEmailConfirmCode(code);
        secUserRepository.save(user);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("admin@vps14015ua.hyperhost.name");
        simpleMailMessage.setTo(user.getEmailNew());
        simpleMailMessage.setSubject("Email confirmation");
        simpleMailMessage.setText("Hello, " + user.getName() + "\n" +
                "Please confirm your email, following the link below:\n" +
                "http://195.54.163.16:9999/emailconfirm?code=" + user.getEmailConfirmCode() + "\n" +
                "or by entering this code: " + user.getEmailConfirmCode() + " on your profile page, here:\n" +
                "http://195.54.163.16:9999/profile");
        boolean result;
        try {
            mailSender.send(simpleMailMessage);
            result = true;
        } catch (MailException ex) {
            System.out.println("Mail was not sent " + user.getEmailNew());
            result = false;
        }
        return result;

    }

    @Override
    public boolean codeExists(String code) {

        return secUserRepository.codeExists(code);
    }


}
