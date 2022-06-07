package tacos.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tacos.domain.User;
import tacos.repository.UserJPARepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserJPARepository userJPARepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userJPARepository.findByUserName(username);

        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("User: " + username + "Not Found!");
        }
    }
}
