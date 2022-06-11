package tacos.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import tacos.dto.RegistrationDto;
import tacos.domain.User;
import tacos.service.UserService;

@RestController
@Api(tags = "注册模块")
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Object> processRegistration(RegistrationDto registrationInfo) {
        User user = new User();
        BeanUtils.copyProperties(registrationInfo, user);
        userService.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
