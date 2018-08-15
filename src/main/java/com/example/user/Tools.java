package com.example.user;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Random;

public class Tools {

    public static void addCurrUserToModel(Model model, Principal principal, SecUserRepository secUserRepository) {

        if (principal == null) {
            model.addAttribute("currentuser", "anonymousUser");
        } else {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("currentuser", user);
            model.addAttribute("currentUserDetails", secUserRepository.findByLogin(user.getUsername()));
        }

    }

    /**
     * Creates code from numeric (48-57), alphabetic uppercase 65-90) and lowercase (97-122) values
     */
    public static String generateCode(int length) {
        String password = "";
        for (int i = 0; i < length; i++) {
            Random random = new Random();
            int randInt = random.nextInt(62);
            randInt = (randInt < 10 ? randInt + 48: (randInt < 36 ? randInt + 65 - 10 : randInt + 97 - 10 - 26));
            password += (char) randInt;
        }
        return password;
    }


}
