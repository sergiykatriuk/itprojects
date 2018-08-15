package com.example.user;

import com.captcha.botdetect.web.servlet.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

import static com.example.user.Tools.addCurrUserToModel;

@Controller
public class ControlProfile {

    @Autowired
    private SecUserRepository secUserRepository;
    @Autowired
    private UserServ userServ;


    @RequestMapping(value = {"/*"})
    public String any(Model model, Principal principal) {

        addCurrUserToModel(model, principal, secUserRepository);
        return "profile";
    }

    @RequestMapping("/register")
    public String register() {

        return "register";
    }

    @RequestMapping("/profileError")
    public String profileError(RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("messages",
                new Messages("warning", "Login failed !").toJson());
        return "redirect:/profile";
    }

    @RequestMapping("/logoutSuccess")
    public String logoutSuccess(RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("messages",
                new Messages("success", "Logged out successfully.")
                        .toJson());

        return "redirect:/profile";
    }

    @RequestMapping("/profile")
    public String profile(Model model, Principal principal) {

        addCurrUserToModel(model, principal, secUserRepository);

        if (principal != null) {
            UserDto user = userServ.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("currentUserDetails", user);
        }
        return "profile";
    }

    @RequestMapping(value = "/createuser", method = RequestMethod.POST)
    public String createuser(@RequestParam(required = false) String inputLogin
            , @RequestParam(required = false) String inputPassword
            , @RequestParam(required = false) String inputUsername
            , @RequestParam(required = false) String inputEmail
            , @RequestParam(required = false) String captchaCodeReg
            , HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {

        Captcha captcha = Captcha.load(httpServletRequest, "captchaReg");

        if (captchaCodeReg == null) {
            captchaCodeReg = "";
        }

        boolean isValid = captcha.validate(captchaCodeReg);

        UserDto user = new UserDto();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setLogin(inputLogin);

        if (inputPassword == null) {
            inputPassword = "";
        }

        user.setPassword(passwordEncoder.encode(inputPassword));
        user.setName(inputUsername);
        user.setEmailNew(inputEmail);
        redirectAttributes.addFlashAttribute("curruser", user);

        if (isValid) {

            if ((inputLogin == null) || (inputPassword == null) || (inputUsername == null) || (inputEmail == null)
                    || (inputLogin.isEmpty()) || (inputPassword.isEmpty()) || (inputUsername.isEmpty()) || (inputEmail.isEmpty())
                    ) {

                redirectAttributes.addFlashAttribute("messages",
                        new Messages("warning", "Fill all the fields !!!")
                                .toJson());
                return "redirect:/register";

            } else if ((inputLogin.length() > 20) || (inputPassword.length() > 20)
                    || (inputUsername.length() > 45) || (inputEmail.length() > 45)) {

                redirectAttributes.addFlashAttribute("messages",
                        new Messages("warning", "Invalid field length !").toJson());
                return "redirect:/register";

            } else if (userServ.loginExists(inputLogin)) {

                redirectAttributes.addFlashAttribute("messages",
                        new Messages("warning", "Login already exists !!!").toJson());
                return "redirect:/register";

            } else if (userServ.emailExists(inputEmail)) {

                redirectAttributes.addFlashAttribute("messages",
                        new Messages("warning", "E-mail already exists !!!").toJson());
                return "redirect:/register";

            } else {

                userServ.create(user);
                Messages messages = new Messages("success", "User successfully registered !");
                if (userServ.sendEmailConfirmation(user.getLogin())) {
                    messages.addMessage("info", "Confirmation email was sent !");
                } else {
                    messages.addMessage("warning", "Email was not sent :(");
                }
                redirectAttributes.addFlashAttribute("messages", messages.toJson());

            }
        } else {

            redirectAttributes.addFlashAttribute("messages"
                    , new Messages("warning", "CAPTCHA is incorrect !")
                            .toJson());
            return "redirect:/register";

        }
        return "redirect:/profile";
    }


    @RequestMapping(value = "/sendconfirmemail")
    public String sendConfirmEmail(RedirectAttributes redirectAttributes) {

        String login = SecurityContextHolder.getContext().getAuthentication().getName();

        if (userServ.sendEmailConfirmation(login)) {

            redirectAttributes.addFlashAttribute("messages",
                    new Messages("info", "New code was sent, please check your mailbox.").toJson());

        } else {

            redirectAttributes.addFlashAttribute("messages",
                    new Messages("warning", "Something went wrong, please repeat...").toJson());

        }

        return "redirect:/profile";
    }

    @RequestMapping(value = "/emailconfirm")
    public String emailConfirm(@RequestParam(required = false) String code,
                               RedirectAttributes redirectAttributes) {

        if ((code == null) || (code.isEmpty()) || (code.length() > 20)
                || (!userServ.codeExists(code))) {

            redirectAttributes.addFlashAttribute("messages", new Messages("warning", "The code is incorrect !").toJson());
            return "redirect:/profile";
        } else {

            if (userServ.confirmEmail(code)) {

                redirectAttributes.addFlashAttribute("messages",
                        new Messages("success", "Email was confirmed successfully !").toJson());

            } else {

                redirectAttributes.addFlashAttribute("messages",
                        new Messages("warning", "Email was not confirmed !").toJson());

            }

        }
        return "redirect:/profile";
    }


    @RequestMapping("/loginSuccess")
    public String loginSuccess(Model model, Principal principal) {

        addCurrUserToModel(model, principal, secUserRepository);
        UserDto user = userServ.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        Messages messages = new Messages("info", "Login OK !")
                .addMessage("success", "Hello " + user.getName() + " !");

        model.addAttribute("messages", messages.toJson());
        model.addAttribute("currentUserDetails", user);

        return "profile";
    }


}
