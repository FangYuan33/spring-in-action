package tacos.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tacos.repository.UserJPARepository;
import tacos.domain.User;
import tacos.security.dto.RegistrationInfoDto;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationInfoDto registrationInfo) {
        User user = registrationInfo.toUser(passwordEncoder);
        userJPARepository.save(user);

        return "redirect:/login";
    }
}
