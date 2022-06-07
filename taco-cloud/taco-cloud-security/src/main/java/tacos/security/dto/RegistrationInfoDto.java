package tacos.security.dto;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacos.domain.User;

import java.time.LocalDateTime;

@Data
public class RegistrationInfoDto {

    private String userName;

    private String password;

    private String phoneNumber;

    public User toUser(PasswordEncoder passwordEncoder) {
        User user =  new User();
        BeanUtils.copyProperties(this, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegistrationTime(LocalDateTime.now());

        return user;
    }
}
