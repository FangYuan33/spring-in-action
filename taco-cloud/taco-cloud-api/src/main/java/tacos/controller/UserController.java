package tacos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.domain.User;
import tacos.repository.mapper.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/save")
    public Mono<User> save(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/list")
    public Flux<User> list() {
        return userRepository.findAll();
    }
}
